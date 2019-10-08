package cn.hylexus.jt808.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServer {

    private Logger log = LoggerFactory.getLogger(getClass());
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private int port = 8080;

    public TCPServer() {
    }

    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer();
        server.startServer();
    }

    private void bind() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).
                channel(NioServerSocketChannel.class).
                childHandler(new ServerInitializer()).
                option(ChannelOption.SO_BACKLOG, 128).
                childOption(ChannelOption.SO_KEEPALIVE, true);
        log.info("TCP服务启动完毕,port={}", port);
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
    }

    public synchronized void startServer() {
        if (isRunning) {
            throw new IllegalStateException(getClass().getSimpleName() + " is already started .");
        }
        isRunning = true;

        new Thread(() -> {
            try {
                bind();
            } catch (Exception e) {
                log.info("TCP服务启动出错:{}", e.getMessage());
                e.printStackTrace();
            }
        }, getClass().getSimpleName()).start();
    }

    @SuppressWarnings("unused")
    public synchronized void stopServer() {
        if (!isRunning) {
            throw new IllegalStateException(getName() + " is not yet started .");
        }
        isRunning = false;

        try {
            Future<?> future = workerGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("TCP服务已经停止...");
    }

    private String getName() {
        return "TCP-Server";
    }
}