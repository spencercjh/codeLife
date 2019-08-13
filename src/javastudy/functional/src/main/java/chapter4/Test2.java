package chapter4;

import java.util.function.Function;

/**
 * @author Spencer
 * page 115
 */
public class Test2 {
    public static Function<Integer, Function<Integer, Integer>> add = new Function<Integer, Function<Integer, Integer>>() {
        @Override
        public Function<Integer, Integer> apply(Integer x) {
            return y -> {
                class AddHelper {
                    Function<Integer, Function<Integer, BaseTailCell<Integer>>> addHelper = a -> b -> b == 0 ?
                            BaseTailCell.ofReturn(a) :
                            BaseTailCell.ofSuspend(() -> this.addHelper.apply(a + 1).apply(b - 1));
                }
                return new AddHelper().addHelper.apply(x).apply(y).value();
            };
        }
    };

    public static void main(String[] args) {
        System.out.println(add.apply(3).apply(100));
    }
}
