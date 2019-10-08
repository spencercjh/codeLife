package chapter2;

/**
 * @author Spencer
 * Page 37
 */
public class Test4 {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> addWithTwoAnonymousClass = new Function<Integer,
                Function<Integer, Integer>>() {
            @Override
            public Function<Integer, Integer> apply(Integer number1) {
                return new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer number2) {
                        return number1 + number2;
                    }
                };
            }
        };
        Function<Integer, Function<Integer, Integer>> addWithOneAnonymousClass = number1 ->
                new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer number2) {
                        return number1 + number2;
                    }
                };
        Function<Integer, Function<Integer, Integer>> addWithLambda = number1 -> number2
                -> number1 + number2;
        Function<Integer, Function<Integer, Integer>> multipleWithLambda = number1 -> number2
                -> number1 * number2;
        System.out.println(addWithLambda.apply(1).apply(1));
    }
}