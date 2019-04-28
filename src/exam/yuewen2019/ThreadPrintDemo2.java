package exam.yuewen2019;

/**
 * @author SpencerCJH
 * @date 2019/4/22 22:00
 */

@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class ThreadPrintDemo2 {

    private static volatile int num = 0;
    private static volatile boolean flag = false;
    private static int MAX = 6;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (; MAX > num; ) {
                if (!flag && (num == 0 || ++num % 2 == 0)) {
                    System.out.println(num);
                    flag = true;
                }
            }
        }
        );
        Thread t2 = new Thread(() -> {
            for (; MAX > num; ) {
                if (flag && (++num % 2 != 0)) {
                    System.out.println(num);
                    flag = false;
                }
            }
        }
        );
        t1.start();
        t2.start();
    }
}