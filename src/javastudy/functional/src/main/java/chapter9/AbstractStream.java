package chapter9;

import chapter4.BaseTailCell;
import chapter7.Result;
import chapter8.AdvancedList;
import lombok.extern.slf4j.Slf4j;
import util.Tuple;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "WeakerAccess"})
@Slf4j
public abstract class AbstractStream<T> implements Stream<T> {
    /**
     * empty stream constant
     */
    private static final AbstractStream EMPTY = new Empty();

    private AbstractStream() {
    }

    /**
     * a factory method of Stream
     * @param head Supplier-wrapped head
     * @param tail Supplier-wrapped rest part
     * @param <A>  Stream value type
     * @return Stream
     */
    public static <A> Stream<A> construct(Supplier<A> head, Supplier<Stream<A>> tail) {
        return new Constructor<>(head, tail);
    }

    /**
     * empty stream
     * @param <A> stream value type
     * @return empty stream
     */
    @SuppressWarnings("unchecked")
    public static <A> Stream<A> empty() {
        return EMPTY;
    }

    /**
     * limitless integer stream begin with start
     * @param start beginning
     * @return Limitless Integer Stream
     * @see #iterator(Object, Function)
     */
    public static Stream<Integer> integerStream(int start) {
        return unfold(start, x -> Result.success(new Tuple<>(x, x + 1)));
    }

    /**
     * limitless object stream begin with start object
     * @param a   origin object
     * @param <A> stream element type
     * @return Limitless Object A Stream
     * @see #iterator(Object, Function)
     */
    public static <A> Stream<A> repeat(A a) {
        return iterator(a, x -> x);
    }

    /**
     * Returns an infinite sequential ordered {@code Stream} produced by iterative
     * application of a function {@code f} to an suspended initial element {@code seed},
     * producing a {@code Stream} consisting of {@code seed}, {@code f(seed)},
     * {@code f(f(seed))}, etc.
     * @param seed the Supplier-wrapped initial element
     * @param f    a function to be applied to to the previous element to produce
     *             a new element
     * @param <A>  the type of stream elements
     * @return a new sequential {@code Stream}
     */
    public static <A> Stream<A> iterator(Supplier<A> seed, Function<A, A> f) {
        return construct(seed, () -> iterator(f.apply(seed.get()), f));
    }

    /**
     * Returns an infinite sequential ordered {@code Stream} produced by iterative
     * application of a function {@code f} to an initial element {@code seed},
     * producing a {@code Stream} consisting of {@code seed}, {@code f(seed)},
     * {@code f(f(seed))}, etc.
     * @param seed the initial element
     * @param f    a function to be applied to to the previous element to produce
     *             a new element
     * @param <A>  the type of stream elements
     * @return a new sequential {@code Stream}
     */
    public static <A> Stream<A> iterator(A seed, Function<A, A> f) {
        return construct(() -> seed, () -> iterator(f.apply(seed), f));
    }

    /**
     * 以一个起始状态S类型和一个从S到{@code Result<Tuple<A,S>>}的函数为参数并返回A的流
     * @param start the initial element
     * @param f     a function to be applied to to the origin element to produce
     *              a new {@code Result<Tuple<A,S>>}
     * @param <A>   target Stream element type
     * @param <S>   origin type
     * @return the new Stream
     */
    public static <A, S> Stream<A> unfold(S start, Function<S, Result<Tuple<A, S>>> f) {
        return f.apply(start).map(tuple -> construct(() -> tuple.first, () -> unfold(tuple.second, f))).getOrElse(AbstractStream.empty());
    }

    /**
     * Gracefully use the unfold method to implement an infinite stream of Fibonacci numbers
     * @return the Fibonacci Stream
     */
    public static Stream<Integer> fibs() {
        return unfold(new Tuple<>(1, 1), tuple -> Result.success(new Tuple<>(tuple.first,
                new Tuple<>(tuple.second, tuple.first + tuple.second))));
    }

    @Override
    public AdvancedList<T> toList() {
        return toList(this, AdvancedList.list()).value().reverse();
    }

    @Override
    public Stream<T> append(Supplier<Stream<T>> streamSupplier) {
        return foldRight(streamSupplier, x -> y -> construct(() -> x, y));
    }

    private BaseTailCell<AdvancedList<T>> toList(Stream<T> stream, AdvancedList<T> acc) {
        return stream.isEmpty() ?
                BaseTailCell.ofReturn(acc) :
                BaseTailCell.ofSuspend(() -> toList(stream.tail(), AdvancedList.construct(stream.head(), acc)));
    }

    BaseTailCell<Boolean> exists(Stream<T> stream, Function<T, Boolean> predicate) {
        log.debug("present head is {}", stream.headOption());
        return stream.isEmpty() ?
                BaseTailCell.ofReturn(false) :
                predicate.apply(stream.head()) ?
                        BaseTailCell.ofReturn(true) :
                        BaseTailCell.ofSuspend(() -> exists(stream.tail(), predicate));
    }

    BaseTailCell<Stream<T>> dropWhile(Stream<T> acc, Function<T, Boolean> predicate) {
        log.debug("present head is {}", acc.headOption());
        return acc.isEmpty() ?
                BaseTailCell.ofReturn(acc) :
                predicate.apply(acc.head()) ?
                        BaseTailCell.ofSuspend(() -> dropWhile(acc.tail(), predicate)) :
                        BaseTailCell.ofReturn(acc);
    }

