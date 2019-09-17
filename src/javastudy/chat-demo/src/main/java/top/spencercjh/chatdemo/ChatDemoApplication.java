package top.spencercjh.chatdemo;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.spencercjh.chatdemo.netty.Netty;

import java.net.InetSocketAddress;

/**
 * @author spencercjh
 */
@SpringBootApplication
public class ChatDemoApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ChatDemoApplication.class);

    @Autowired
    private Netty netty;

    public static void main(String... args) {
        SpringApplication.run(ChatDemoApplication.class, args);
    }


    @Override
    public void run(String... args) {
        logger.info("Netty's WebSocket server is listen: " + Netty.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(Netty.WS_HOST, Netty.WS_PORT);
        ChannelFuture future = netty.start(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> netty.destroy()));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
