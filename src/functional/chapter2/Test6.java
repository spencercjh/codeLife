package functional.chapter2;

/**
 * @author Spencer
 * page 42 43
 */
public class Test6 {
    public static void main(String[] args) {
        // <T,U,V> T是输入，V是输出
        System.out.println(Function.<Long, Double, Integer>higherComposeWithLambda().
                apply(number -> (int) (number * 3)).
                apply(number -> number + 2.0).
                apply(1L) == 9);
    }
}
