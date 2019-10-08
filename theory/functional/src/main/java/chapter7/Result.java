package chapter7;

import chapter6.Option;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */

@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "WeakerAccess", "unused"})
public abstract class Result<V> implements Serializable, BaseResult<V> {
    private static final Result EMPTY = new Empty();

    private Result() {
    }

    /**
     * 将从A到B的函数转换为从Result A到Result B的函数
     * @param f   function from A to B
     * @param <A> type A
     * @param <B> type B
     * @return function from Result A to Result B
     */
    public static <A, B> Function<Result<A>, Result<B>> lift(Function<A, B> f) {
        return (Result<A> x) -> {
            try {
                return x.map(f);
            } catch (Exception e) {
                e.printStackTrace();
                return failure(e);
            }
        };
    }

    /**
     * 将从A到B到C的函数转换为从Result A到Result B到Result C的函数
     * @param f   function from A to B to C
     * @param <A> type A
     * @param <B> type B
     * @param <C> type C
     * @return function from Result A to Result B to Result C
     */
    public static <A, B, C> Function<Result<A>, Function<Result<B>, Result<C>>> lift2(Function<A, Function<B, C>> f) {
        return (Result<A> a) -> (Result<B> b) -> a.map(f).flatMap(b::map);
    }

    /**
     * 将从A到B到C到D的函数转换为从Result A到Result B到Result C到Result D的函数
     * @param f   function from A to B to C to D
     * @param <A> type A
     * @param <B> type B
     * @param <C> type C
     * @param <D> type D
     * @return function from Result A to Result B to Result C to Result D
     */
    public static <A, B, C, D> Function<Result<A>, Function<Result<B>, Function<Result<C>, Result<D>>>> lift3(Function<A, Function<B, Function<C, D>>> f) {
        return (Result<A> a) -> (Result<B> b) -> (Result<C> c) -> a.map(f).flatMap(b::map).flatMap(c::map);
    }

    /**
     * 接收一个Result A和一个Result B以及一个从A到B到C的函数，返回一个Result C
     * @param a   Result A
     * @param b   Result B
     * @param f   function from A to B to C
     * @param <A> type A
     * @param <B> type B
     * @param <C> type C
     * @return Result C
     */
    public static <A, B, C> Result<C> map2(Result<A> a, Result<B> b, Function<A, Function<B, C>> f) {
        return lift2(f).apply(a).apply(b);
    }

    public static <T> Result<T> of(T value) {
        return value != null ? success(value) : failure("null value");
    }

    public static <T> Result<T> of(T value, String message) {
        return value != null ? success(value) : failure(message);
    }

    public static <T> Result<T> of(T value, Function<T, Boolean> predicate) {
        try {
            return predicate.apply(value) ? success(value) : empty();
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = String.format("Exception while evaluating predicate: %s", value);
            return failure(new IllegalStateException(errorMessage, e));
        }
    }

    public static <T> Result<T> of(T value, Function<T, Boolean> predicate, String message) {
        try {
            return predicate.apply(value) ? success(value) : failure(String.format(message, value));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = String.format("Exception while evaluating predicate: %s", String.format(message, value));
            return failure(new IllegalStateException(errorMessage, e));
        }
    }

    public static <T> Result<T> failure() {
        return new Failure<>();
    }

    public static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    public static <T> Result<T> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> empty() {
        return EMPTY;
    }

    /**
     * get this Result or a suspended default Result
     * @param defaultValue a suspended default Result
     * @return this or a suspended default Result
     */
    public Result<V> ofElse(Supplier<Result<V>> defaultValue) {
        return map((V x) -> this).getOrElse(defaultValue);
    }

    /**
     * filter
     * @param f function from V to Boolean
     * @return this or a failure
     */
    public Result<V> filter(Function<V, Boolean> f) {
        return flatMap((V x) -> f.apply(x) ? this : failure("Condition not matched"));
    }

    public boolean isSuccess() {
        return this.getClass().equals(Success.class);
    }

    public boolean isFailure() {
        return this.getClass().equals(Failure.class);
    }

    public boolean isEmpty() {
        return this.getClass().equals(Empty.class);
    }

    private static class Empty<V> extends Result<V> {
        public Empty() {
            super();
        }

        @Override
        public V getOrElse(V defaultValue) {
            return defaultValue;
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return empty();
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return empty();
        }

        @Override
        public Option<V> toOption() {
            return Option.none();
        }

        @Override
        public Result<V> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<V> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<V> mapFailure(Exception e) {
            return this;
        }

        @Override
        public void forEach(Consumer<V> effect) {
            System.err.println("empty called forEach");
        }

        @Override
        public void forEachOrThrow(Consumer<V> effect) {
            System.err.println("empty called forEachOrThrow");
        }

        @Override
        public Result<RuntimeException> forEachOrException(Consumer<V> effect) {
            return empty();
        }

        @Override
        public String toString() {
            return "Empty()";
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    private static class Failure<V> extends Empty<V> {
        private final RuntimeException exception;

        private Failure() {
            super();
            exception = new RuntimeException();
        }

        private Failure(String message) {
            super();
            exception = new RuntimeException(message);
        }

        private Failure(Exception e) {
            super();
            this.exception = new IllegalStateException(e.getMessage(), e);
        }

        @Override
        public String toString() {
            return String.format("Failure(%s)", exception.getMessage());
        }

        @Override
        public V getOrElse(V defaultValue) {
            return defaultValue;
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return failure(exception);
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return failure(exception);
        }

        @Override
        public Option<V> toOption() {
            return Option.none();
        }

        @Override
        public Result<V> mapFailure(String s) {
            return failure(new IllegalStateException(s, exception));
        }

        @Override
        public Result<V> mapFailure(String s, Exception e) {
            return failure(new IllegalStateException(s, e));
        }

        @Override
        public Result<V> mapFailure(Exception e) {
            return failure(new IllegalStateException(exception));
        }

        @Override
        public void forEachOrThrow(Consumer<V> effect) {
            throw exception;
        }

        @Override
        public Result<RuntimeException> forEachOrException(Consumer<V> effect) {
            return success(exception);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Failure<?> failure = (Failure<?>) o;
            return Objects.equals(exception, failure.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }
    }

    private static class Success<V> extends Result<V> {
        private final V value;

        private Success(V value) {
            super();
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Success(%s)", value.toString());
        }

        @Override
        public V getOrElse(V defaultValue) {
            return value;
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return value;
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            try {
                return Result.success(f.apply(value));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(e);
            }
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            try {
                return map(f).getOrElse(failure());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(e);
            }
        }

        @Override
        public Option<V> toOption() {
            return Option.some(value);
        }

        @Override
        public Result<V> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<V> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<V> mapFailure(Exception e) {
            return this;
        }

        @Override
        public void forEach(Consumer<V> effect) {
            effect.accept(value);
        }

        @Override
        public void forEachOrThrow(Consumer<V> effect) {
            effect.accept(value);
        }

        @Override
        public Result<RuntimeException> forEachOrException(Consumer<V> effect) {
            effect.accept(value);
            return empty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Success<?> success = (Success<?>) o;
            return Objects.equals(value, success.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}