package functional.chapter3.email3;

import functional.chapter3.email2.Result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Spencer
 * Page 77
 */
public class EmailValidation {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    private static final Consumer<String> SUCCESS = s -> System.out.println("Mail sent to:\t" + s);

    private static final Consumer<String> FAILURE = s -> System.err.println("Error message logged:\t" + s);
    private static final Function<String, Result<String>> EMAIL_CHECKER = s -> Case.match(
            Case.matchCase(() -> Result.success(s)),
            Case.matchCase(() -> s == null, () -> Result.failure("email must not be null")),
            Case.matchCase(() -> s.length() == 0, () -> Result.failure("email must not be empty")),
            Case.matchCase(() -> !EMAIL_PATTERN.matcher(s).matches(), () -> Result.failure("email " + s + " is invalid"))
    );

    public static void main(String[] args) {
        EMAIL_CHECKER.apply(null).bind(SUCCESS, FAILURE);
        EMAIL_CHECKER.apply("").bind(SUCCESS, FAILURE);
        EMAIL_CHECKER.apply("shouspencercjh@foxmail.com").bind(SUCCESS, FAILURE);
    }
}
