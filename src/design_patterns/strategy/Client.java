package design_patterns.strategy;

/**
 * @author SpencerCJH
 * @date 2019/4/28 22:11
 */
public class Client {
    public static void main(String[] args) {
        System.out.println(Calculator.ADD.execute(5, 10));
        System.out.println(Calculator.SUB.execute(10, 5));
    }
}
