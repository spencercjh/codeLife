package functional.chapter2;

import static functional.chapter2.Function.composeWithLambda;

/**
 * @author Spencer
 * Page 32
 */
public class Test2 {
    public static void main(String[] args) {
        Function<Integer, Integer> triple = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg * 3;
            }
        };
        Function<Integer, Integer> square = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg * arg;
            }
        };
        System.out.println(composeWithLambda(triple, square).apply(3));
    }


}
