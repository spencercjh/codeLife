package top.spencercjh.exercise.function;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Spencer
 */
public class Difference {
    public static void main(String[] args) {
        Function<Integer, BiFunction<Integer, Integer, Integer>> fun = new Function<Integer, BiFunction<Integer, Integer, Integer>>() {
            @Override
            public BiFunction<Integer, Integer, Integer> apply(Integer x) {
                return new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer y, Integer z) {
                        return x + y + z;
                    }
                };
            }
        };
        Function<Integer, BiFunction<Integer, Integer, Integer>> same = x -> (y, z) -> x + y + z;
        System.out.println(fun.apply(1).apply(2, 3));
        System.out.println(same.apply(1).apply(2, 3));
    }
}
