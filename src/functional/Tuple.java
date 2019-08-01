package functional;

/**
 * @author Spencer
 * page 10
 */
public class Tuple<T, U> {
    public final T first;
    public final U second;

    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }
}