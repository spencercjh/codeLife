package file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author SpencerCJH
 * @date 2019/5/14 14:42
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9.]*");
    private final String url;


    HttpFileServerHandler(String url) {
        this.url = url;
    }

    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        String dirPath = dir.getPath();
        stringBuilder.append("<!DOCTYPE html>\r\n");
        stringBuilder.append("<html><head><title>");
        stringBuilder.append(dirPath);
        stringBuilder.append(" 目录：");
        stringBuilder.append("</title></head><body>\r\n");
        stringBuilder.append("<h3>");
        stringBuilder.append(dirPath).append(" 目录：");
        stringBuilder.append("</h3>\r\n");
        stringBuilder.append("<ul>");
        stringBuilder.append("<li>向上：<a href=\"../\">..</a></li>\r\n");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isHidden() || !file.canRead()) {
                continue;
            }
            String name = file.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            if (file.isDirectory()) {
                stringBuilder.append("<li>文件夹：<a href=\"");
            } else {
                stringBuilder.append("<li>文件：<a href=\"");
            }
            stringBuilder.append(name);
            stringBuilder.append("\">");
            stringBuilder.append(name);
            stringBuilder.append("</a></li>\r\n");
        }
        stringBuilder.append("</ul></body></html>\r\n");
        ByteBuf buffer = Unpooled.copiedBuffer(stringBuilder, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                mimeTypesMap.getContentType(file.getPath()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private String sanitizeUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, CharsetUtil.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, CharsetUtil.ISO_8859_1.name());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                throw new Error();
            }
        }
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 如果无法解码400
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }
        // 只支持GET方法
        if (request.method() != GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.uri();
        // 格式化URL，并且获取路径
        final String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        File file = new File(path);
        // 如果文件不可访问或者文件不存在
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        // 如果是目录
        if (file.isDirectory()) {
            //1. 以/结尾就列出所有文件
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                //2. 否则自动+/
                sendRedirect(ctx, uri + '/');
            }
            return;
        }
        if (!file.isFile()) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        try {
            dealWithResponse(file, ctx, request);
        } catch (IOException e) {
            e.printStackTrace();
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private void dealWithResponse(File file, ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        RandomAccessFile randomAccessFile;
        try {
            // 以只读的方式打开文件
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            sendError(ctx, NOT_FOUND);
            return;
        }
        long fileLength = randomAccessFile.length();
        // 创建一个默认的HTTP响应
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        // 设置Content Length
        HttpHeaderUtil.setContentLength(response, fileLength);
        // 设置Content Type
        setContentTypeHeader(response, file);
        // 如果request中有KEEP ALIVE信息
        if (HttpHeaderUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        // 1.http response
        ctx.write(response);
        ChannelFuture sendFileFuture;
        // 通过Netty的ChunkedFile对象直接将文件写入发送到缓冲区中
        // 2.http content
        sendFileFuture = ctx.write(
                new ChunkedFile(randomAccessFile, 0, fileLength, 8192),
                ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future,
                                            long progress, long total) {
                // total unknown
                if (total < 0) {
                    System.err.println("Transfer progress: " + progress);
                } else {
                    System.err.println("Transfer progress: " + progress + " / " + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) {
                System.out.println("Transfer complete.");
            }
        });
        // 3.last http content
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果不支持keep-Alive，服务器端主动关闭请求
        if (!HttpHeaderUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}