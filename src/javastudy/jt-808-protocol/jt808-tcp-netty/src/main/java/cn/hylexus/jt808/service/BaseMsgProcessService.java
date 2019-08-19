package cn.hylexus.jt808.service;

import cn.hylexus.jt808.server.SessionManager;
import cn.hylexus.jt808.vo.Session;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseMsgProcessService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private SessionManager sessionManager;

    BaseMsgProcessService() {
        this.sessionManager = SessionManager.getInstance();
    }

    protected ByteBuf getByteBuf(byte[] arr) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
        byteBuf.writeBytes(arr);
        return byteBuf;
    }

    void sendToClient(Channel channel, byte[] arr) throws InterruptedException {
        ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(arr)).sync();
        if (!future.isSuccess()) {
            log.error("发送数据出错:{}", future.cause());
        }
    }

    private int getFlowId(Channel channel, int defaultValue) {
        Session session = this.sessionManager.findBySessionId(Session.buildId(channel));
        if (session == null) {
            return defaultValue;
        }

        return session.currentFlowId();
    }

    int getFlowId(Channel channel) {
        return this.getFlowId(channel, 0);
    }

}
