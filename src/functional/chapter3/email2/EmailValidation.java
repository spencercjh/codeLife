package functional.chapter3.email2;

import functional.chapter2.summery.Function;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author Spencer
 * page 72 73
 */
public class EmailValidation {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    private final static Function<String, Result<String>> EMAIL_CHECKER = s -> null == s ?
            Result.failure("email must not be null") : s.length() == 0 ?
            Result.failure("email must not be empty") : EMAIL_PATTERN.matcher(s).matches() ?
            Result.success(s) : Result.failure("email " + s + " is invalid.");
    private static final Consumer<String> FAILURE = s -> System.err.println("Error message logged:\t" + s);
    private static final Consumer<String> SUCCESS = s -> System.out.println("Mail sent to:\t" + s);

    public static void main(String[] args) {
        EMAIL_CHECKER.apply(null).bind(SUCCESS, FAILURE);
        EMAIL_CHECKER.apply("").bind(SUCCESS, FAILURE);
        EMAIL_CHECKER.apply("shouspencercjh@foxmail.com").bind(SUCCESS, FAILURE);
    }
}
