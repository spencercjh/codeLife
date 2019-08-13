package chapter2.summery;

import chapter3.CollectionUtilities;

import java.util.List;
import java.util.function.Function;

/**
 * @author Spencer
 * Page 56 57
 */
public abstract class AbstractFunctions implements Function {
    /**
     * 恒等函数
     * @param <T> Input type
     * @return Function<T, T>
     */
    public static <T> Function<T, T> identity() {
        return (T t) -> t;
    }

    /**
     * 将2个function f、g 组合起来的静态方法
     * @param f   function1
     * @param g   function2
     * @param <T> Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<V, U>
     */
    public static <T, U, V> Function<V, U> compose(Function<T, U> f, Function<V, T> g) {
        return (V x) -> f.apply(g.apply(x));
    }

    /**
     * 将2个function f、g倒序组合起来的静态方法
     * @param f   function1
     * @param g   function2
     * @param <T> Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<T, V>
     */
    public static <T, U, V> Function<T, V> andThen(Function<T, U> f, Function<U, V> g) {
        return (T x) -> g.apply(f.apply(x));
    }

    /**
     * 将2个function f、g 组合起来的柯里化的静态方法
     * @param <T> Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<Function < T, U>,Function<Function<U, V>,Function<T, V>>>
     */
    public static <T, U, V> Function<Function<T, U>,
            Function<Function<U, V>,
                    Function<T, V>>> compose() {
        return (Function<T, U> x) -> (Function<U, V> y) -> y.compose(x);
    }

    /**
     * 将2个function f、g倒序组合起来的柯里化的静态方法
     * @param <T> Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<Function < T, U>,Function<Function<V, T>,Function<V, U>>>
     */
    public static <T, U, V> Function<Function<T, U>,
            Function<Function<V, T>,
                    Function<V, U>>> andThen() {
        return (Function<T, U> x) -> (Function<V, T> y) -> y.andThen(x);
    }

    /**
     * 将2个function f、g组合起来的高阶多态的柯里化的静态方法
     * @param <T> Polymorphic Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<Function < U, V>,Function<Function<T, U>,Function<T, V>>>
     */
    public static <T, U, V> Function<Function<U, V>,
            Function<Function<T, U>,
                    Function<T, V>>> higherCompose() {
        return (Function<U, V> x) -> (Function<T, U> y) -> (T z) -> x.apply(y.apply(z));
    }

    /**
     * 将2个function f、g倒序组合起来的高阶多态的柯里化的静态方法
     * @param <T> Polymorphic Input type
     * @param <U> Intermediate transition type
     * @param <V> Output type
     * @return Function<Function < T, U>,Function<Function<U, V>,Function<T, V>>>
     */
    public static <T, U, V> Function<Function<T, U>,
            Function<Function<U, V>,
                    Function<T, V>>> higherAndThen() {
        return (Function<T, U> x) -> (Function<U, V> y) -> (T z) -> y.apply(x.apply(z));
    }

    /**
     * 通过左折叠来复合函数
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> list) {
        return x -> CollectionUtilities.foldLeft(CollectionUtilities.reverse(list), x, (T t) -> (Function<T, T> function) -> function.apply(t));
    }

    public static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> list) {
        return x -> CollectionUtilities.foldRight(list, x, (Function<T, T> function) -> function::apply);
    }
}