    BaseTailCell<Stream<T>> drop(Stream<T> acc, int n) {
        log.debug("present head is {}", acc.headOption());
        return n <= 0 ?
                BaseTailCell.ofReturn(acc) :
                BaseTailCell.ofSuspend(() -> drop(acc.tail(), n - 1));
    }

    /**
     * an empty Stream
     * @param <T> stream value type
     */
    private static class Empty<T> extends AbstractStream<T> {
        @Override
        public T head() {
            throw new IllegalStateException("head called on empty");
        }

        @Override
        public Stream<T> tail() {
            throw new IllegalStateException("tail called on empty");
        }

        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public Result<T> headOption() {
            return Result.empty();
        }

        @Override
        public Stream<T> take(int n) {
            return this;
        }

        @Override
        public Stream<T> drop(int n) {
            return this;
        }

        @Override
        public Stream<T> takeWhile(Function<T, Boolean> predicate) {
            return this;
        }

        @Override
        public Stream<T> dropWhile(Function<T, Boolean> predicate) {
            return this;
        }

        @Override
        public boolean exists(Function<T, Boolean> predicate) {
            return false;
        }

        @Override
        public <U> U foldRight(Supplier<U> supplier, Function<T, Function<Supplier<U>, U>> f) {
            return supplier.get();
        }

        @Override
        public Result<T> headOptionViaFoldRight() {
            return Result.empty();
        }

        @Override
        public <U> Stream<U> map(Function<T, U> f) {
            throw new IllegalStateException("map called on empty");
        }

        @Override
        public Stream<T> filter(Function<T, Boolean> predicate) {
            return this;
        }

        @Override
        public <U> Stream<U> flatMap(Function<T, Stream<U>> f) {
            throw new IllegalStateException("flatMap called on empty");
        }

        @Override
        public Result<T> find(Function<T, Boolean> predicate) {
            return Result.empty();
        }
    }

    /**
     * a not-null Stream
     * @param <T> stream value type
     */
    private static class Constructor<T> extends AbstractStream<T> {
        private final Supplier<T> head;
        private final Supplier<Stream<T>> tail;
        private Result<T> h;
        private Stream<T> t;

        private Constructor(Supplier<T> head, Supplier<Stream<T>> tail) {
            this.head = head;
            this.tail = tail;
            this.h = Result.empty();
        }

        @SuppressWarnings("unused")
        private Constructor(T head, Supplier<Stream<T>> tail) {
            this.head = () -> head;
            this.tail = tail;
            this.h = Result.success(head);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Constructor<?> that = (Constructor<?>) o;
            return Objects.equals(head, that.head) &&
                    Objects.equals(tail, that.tail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(head, tail);
        }

        @Override
        public T head() {
            return h.getOrElse(head.get());
        }

        @Override
        public Stream<T> tail() {
            if (t == null) {
                t = tail.get();
            }
            return t;
        }

        @Override
        public Boolean isEmpty() {
            return false;
        }

        @Override
        public Result<T> headOption() {
            return Result.success(head());
        }

        @Override
        public Stream<T> take(int n) {
            log.debug("present head is {}", headOption());
            return n <= 0 ?
                    AbstractStream.empty() :
                    AbstractStream.construct(head, () -> tail().take(n - 1));
        }

        @Override
        public Stream<T> drop(int n) {
            return drop(this, n).value();
        }

        @Override
        public Stream<T> takeWhile(Function<T, Boolean> predicate) {
            log.debug("present head is {}", headOption());
            return predicate.apply(head()) ?
                    AbstractStream.construct(head, () -> tail().takeWhile(predicate)) :
                    empty();
        }

        @Override
        public Stream<T> dropWhile(Function<T, Boolean> predicate) {
            return dropWhile(this, predicate).value();
        }

        @Override
        public boolean exists(Function<T, Boolean> predicate) {
            return exists(this, predicate).value();
        }

        @Override
        public <U> U foldRight(Supplier<U> supplier, Function<T, Function<Supplier<U>, U>> f) {
            return f.apply(head()).apply(() -> tail().foldRight(supplier, f));
        }

        @Override
        public Result<T> headOptionViaFoldRight() {
            return foldRight(Result::empty, x -> ignore -> Result.success(x));
        }

        @Override
        public <U> Stream<U> map(Function<T, U> f) {
            return foldRight(AbstractStream::empty, x -> (Supplier<Stream<U>> y) -> AbstractStream.construct(() -> f.apply(x), y));
        }

        @Override
        public Stream<T> filter(Function<T, Boolean> predicate) {
            return foldRight(AbstractStream::empty, (T x) -> (Supplier<Stream<T>> y) ->
                    predicate.apply(x) ? AbstractStream.construct(() -> x, y) : y.get());
        }

        @Override
        public <U> Stream<U> flatMap(Function<T, Stream<U>> f) {
            return foldRight(AbstractStream::empty, (T x) -> (Supplier<Stream<U>> y) -> f.apply(x).append(y));
        }

        @Override
        public Result<T> find(Function<T, Boolean> predicate) {
            return filter(predicate).headOption();
        }
    }
}
