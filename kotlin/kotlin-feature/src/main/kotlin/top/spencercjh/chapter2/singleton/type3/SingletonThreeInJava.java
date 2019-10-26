package top.spencercjh.chapter2.singleton.type3;

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:49
 */
class SingletonThreeInJava {
    private static SingletonThreeInJava INSTANCE = null;

    private SingletonThreeInJava() {
    }

    public static synchronized SingletonThreeInJava getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonThreeInJava();
        }
        return INSTANCE;
    }
}
