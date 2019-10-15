package top.spencercjh.jt808.server;

import top.spencercjh.jt808.common.TPMSConsts;
import top.spencercjh.jt808.service.codec.Decoder4LoggingOnly;
import top.spencercjh.jt808.service.handler.tcpServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author SpencerCJH
 * @date 2019/7/22 13:15
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast("idleStateHandler", new IdleStateHandler(
                TPMSConsts.TCP_CLIENT_IDLE_MINUTES,
                0,
                0,
                TimeUnit.MINUTES));
        socketChannel.pipeline().addLast(new Decoder4LoggingOnly());
        // 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
        socketChannel.pipeline().addLast(
                new DelimiterBasedFrameDecoder(1024,
                        Unpooled.copiedBuffer(new byte[]{0x7e}),
                        Unpooled.copiedBuffer(new byte[]{0x7e, 0x7e})));
        socketChannel.pipeline().addLast(new tcpServerHandler());
    }
}
