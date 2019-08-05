package functional.chapter4;

import java.util.function.Supplier;

/**
 * @author Spencer
 * page 110
 */
public abstract class BaseTailCell<T> {
    private BaseTailCell() {
    }

    public static <T> Return<T> ofReturn(T t) {
        return new Return<>(t);
    }

    public static <T> Suspend<T> ofSuspend(Supplier<BaseTailCell<T>> resume) {
        return new Suspend<>(resume);
    }

    /**
     * resume
     * @return tailCell
     */
    public abstract BaseTailCell<T> resume();

    /**
     * 获取结果
     * @return value
     */
    public abstract T value();

    /**
     * 是否暂停
     * @return boolean
     */
    public abstract boolean isSuspend();

    private static class Return<T> extends BaseTailCell<T> {

        private final T t;

        private Return(T t) {
            this.t = t;
        }

        @Override
        public BaseTailCell<T> resume() {
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

    private static class Suspend<T> extends BaseTailCell<T> {
        private final Supplier<BaseTailCell<T>> resume;

        private Suspend(Supplier<BaseTailCell<T>> resume) {
            this.resume = resume;
        }

        @Override
        public BaseTailCell<T> resume() {
            return resume.get();
        }

        @Override
        public T value() {
            BaseTailCell<T> tailCellResource = this;
            while (tailCellResource.isSuspend()) {
                tailCellResource = tailCellResource.resume();
            }
            return tailCellResource.value();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }
}
