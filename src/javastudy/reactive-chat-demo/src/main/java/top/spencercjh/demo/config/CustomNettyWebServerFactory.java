package top.spencercjh.demo.config;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.server.HttpServer;

/**
 * @author Spencer
 */
@Slf4j
@Configuration
public class CustomNettyWebServerFactory {
    final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Value("${server.port}")
    private int port;

    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        log.info("server run on port:{}", port);
        NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
        webServerFactory.addServerCustomizers(new PortCustomizer(), new BootstrapCustomizer());
        return webServerFactory;
    }

    class PortCustomizer implements NettyServerCustomizer {
        @Override
        public HttpServer apply(HttpServer httpServer) {
            return httpServer.port(port);
        }
    }

    class BootstrapCustomizer implements NettyServerCustomizer {
        @Override
        public HttpServer apply(HttpServer httpServer) {
            EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(0);
            EventLoopGroup workEventLoopGroup = new NioEventLoopGroup(0);
            return httpServer.tcpConfiguration(tcpServer ->
                    tcpServer.bootstrap(serverBootstrap ->
                            serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup)
                                    .channel(NioServerSocketChannel.class)
                                    .childHandler(new ChannelInitializer<Channel>() {
                                        @Override
                                        protected void initChannel(Channel ch) throws Exception {
                                            ChannelPipeline pipeline = ch.pipeline();
                                            // 这里不能再加encode decode的handler了，实测会重复编码，无法解析了
                                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());

                                        }
                                    })
                                    .option(ChannelOption.SO_BACKLOG, 128)
                                    .childOption(ChannelOption.SO_KEEPALIVE, true))
            );
        }
    }
}
