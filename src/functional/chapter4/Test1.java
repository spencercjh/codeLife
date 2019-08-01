package functional.chapter4;

/**
 * @author Spencer
 * page 111
 */
public class Test1 {
    public static int recursiveAdd(int x, int y) {
        return y == 0 ? x : recursiveAdd(++x, --y);
    }

    public static TailCell<Integer> add(int x, int y) {
        return y == 0 ? new TailCell.Return<>(x) : new TailCell.Suspend<>(() -> add(x + 1, y - 1));
    }

    public static void main(String[] args) {
        TailCell<Integer> tailCell = add(10, 10);
        while (tailCell.isSuspend()) {
            tailCell = tailCell.resume();
        }
        System.out.println(tailCell.value());
    }
}
