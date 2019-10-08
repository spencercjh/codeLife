package chapter6;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "WeakerAccess"})
public abstract class Option<A> implements Options, BaseOption<A> {
    private static final Option NONE = new None();

    private Option() {

    }

    public static <A> Option<A> some(A a) {
        return new Some<>(a);
    }

    @SuppressWarnings("unchecked")
    public static <A> Option<A> none() {
        return NONE;
    }

    private static class None<A> extends Option<A> {
        private None() {

        }

        @Override
        public A getOrThrow() {
            throw new IllegalStateException("get called on None");
        }

        @Override
        public A getOrElse(Supplier<A> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <B> Option<B> map(Function<A, B> f) {
            return none();
        }

        @Override
        public <B> Option<B> flatMap(Function<A, Option<B>> f) {
            return none();
        }

        @Override
        public Option<A> orElse(Supplier<Option<A>> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public Option<A> filter(Function<A, Boolean> f) {
            return none();
        }

        @Override
        public String toString() {
            return "None{}";
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return false;
        }
    }

    private static class Some<A> extends Option<A> {
        private final A value;

        private Some(A value) {
            this.value = value;
        }

        @Override
        public A getOrThrow() {
            return this.value;
        }

        @Override
        public A getOrElse(Supplier<A> defaultValue) {
            return this.value;
        }

        @Override
        public <B> Option<B> map(Function<A, B> f) {
            return new Some<>(f.apply(value));
        }

        @Override
        public <B> Option<B> flatMap(Function<A, Option<B>> f) {
            return map(f).getOrElse(Option::none);
        }

        @Override
        public Option<A> orElse(Supplier<Option<A>> defaultValue) {
            // FIXME cannot work, may be the version of the Supplier
//            return map(x -> this).getOrElse(defaultValue);
            throw new IllegalStateException("orElse have some problem");
        }

        @Override
        public Option<A> filter(Function<A, Boolean> f) {
            return flatMap((A x) -> f.apply(x) ? this : Option.none());
        }

        @Override
        public String toString() {
            return String.format("Some(%s)", this.value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Some<?> some = (Some<?>) o;
            return Objects.equals(value, some.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }
}
