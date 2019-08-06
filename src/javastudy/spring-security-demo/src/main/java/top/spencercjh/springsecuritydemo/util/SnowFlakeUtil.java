package top.spencercjh.springsecuritydemo.util;


/**
 * twitter的snowflake算法 -- java实现
 * @author beyond
 * @date 2016/11/26
 */
public class SnowFlakeUtil {
    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;
    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 每一部分占用的位数
     * 数据中心占用的位数
     */
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
    volatile private static SnowFlakeUtil INSTANCE = null;
    /**
     * 数据中心
     */
    private long dataCenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTimeStamp = -1L;

    private SnowFlakeUtil(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public static String getSessionId() {
        if (null != INSTANCE) {
            return String.valueOf(INSTANCE.nextId());
        } else {
            synchronized (SnowFlakeUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new SnowFlakeUtil(1, 1);
                }
            }
        }
        return String.valueOf(INSTANCE.nextId());
    }

    /**
     * 产生下一个ID
     * @return id
     */
    private synchronized long nextId() {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp < lastTimeStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currentTimeStamp == lastTimeStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currentTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastTimeStamp = currentTimeStamp;
        //时间戳部分
        return (currentTimeStamp - START_STAMP) << TIMESTAMP_LEFT
                //数据中心部分
                | dataCenterId << DATA_CENTER_LEFT
                //机器标识部分
                | machineId << MACHINE_LEFT
                //序列号部分
                | sequence;
    }

    private long getNextMill() {
        long mill = System.currentTimeMillis();
        while (mill <= lastTimeStamp) {
            mill = System.currentTimeMillis();
        }
        return mill;
    }
}
