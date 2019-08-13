package chapter2;

/**
 * @author Spencer
 * page 44 45
 */
public class Test7 {
    public static void main(String[] args) {
        System.out.println(Math.sin(Math.PI / 3.0) - myCos(Math.PI / 6.0) < Double.MIN_VALUE);
    }

    static Double myCos(Double arg) {
        return Function.<Double, Double, Double>higherComposeWithLambda().
                apply(Math::sin).
                apply(number -> Math.PI / 2 - number).
                apply(arg);
    }
}
