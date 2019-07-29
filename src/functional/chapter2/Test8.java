package functional.chapter2;

/**
 * @author Spencer
 * page 47
 */
public class Test8 {

    public static void main(String[] args) {
        double taxRate = 0.1;
        Function<Double, Double> addTax = price -> price + price * taxRate;
        System.out.println(addTax.apply(100.0));
        Function<Double, Function<Double, Double>> addTax2 = rate -> price -> price + price * rate;
        System.out.println(addTax2.apply(taxRate).apply(100.0));
    }
}
