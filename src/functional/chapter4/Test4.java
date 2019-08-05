package functional.chapter4;

import java.math.BigInteger;

/**
 * @author Spencer
 * page 120
 */
public class Test4 {
    private static BaseTailCell<BigInteger> fib_(BigInteger acc1, BigInteger acc2, BigInteger x) {
        if (x.equals(BigInteger.ZERO)) {
            return BaseTailCell.ofReturn(BigInteger.ZERO);
        } else if (x.equals(BigInteger.ONE)) {
            return BaseTailCell.ofReturn(acc1.add(acc2));
        } else {
            return BaseTailCell.ofSuspend(() -> fib_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
        }
    }

    public static BigInteger fib(int x) {
        return fib_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).value();
    }

    public static void main(String[] args) {
        System.out.println(fib(10000));
    }
}
