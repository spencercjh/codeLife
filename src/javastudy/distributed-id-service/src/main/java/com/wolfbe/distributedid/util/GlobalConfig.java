package com.wolfbe.distributedid.util;

/**
 * @author Andy
 */
public class GlobalConfig {

    public static String DEFAULT_HOST = "localhost";
    public static int DEFAULT_PORT = 16830;

    /**
     * HTTP协议和SDK协议服务器的端口
     */
    public static int HTTP_PORT = 16830;
    public static int SDKS_PORT = 16831;

    /**
     * HTTP协议和SDK协议的请求路径
     */
    public static String HTTP_REQUEST = "getid";
    public static String SDKS_REQUEST = "getid";

    /**
     * 数据中心的标识ID，取值范围：0~31
     * 机器或进程的标识ID，取值范围：0~31
     * 两个标识ID组合在分布式环境中必须唯一
     */
    public static long DATA_CENTER_ID = 1;
    public static long MACHINES_SIGN = 1;

    /**
     * 流量控制，表示每秒处理的并发数
     */
    public static int HANDLE_HTTP_TPS = 10000;
    public static int HANDLE_SDKS_TPS = 50000;
    public static int ACQUIRE_TIMEOUT_MILLIS = 5000;
}
