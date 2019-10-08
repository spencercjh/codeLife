package chapter9;

import chapter7.Result;
import chapter8.AdvancedList;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
public interface Stream<T> {
    /**
     * Stream head element
     * @return value
     */
    T head();

    /**
     * Stream rest part without head
     * @return stream
     */
    Stream<T> tail();

    /**
     * whether stream is empty
     * @return true or false
     */
    Boolean isEmpty();

    /**
     * get Result-wrapped Stream head
     * @return a Result-wrapped{@link Result} Stream head
     */
    Result<T> headOption();

    /**
     * get stream with first n elements
     * @param n element amounts
     * @return the new Stream
     */
    Stream<T> take(int n);

    /**
     * drop first n elements
     * @param n dropped elements amount
     * @return the new Stream
     */
    Stream<T> drop(int n);

    /**
     * convert Stream to List
     * @return functional list{@link AdvancedList}
     */
    AdvancedList<T> toList();

    /**
     * As long as the conditions match, it will return a Stream containing all the starting elements
     * @param predicate function to apply to each element
     * @return the new Stream
     */
    Stream<T> takeWhile(Function<T, Boolean> predicate);

    /**
     * drop elements matched the predicate and return a new Stream
     * @param predicate function to apply to each element
     * @return the new Stream
     */
    Stream<T> dropWhile(Function<T, Boolean> predicate);

    /**
     * 对Stream内元素以求值，直到匹配predicate为止返回true，否则返回false
     * @param predicate function from T to Boolean
     * @return true or false
     */
    boolean exists(Function<T, Boolean> predicate);

    /**
     * foldRight like List but use Supplier instead of base type
     * @param supplier Supplier
     * @param f        function to apply to each element
     * @param <U>      target result type
     * @return result
     */
    <U> U foldRight(Supplier<U> supplier, Function<T, Function<Supplier<U>, U>> f);

    /**
     * implement headOption by FoldRight
     * @return Result-wrapped head
     */
    Result<T> headOptionViaFoldRight();

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the elements of this stream.
     * @param f   function to apply to each element
     * @param <U> The element type of the new stream
     * @return the new Stream
     */
    <U> Stream<U> map(Function<T, U> f);

    /**
     * Returns a stream consisting of the elements of this stream that match
     * the given predicate.
     * @param predicate predicate to apply to each element to determine if it
     *                  should be included
     * @return the new Stream
     */
    Stream<T> filter(Function<T, Boolean> predicate);

    /**
     * Returns a stream with another stream appended to its head.
     * @param streamSupplier another stream's Supplier
     * @return the nwe Stream
     */
    Stream<T> append(Supplier<Stream<T>> streamSupplier);

    /**
     * Returns a stream consisting of the results of replacing each element of
     * this stream with the contents of a mapped stream produced by applying
     * the provided mapping function to each element.
     * @param f   function to apply to each element which produces a stream
     *            of new values
     * @param <U> The element type of the new stream
     * @return the new stream
     */
    <U> Stream<U> flatMap(Function<T, Stream<U>> f);

    /**
     * Returns an {@link Result} describing the first element that match the predicate of this stream,
     * or an empty {@code Result} if the stream is empty.  If the stream has
     * no encounter order, then any element may be returned.
     * @param predicate predicate to apply to each element to determine if it
     *                  should be return
     * @return Result-wrapped element
     */
    Result<T> find(Function<T, Boolean> predicate);
}
