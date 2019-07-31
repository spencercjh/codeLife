package functional.chapter3.email1;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Spencer
 * page 68
 */
public class EmailValidation {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    private final static Function<String, Result> emailChecker = s -> s == null ?
            new Result.Failure("email must not be null") : s.length() == 0 ?
            new Result.Failure("email must not be empty") : EMAIL_PATTERN.matcher(s).matches() ?
            new Result.Success() : new Result.Failure("email " + s + " is invalid.");

    private static void logError(String s) {
        System.err.println("error message logged:\t" + s);
    }

    private static void sendVerification(String s) {
        System.out.println("Mail sent to:\t" + s);
    }

    private static Runnable validate(String s) {
        Result result = emailChecker.apply(s);
        return (result instanceof Result.Success) ?
                () -> sendVerification(s) :
                () -> logError(((Result.Failure) result).getErrorMessage());
    }

    public static void main(String... args) {
        validate(null).run();
        validate("").run();
        validate("shouspencercjh@foxmail.com").run();
    }
}
