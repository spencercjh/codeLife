package leetcode;

/**
 * @author SpencerCJH
 * @date 2019/4/9 10:47
 */
public class SqrtX_69 {
    class Solution {
        public int mySqrt(int x) {
            if ((long) x <= 1) {
                return x;
            }
            long r = (long) x;
            while (r * r > (long) x) {
                r = (r + (long) x / r) / 2;
            }
            return (int) r;
        }
    }
}
