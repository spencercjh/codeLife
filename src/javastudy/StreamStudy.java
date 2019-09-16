package javastudy;


import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author SpencerCJH
 * @date 2019/5/6 15:19
 * @see "Module:functional chapter9.AbstractStream"
 */
public class StreamStudy {
    /**
     * return numbers from 0 to limit
     * @param limit
     * @return Stream
     * @since 1.9
     */
    private static LongStream integers(long limit) {
        return LongStream.range(0, limit);
    }

    /**
     * @return primes stream
     */
    private static Stream<BigInteger> primes() {
        return Stream.iterate((BigInteger.ONE.add(BigInteger.ONE)), BigInteger::nextProbablePrime);
    }

    private static LongStream otherPrimes(long limit) {
        return integers(limit).filter(StreamStudy::isPrime);
    }

    /**
     * https://blog.csdn.net/afei__/article/details/80638460
     * 一个优化过的判断素数方法
     * @param num
     * @return isPrime
     */
    private static boolean isPrime(long num) {
        if (num <= 3) {
            return num > 1;
        }
        // 不在6的倍数两侧的一定不是质数
        if (num % 6 != 1 && num % 6 != 5) {
            return false;
        }
        int sqrt = (int) Math.sqrt(num);
        for (int i = 5; i <= sqrt; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * return limit of MersennePrimes(p is prime and 2**p-1 is also prime)
     * @param limit
     * @return Stream
     */
    private static Stream<String> outputMersennePrimes(long limit) {
        return primes().map(p -> (BigInteger.ONE.add(BigInteger.ONE)).pow(p.intValueExact()).subtract(BigInteger.ONE))
                .filter(temp -> temp.isProbablePrime(50))
                .limit(limit)
                .map(ans -> ans.bitLength() + ": " + ans);
    }

    /**
     * return limit * limit of number from 0 to limit
     * @param limit
     * @return Stream
     */
    public static LongStream doubleCycleTest(long limit) {
        return integers(limit).flatMap((unused) ->
                integers(limit).map(x -> x + 1));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void comparePrimesTest() {
        long start = System.currentTimeMillis();
        otherPrimes(100000).boxed().collect(Collectors.toList());
        System.out.println("times:   " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        primes().limit(100000).collect(Collectors.toList());
        System.out.println("times:   " + (System.currentTimeMillis() - start));
    }
}
