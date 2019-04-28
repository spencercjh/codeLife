package exam.yuewen2019;

/**
 * @author SpencerCJH
 * @date 2019/4/22 22:00
 */

@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
class ThreadPrintDemo {
    private static int MAX = 6;

    public static void main(String[] args) {
        final ThreadPrintDemo demo = new ThreadPrintDemo();
        Thread t1 = new Thread(demo::print1);
        Thread t2 = new Thread(demo::print2);
        t1.start();
        t2.start();
    }

    private synchronized void print2() {
        for (int i = 1; i <= MAX; i += 2) {
            System.out.println(i);
            this.notify();
            if (i == MAX) {
                break;
            }
            try {
                this.wait();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void print1() {
        for (int i = 0; i <= MAX; i += 2) {
            System.out.println(i);
            this.notify();
            if (i == MAX) {
                break;
            }
            try {
                this.wait();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}