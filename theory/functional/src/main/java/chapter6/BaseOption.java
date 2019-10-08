package chapter6;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
public interface BaseOption<A> {
    /**
     * 如果包含的值存在就将其返回否则就抛出异常
     * @return value
     */
    A getOrThrow();

    /**
     * 如果包含的值存在就将其返回否则就返回一个默认值
     * @param defaultValue suspended defaultValue
     * @return value
     */
    A getOrElse(Supplier<A> defaultValue);

    /**
     * 通过应用从A到B的函数function将AbstractOption<A>应用到AbstractOption<B>
     * @param f   function
     * @param <B> return AbstractOption value type
     * @return AbstractOption<B>
     */
    <B> Option<B> map(Function<A, B> f);

    /**
     * 接收一个从A到AbstractOption<B>的函数为参数，并返回一个AbstractOption<B>
     * @param f   function
     * @param <B> return AbstractOption value type
     * @return AbstractOption<B>
     */
    <B> Option<B> flatMap(Function<A, Option<B>> f);

    /**
     * 如果包含的值存在就将其返回否则就返回一个AbstractOption的默认值
     * @param defaultValue default AbstractOption<A>
     * @return default AbstractOption<A>
     */
    @Deprecated
    Option<A> orElse(Supplier<Option<A>> defaultValue);

    /**
     * 符合function的条件才返回value
     * @param f function
     * @return value
     */
    Option<A> filter(Function<A, Boolean> f);
}
