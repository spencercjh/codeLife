package functional.chapter4;

import functional.chapter3.CollectionUtilities;

import java.math.BigInteger;
import java.util.List;

import static functional.chapter3.CollectionUtilities.list;

/**
 * @author Spencer
 * page 128 129
 */
public class Test5 {
    private static BaseTailCell<List<BigInteger>> fibonacci(List<BigInteger> acc, BigInteger acc1, BigInteger acc2, BigInteger x) {
        return x.equals(BigInteger.ZERO) ? BaseTailCell.ofReturn(acc) :
                x.equals(BigInteger.ONE) ? BaseTailCell.ofReturn(CollectionUtilities.append(acc, acc1.add(acc2))) :
                        BaseTailCell.ofSuspend(() -> fibonacci(CollectionUtilities.append(acc, acc1.add(acc2)),
                                acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
    }

    private static <T> String makeString(List<T> list, String separator) {
        return list.isEmpty() ? "" :
                CollectionUtilities.getTail(list).isEmpty() ? CollectionUtilities.getHead(list).toString() :
                        CollectionUtilities.getHead(list) +
                                CollectionUtilities.foldLeft(CollectionUtilities.getTail(list),
                                        "", x -> y -> x + separator + y);
    }

    public static String fibonacciFacade(int number) {
        return makeString(fibonacci(list(BigInteger.ZERO),
                BigInteger.ONE,
                BigInteger.ZERO,
                BigInteger.valueOf(number)).value(), ", ");
    }

    public static void main(String[] args) {
        System.out.println(fibonacciFacade(100));
    }
}
