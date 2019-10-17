package chapter2.type2;

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:43
 */
class SingletonTwoInJava {
    private static SingletonTwoInJava INSTANCE = null;

    private SingletonTwoInJava() {
    }

    public static SingletonTwoInJava getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonTwoInJava();
        }
        return INSTANCE;
    }
}
