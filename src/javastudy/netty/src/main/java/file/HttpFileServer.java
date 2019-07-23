package file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * https://www.cnblogs.com/carl10086/p/6185095.html
 *
 * @author SpencerCJH
 * @date 2019/5/14 14:51
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/";
    private static final int DEFAULT_PORT = 8080;
    private static final String HOST = "127.0.0.1";
    /**
     * 65536
     */
    private static final int MAX_CONTENT_LENGTH = 1 << 16;

    public static void main(String[] args) throws Exception {
        new HttpFileServer().run(HOST, DEFAULT_PORT, DEFAULT_URL);
    }

    private void run(final String host, final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 请求消息解码器
                            ch.pipeline().addLast("file-decoder", new HttpRequestDecoder());
                            // 目的是将多个消息转换为单一的request或者response对象
                            ch.pipeline().addLast("file-aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH));
                            //响应解码器
                            ch.pipeline().addLast("file-encoder", new HttpResponseEncoder());
                            //目的是支持异步大文件传输
                            ch.pipeline().addLast("file-chunked", new ChunkedWriteHandler());
                            // 业务逻辑
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            System.out.println("HTTP文件目录服务器启动，网址是 : " + "file://" + host + ":" + port + url);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}