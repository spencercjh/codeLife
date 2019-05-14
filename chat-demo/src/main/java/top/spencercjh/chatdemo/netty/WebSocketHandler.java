package top.spencercjh.chatdemo.netty;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.spencercjh.chatdemo.pojo.WebSocketMessage;

import java.util.Date;

/**
 * @author spencercjh
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private WebSocketServerHandshaker handShaker;
    private Gson gson = new Gson();

    /**
     * on msg
     * 有信号进来时
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketMessage(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * on open
     * Invoked when a Channel is active; the Channel is connected/bound and ready.
     * 当连接打开时，这里表示有数据将要进站。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyConfig.group.add(ctx.channel());
    }

    /**
     * on close
     * Invoked when a Channel leaves active state and is no longer connected to its remote peer.
     * 当连接要关闭时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        broadcastWsMsg(ctx, new WebSocketMessage(-11000, ctx.channel().id().toString()));
        NettyConfig.group.remove(ctx.channel());
    }

    /**
     * on msg over
     * Invoked when a read operation on the Channel has completed.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * onerror
     * 发生异常时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 集中处理 ws 中的消息
     */
    private void handleWebSocketMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof CloseWebSocketFrame) {
            // 关闭指令
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
        }

        if (msg instanceof PingWebSocketFrame) {
            // ping 消息
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
        } else if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame message = (TextWebSocketFrame) msg;
            // 文本消息
            WebSocketMessage webSocketmessage = gson.fromJson(message.text(), WebSocketMessage.class);
            logger.info("接收到消息：" + webSocketmessage);
            switch (webSocketmessage.getType()) {
                // 进入房间
                case 1:
                    // 给进入的房间的用户响应一个欢迎消息，向其他用户广播一个有人进来的消息
                    broadcastWsMsg(ctx, new WebSocketMessage(-10001, webSocketmessage.getName()));
                    AttributeKey<String> name;
                    if (AttributeKey.exists(webSocketmessage.getName())) {
                        name = AttributeKey.valueOf(webSocketmessage.getName());
                    } else {
                        name = AttributeKey.newInstance(webSocketmessage.getName());
                    }
                    ctx.channel().attr(name);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(
                            gson.toJson(new WebSocketMessage(-1, webSocketmessage.getName()))));
                    break;
                // 发送消息
                case 2:
                    // 广播消息
                    broadcastWsMsg(ctx, new WebSocketMessage(
                            -2, webSocketmessage.getName(), webSocketmessage.getBody()));
                    break;
                // 离开房间.
                case 3:
                    broadcastWsMsg(ctx, new WebSocketMessage(
                            -11000, webSocketmessage.getName(), webSocketmessage.getBody()));
                    break;
                default:
                    break;
            }
            NettyConfig.group.writeAndFlush(new TextWebSocketFrame(new Date().toString()));
        }
        // donothing, 暂时不处理二进制消息

    }

    /**
     * 处理 http 请求，WebSocket 初始握手 (opening handshake ) 都始于一个 HTTP 请求
     */
    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess() || !("websocket".contentEquals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + NettyConfig.WS_HOST + NettyConfig.WS_PORT, null, false);
        handShaker = factory.newHandshaker(req);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 响应非 WebSocket 初始握手请求
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, DefaultFullHttpResponse res) {
        if (200 != res.status().code()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (200 != res.status().code()) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 广播 websocket 消息（不给自己发）
     */
    private void broadcastWsMsg(ChannelHandlerContext ctx, WebSocketMessage msg) {
        NettyConfig.group.stream()
                .filter(channel -> channel.id() != ctx.channel().id())
                .forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(msg))));
    }
}
