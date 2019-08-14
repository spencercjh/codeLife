package chapter7;

import chapter6.Option;

import java.io.Serializable;
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

    public static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    public static <T> Result<T> failure() {
        return new Failure<>();
    }

    public static <T> Result<T> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> failure(RuntimeException e) {
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
            // nothing
        }

        @Override
        public String toString() {
            return "Empty()";
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

        private Failure(RuntimeException exception) {
            super();
            this.exception = exception;
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
    }
}
