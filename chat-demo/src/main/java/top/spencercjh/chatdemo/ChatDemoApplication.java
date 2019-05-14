package top.spencercjh.chatdemo;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.spencercjh.chatdemo.netty.NettyConfig;
import top.spencercjh.chatdemo.netty.ServerBootStrap;

import java.net.InetSocketAddress;

/**
 * @author spencercjh
 */
@SpringBootApplication
public class ChatDemoApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ChatDemoApplication.class);

    @Autowired
    private ServerBootStrap serverBootStrap;

    public static void main(String[] args) {
        SpringApplication.run(ChatDemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("Netty's ws server is listen: " + NettyConfig.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);
        ChannelFuture future = serverBootStrap.start(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> serverBootStrap.destroy()));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
