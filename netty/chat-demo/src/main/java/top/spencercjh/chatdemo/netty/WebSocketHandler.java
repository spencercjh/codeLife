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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author spencercjh
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final Gson gson = new Gson();
    private WebSocketServerHandshaker handShaker;

    /**
     * 消息读取
     * @param ctx ChannelHandlerContext
     * @param msg WebSocketFrame or HTTP Request
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
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
        Netty.CHANNEL_GROUP.add(ctx.channel());
    }

    /**
     * on close
     * Invoked when a Channel leaves active state and is no longer connected to its remote peer.
     * 当连接要关闭时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        broadcastWsMsg(ctx, new WebSocketMessage(-11000, ctx.channel().id().toString()));
        Netty.CHANNEL_GROUP.remove(ctx.channel());
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
                    broadcastWsMsg(ctx, new WebSocketMessage(WebSocketMessage.ENTER_MSG_TYPE, webSocketmessage.getName()));
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(
                            gson.toJson(new WebSocketMessage(WebSocketMessage.WELCOME_MSG_TYPE, webSocketmessage.getName()))));
                    break;
                // 发送消息
                case 2:
                    // 广播消息
                    broadcastWsMsg(ctx, new WebSocketMessage(
                            WebSocketMessage.NORMAL_MSG_TYPE, webSocketmessage.getName(), webSocketmessage.getBody()));
                    break;
                // 离开房间.
                case 3:
                    broadcastWsMsg(ctx, new WebSocketMessage(
                            WebSocketMessage.LEAVE_MSG_TYPE, webSocketmessage.getName(), webSocketmessage.getBody()));
                    break;
                default:
                    break;
            }
            Netty.CHANNEL_GROUP.writeAndFlush(new TextWebSocketFrame(new Date().toString()));
        }
    }

    /**
     * 处理 http 请求，WebSocket 初始握手 (opening handshake ) 都始于一个 HTTP 请求
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        if (!fullHttpRequest.decoderResult().isSuccess() || !(WebSocketMessage.HEADER_WEBSOCKET.contentEquals(
                fullHttpRequest.headers().get(WebSocketMessage.HEADER_UPGRADE)))) {
            sendHttpResponse(ctx, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + Netty.WS_HOST + Netty.WS_PORT, null, false);
        handShaker = factory.newHandshaker(fullHttpRequest);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), fullHttpRequest);
        }
    }

    /**
     * 响应非 WebSocket 初始握手请求
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, DefaultFullHttpResponse res) {
        if (HttpStatus.OK.value() != res.status().code()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(res);
        if (HttpStatus.OK.value() != res.status().code()) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 广播 websocket 消息（不给自己发）
     */
    private void broadcastWsMsg(ChannelHandlerContext ctx, WebSocketMessage msg) {
        Netty.CHANNEL_GROUP.stream()
                .filter(channel -> !channel.id().equals(ctx.channel().id()))
                .forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(msg))));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    static class WebSocketMessage {
        static final String HEADER_UPGRADE = "Upgrade";
        static final String HEADER_WEBSOCKET = "websocket";
        /**
         * 前端：收到进入房间的响应 包含房间信息
         */
        static final int WELCOME_MSG_TYPE = -1;
        /**
         * 前端：收到其他人发过来的消息
         */
        static final int NORMAL_MSG_TYPE = -2;
        /**
         * 前端：收到其他人离开房间的信息
         */
        static final int LEAVE_MSG_TYPE = -11000;
        /**
         * 前端：收到其他人进入房间的消息
         */
        static final int ENTER_MSG_TYPE = -10001;
        /**
         * 消息类型
         */
        private int type;
        /**
         * 用户名称
         */
        private String name;
        /**
         * 房间 ID
         */
        private long roomId;
        /**
         * 消息主体
         */
        private String body;
        /**
         * 错误码
         */
        private int errorCode;

        WebSocketMessage(int type, String name) {
            this.type = type;
            this.name = name;
            this.errorCode = 0;
        }

        WebSocketMessage(int type, String name, String body) {
            this.type = type;
            this.name = name;
            this.body = body;
            this.errorCode = 0;
        }
    }
}
