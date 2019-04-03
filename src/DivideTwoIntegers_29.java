/**
 * @author spencercjh
 */
public class DivideTwoIntegers_29 {
    class Solution {
        public int divide(int dividend, int divisor) {
            if (dividend == 0) {
                return 0;
            }
            if (dividend == Integer.MIN_VALUE && divisor == -1) {
                return Integer.MAX_VALUE;
            }
            boolean negative;
            // 用异或来计算是否符号相异
            negative = (dividend ^ divisor) < 0;
            long t = Math.abs((long) dividend);
            long d = Math.abs((long) divisor);
            int result = 0;
            for (int i = 31; i >= 0; i--) {
                // 找出足够大的数2^n*divisor
                if ((t >> i) >= d) {
                    // 将结果加上2^n
                    result += 1 << i;
                    // 将被除数减去2^n*divisor
                    t -= d << i;
                }
            }
            return negative ? -result : result;
        }
    }
}
