package functional.chapter2;

import org.jetbrains.annotations.NotNull;

/**
 * @author Spencer
 * Page 30
 */
public interface IntegerFunction {
    /**
     * 一个操作
     * @param arg
     * @return
     */
    int apply(int arg);

    /**
     * test default
     * @param o
     */
    default void request(@NotNull Object o) {
        System.out.println(o.toString());
    }

    static IntegerFunction compose(final IntegerFunction function1, final IntegerFunction function2) {
        return new IntegerFunction() {
            @Override
            public int apply(int arg) {
                return function1.apply(function2.apply(arg));
            }
        };
    }
}
