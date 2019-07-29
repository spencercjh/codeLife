package functional.chapter2;

import static functional.chapter2.Function.composeWithLambda;

/**
 * @author Spencer
 */
public class Test3 {
    public static void main(String[] args) {
        Function<Integer, Integer> triple = x -> x * 3;
        Function<Integer, Integer> square = x -> x * x;
        System.out.println(composeWithLambda(triple, square).apply(3));
    }


}
