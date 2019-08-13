package chapter4;

/**
 * @author Spencer
 * page 111
 */
public class Test1 {
    public static int recursiveAdd(int x, int y) {
        return y == 0 ? x : recursiveAdd(++x, --y);
    }

    private static BaseTailCell<Integer> add(int x, int y) {
        return y == 0 ? BaseTailCell.ofReturn(x) : BaseTailCell.ofSuspend(() -> add(x + 1, y - 1));
    }

    public static int addFacade(int x, int y) {
        return add(x, y).value();
    }

    public static void main(String[] args) {
        System.out.println(addFacade(100, 100));
    }
}
