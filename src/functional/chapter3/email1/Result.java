package functional.chapter3.email1;

/**
 * @author Spencer
 */
public interface Result {
    public class Success implements Result {

    }

    public class Failure implements Result {
        private final String errorMessage;


        public Failure(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
