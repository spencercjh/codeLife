package chapter7;

import chapter6.Option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
@SuppressWarnings("unused")
public interface BaseResult<V> {
    /**
     * get value or default value
     * @param defaultValue default value
     * @return this value or default value
     */
    V getOrElse(final V defaultValue);

    /**
     * get value or a suspended default value
     * @param defaultValue default value
     * @return this value or a suspended default value
     */
    V getOrElse(final Supplier<V> defaultValue);

    /**
     * convert a Result<V> to Result<U> by function from V to U
     * @param f   function
     * @param <U> target Result value type
     * @return target Result
     */
    <U> Result<U> map(Function<V, U> f);

    /**
     * convert a Result<V> to Result<U> by function from V to Result<U>
     * @param f   function
     * @param <U> target Result value type
     * @return target Result
     */
    <U> Result<U> flatMap(Function<V, Result<U>> f);

    /**
     * convert result to option
     * @return AbstractOption
     */
    Option<V> toOption();

    /**
     * convert this failure to a new failure with a message
     * @param s message
     * @return a new failure
     */
    Result<V> mapFailure(String s);

    /**
     * convert this failure to a new failure with a message and a exception
     * @param s message
     * @param e exception
     * @return a new failure
     */
    Result<V> mapFailure(String s, Exception e);

    /**
     * convert this failure to a new failure with a message
     * @param e exception
     * @return a new failure
     */
    Result<V> mapFailure(Exception e);

    /**
     * apply the consumer to value
     * @param effect
     */
    void forEach(Consumer<V> effect);
}
