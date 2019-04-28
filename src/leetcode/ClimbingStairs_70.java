package leetcode;

/**
 * @author SpencerCJH
 * @date 2019/4/9 11:26
 */
public class ClimbingStairs_70 {
    class Solution {
        public int climbStairs(int n) {
            int[] f = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                if (i == 0 || i == 1) {
                    f[i] = 1;
                } else {
                    f[i] = f[i - 1] + f[i - 2];
                }
            }
            return f[n];
        }
    }
}
