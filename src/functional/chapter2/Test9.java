package functional.chapter2;

/**
 * @author Spencer
 * page 53
 */
public class Test9 {
    public static void main(String[] args) {
        Function<Double, Function<Double, Double>> payment = amount -> rate -> amount + amount * rate;
        // following payment can be replaced with (Double amount) -> (Double rate) -> amount + amount * rate
        System.out.println(Function.reverseArgs(payment).apply(0.1).getClass().getSimpleName());
        System.out.println(Function.reverseArgs(payment).apply(0.1).apply(100.0));
    }
}
