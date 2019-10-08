package top.spencercjh.chatdemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author Spencer
 */
@Component
public class Netty {

    /**
     * 存储所有连接的 channel
     */
    final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static String WS_HOST;

    public static int WS_PORT;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    /**
     * spring boot 不允许/不支持把值注入到静态变量中 所以采用 setter 的方式注入
     */
    @Value("${netty.host}")
    public void setWebSocketHost(String host) {
        WS_HOST = host;
    }

    @Value("${netty.port}")
    public void setWebSocketPort(int port) {
        WS_PORT = port;
    }

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        Netty.CHANNEL_GROUP.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    static class ServerInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel channel) {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast("http-codec", new HttpServerCodec());
            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
            pipeline.addLast("handler", new WebSocketHandler());
        }
    }

}
