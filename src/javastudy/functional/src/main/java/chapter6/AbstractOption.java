package chapter6;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Spencer
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractOption<A> implements AbstractOptions {
    private static final AbstractOption NONE = new None();

    private AbstractOption() {

    }

    public static <A> AbstractOption<A> some(A a) {
        return new Some<>(a);
    }

    @SuppressWarnings("unchecked")
    public static <A> AbstractOption<A> none() {
        return NONE;
    }

    /**
     * 如果包含的值存在就将其返回否则就抛出异常
     * @return value
     */
    protected abstract A getOrThrow();

    /**
     * 如果包含的值存在就将其返回否则就返回一个默认值
     * @param defaultValue suspended defaultValue
     * @return value
     */
    public abstract A getOrElse(Supplier<A> defaultValue);

    /**
     * 通过应用从A到B的函数function将AbstractOption<A>应用到AbstractOption<B>
     * @param f   function
     * @param <B> return AbstractOption value type
     * @return AbstractOption<B>
     */
    public abstract <B> AbstractOption<B> map(Function<A, B> f);

    /**
     * 接收一个从A到AbstractOption<B>的函数为参数，并返回一个AbstractOption<B>
     * @param f   function
     * @param <B> return AbstractOption value type
     * @return AbstractOption<B>
     */
    public abstract <B> AbstractOption<B> flatMap(Function<A, AbstractOption<B>> f);

    /**
     * 如果包含的值存在就将其返回否则就返回一个AbstractOption的默认值
     * @param defaultValue default AbstractOption<A>
     * @return default AbstractOption<A>
     */
    @Deprecated
    public abstract AbstractOption<A> orElse(Supplier<AbstractOption<A>> defaultValue);

    /**
     * 符合function的条件才返回value
     * @param f function
     * @return value
     */
    public abstract AbstractOption<A> filter(Function<A, Boolean> f);

    private static class None<A> extends AbstractOption<A> {
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
        public <B> AbstractOption<B> map(Function<A, B> f) {
            return none();
        }

        @Override
        public <B> AbstractOption<B> flatMap(Function<A, AbstractOption<B>> f) {
            return none();
        }

        @Override
        public AbstractOption<A> orElse(Supplier<AbstractOption<A>> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public AbstractOption<A> filter(Function<A, Boolean> f) {
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

    private static class Some<A> extends AbstractOption<A> {
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
        public <B> AbstractOption<B> map(Function<A, B> f) {
            return new Some<>(f.apply(value));
        }

        @Override
        public <B> AbstractOption<B> flatMap(Function<A, AbstractOption<B>> f) {
            return map(f).getOrElse(AbstractOption::none);
        }

        @Override
        public AbstractOption<A> orElse(Supplier<AbstractOption<A>> defaultValue) {
            // FIXME cannot work, may be the version of the Supplier
//            return map(x -> this).getOrElse(defaultValue);
            throw new IllegalStateException("orElse have some problem");
        }

        @Override
        public AbstractOption<A> filter(Function<A, Boolean> f) {
            return flatMap((A x) -> f.apply(x) ? this : AbstractOption.none());
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
