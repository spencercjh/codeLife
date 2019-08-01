package functional.chapter4;

import java.util.function.Supplier;

/**
 * @author Spencer
 * page 110
 */
public interface TailCell<T> {
    /**
     * resume
     * @return tailCell
     */
    TailCell<T> resume();

    /**
     * 获取结果
     * @return value
     */
    T value();

    /**
     * 是否暂停
     * @return boolean
     */
    boolean isSuspend();

    class Return<T> implements TailCell<T> {

        private final T t;

        public Return(T t) {
            this.t = t;
        }

        @Override
        public TailCell<T> resume() {
            throw new IllegalStateException("Return has no resume");
        }

        @Override
        public T value() {
            return t;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    class Suspend<T> implements TailCell<T> {
        private final Supplier<TailCell<T>> resume;

        public Suspend(Supplier<TailCell<T>> resume) {
            this.resume = resume;
        }

        @Override
        public TailCell<T> resume() {
            return resume.get();
        }

        @Override
        public T value() {
            throw new IllegalStateException("Suspend has no value");
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }
}
