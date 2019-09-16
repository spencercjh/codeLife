package com.wolfbe.distributedid.core;

/**
 * twitter的snowflake算法 -- java实现
 * 协议格式： 0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 * @author Andy
 */
public class SnowFlake {

    /**
     * 起始的时间戳，可以修改为服务第一次启动的时间
     * 一旦服务已经开始使用，起始时间戳就不应该改变
     */
    private final static long START_STMP = 1484754361114L;

    /**
     * 每一部分占用的位数
     * <p>
     * 序列号占用的位数
     * <p>
     * 机器标识占用的位数
     * <p>
     * 数据中心占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;
    /**
     * 数据中心
     * <p>
     * 机器标识
     * <p>
     * 序列号
     * <p>
     * 上一次时间戳
     */
    private long dataCenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;


    /**
     * 通过单例模式来获取实例
     * 分布式部署服务时，数据节点标识和机器标识作为联合键必须唯一
     * @param dataCenterId 数据节点标识ID
     * @param machineId    机器标识ID
     */
    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        long start = System.currentTimeMillis();
        for (int i = 0; i < (1 << 18); i++) {
            System.out.println(i + ": " + snowFlake.nextId());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 产生下一个ID
     * @return id
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;
        // 时间戳部分
        return (currStamp - START_STMP) << TIMESTAMP_LEFT
                // 数据中心部分
                | dataCenterId << DATA_CENTER_LEFT
                // 机器标识部分
                | machineId << MACHINE_LEFT
                // 序列号部分
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
