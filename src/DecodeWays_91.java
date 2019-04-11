/**
 * @author : SpencerCJH
 * @date : 2019/4/12 3:19
 */
public class DecodeWays_91 {
    class Solution {
        public int numDecodings(String s) {
            int n = s.length();
            int[] dp = new int[n + 1];
            if (s.charAt(0) != '0') {
                dp[1] = dp[0] = 1;
            }
            for (int i = 2; i <= n; i++) {
                if (s.charAt(i - 1) == '0') {
                    if (s.charAt(i - 2) == '1' || s.charAt(i - 2) == '2') {
                        dp[i] = dp[i - 2];
                    } else {
                        return 0;
                    }
                } else {
                    dp[i] = dp[i - 1];
                    if (s.charAt(i - 2) != '0' && (s.charAt(i - 2) == '1' || s.charAt(i - 2) == '2')) {
                        int t = 10 * (s.charAt(i - 2) - '0') + s.charAt(i - 1) - '0';
                        if (t <= 26) {
                            dp[i] += dp[i - 2];
                        }
                    }
                }
            }
            return dp[n];
        }
    }
}
