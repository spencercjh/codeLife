/**
 * @author spencercjh
 */
public class Pow_50 {
    class Solution {
        public double myPow(double x, int n) {
            long temp = n;
            temp = temp > 0 ? temp : -temp;
            double ans = 1;
            if (n == 0) {
                return 1;
            }
            double base = x;
            while (temp > 0) {
                if ((temp & 1) == 1) {
                    ans = ans * base;
                }
                base = base * base;
                temp >>= 1;
            }
            if (n > 0) {
                return ans;
            } else {
                return 1 / ans;
            }
        }
    }
}
