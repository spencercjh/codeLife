package chapter2.type4;

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:53
 */
class SingletonFourInJava {
    private static SingletonFourInJava INSTANCE = null;

    private SingletonFourInJava() {
    }

    public static synchronized SingletonFourInJava getInstance() {
        if (INSTANCE == null) {
            synchronized (INSTANCE) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonFourInJava();
                }
            }
        }
        return INSTANCE;
    }
}
