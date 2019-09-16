package com.wolfbe.distributedid;

import com.wolfbe.distributedid.core.SnowFlake;
import com.wolfbe.distributedid.http.HttpServer;
import com.wolfbe.distributedid.sdks.SdkServer;
import com.wolfbe.distributedid.util.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 两个服务器进程最好用同一个SnowFlake实例，
 * 部署在分布式环境时，SnowFlake的dataCenterId和machineId作为联合键必须全局唯一,
 * 否则多个节点的服务可能产生相同的ID
 * @author Andy
 */
public class ServerStartup {
    private static final Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    public static void main(String[] args) {
        long dataCenterId = GlobalConfig.DATA_CENTER_ID;
        long machineId = GlobalConfig.MACHINES_SIGN;

        if (args != null && args.length == 2) {
            dataCenterId = Long.parseLong(args[0]);
            machineId = Long.parseLong(args[1]);
        } else {
            logger.error(">>>>>You don't appoint the dataCenterId and machineId argument,will use default value");
        }

        final SnowFlake snowFlake = new SnowFlake(dataCenterId, machineId);

        // 启动Http服务器
        final HttpServer httpServer = new HttpServer(snowFlake);
        httpServer.init();
        httpServer.start();

        // 启动Sdk服务器
        final SdkServer sdkServer = new SdkServer(snowFlake);
        sdkServer.init();
        sdkServer.start();

        logger.info(String.format(new Date() + ">>>>>Server start success, SnowFlake dataCenterId is %d, machineId is %d",
                dataCenterId,
                machineId
        ));

        // 注册进程钩子，在JVM进程关闭前释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            httpServer.shutdown();
            logger.info(">>>>>>>>>> httpServer shutdown");
            sdkServer.shutdown();
            logger.info(">>>>>>>>>> sdkServer shutdown");
            System.exit(0);
        }));
    }
}
