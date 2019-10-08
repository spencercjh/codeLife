package top.spencercjh.demo.web;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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

    @Value("${server.port}")
    private int port;

    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
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
                                    .option(ChannelOption.SO_BACKLOG, 128)
                                    .childOption(ChannelOption.SO_KEEPALIVE, true))
            );
        }
    }
}
