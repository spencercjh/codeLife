package com.wolfbe.distributedid.core;

/**
 * @author Andy
 */
public interface Server {
    /**
     * 服务启动
     */
    void start();

    /**
     * 服务关闭
     */
    void shutdown();
}
