package functional.chapter3.email2;

import java.util.function.Consumer;

/**
 * @author Spencer
 * page 71 72
 */
public interface Result<T> {
    /**
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    /**
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    /**
     * 绑定成功与失败两个Consumer
     * @param success Consumer
     * @param failure Consumer
     */
    void bind(Consumer<T> success, Consumer<String> failure);

    public class Success<T> implements Result<T> {
        private final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public void bind(Consumer<T> success, Consumer<String> failure) {
            success.accept(value);
        }
    }

    public class Failure<T> implements Result<T> {
        private final String errorMessage;

        private Failure(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void bind(Consumer<T> success, Consumer<String> failure) {
            failure.accept(errorMessage);
        }
    }
}
