package chapter2.summery;

/**
 * @author Spencer
 * page 56 57
 */
interface Function<T, U> {
    /**
     * 一个操作
     * @param arg T
     * @return U
     */
    U apply(T arg);

    /**
     * 将1个function和this组合起来，先执行f的方法再将结果传给this
     * @param f Function<V, T>
     * @param <V> Input Type
     * @return Function
     */
    default <V> Function<V, U> compose(Function<V, T> f) {
        return (V x) -> apply(f.apply(x));
    }

    /**
     * 将一个function和this反序组合起来
     * @param f Function<U, V>
     * @param <V> Input Type
     * @return Function<T, V>
     */
    default <V> Function<T, V> andThen(Function<U, V> f) {
        return (T x) -> f.apply(apply(x));
    }
}
