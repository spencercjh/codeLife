package chapter2;

/**
 * @author Spencer
 * Page 38 39 40 41
 */
public class Test5 {
    public static void main(String[] args) {
        Function<Function<Integer, Integer>, Function<Function<Integer, Integer>,
                Function<Integer, Integer>>> composeWithLambda = function1 -> function2 -> number ->
                function1.apply(function2.apply(number));
        Function<Function<Integer, Integer>, Function<Function<Integer, Integer>,
                Function<Integer, Integer>>> composeWithAnonymousClass = new Function<
                Function<Integer, Integer>, Function<Function<Integer, Integer>,
                Function<Integer, Integer>>>() {
            @Override
            public Function<Function<Integer, Integer>, Function<Integer, Integer>> apply(
                    Function<Integer, Integer> function1) {
                return new Function<Function<Integer, Integer>, Function<Integer, Integer>>() {
                    @Override
                    public Function<Integer, Integer> apply(Function<Integer, Integer> function2) {
                        return new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer number) {
                                return function1.apply(function2.apply(number));
                            }
                        };
                    }
                };
            }
        };
        // f(x)=3*x;
        Function<Integer, Integer> tripleWithLambda = number -> number * 3;
        // g(x)=x*x
        Function<Integer, Integer> squareWithLambda = number -> number * number;
        // h(x)=g(f(x))
        Function<Integer, Integer> function = composeWithLambda.apply(squareWithLambda).apply(tripleWithLambda);
        // h(2)
        System.out.println(function.apply(2));
    }
}
