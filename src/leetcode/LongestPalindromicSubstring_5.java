package leetcode;

/**
 * @author Spencer
 */
public class LongestPalindromicSubstring_5 {
    class Solution {
        public String longestPalindrome(String s) {
            if (s == null || s.length() == 1) {
                return s;
            }
            String reverse = new StringBuilder(s).reverse().toString();
            int maxEndIndex = 0, maxLength = 0;
            int[][] dp = new int[s.length()][s.length()];
            for (int i = 0; i < s.length(); i++) {
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(i) == reverse.charAt(j)) {
                        if (i == 0 || j == 0) {
                            dp[i][j] = 1;
                        } else {
                            dp[i][j] = dp[i - 1][j - 1] + 1;
                        }
                    }
                    if (dp[i][j] > maxLength) {
                        int beforeIndex = s.length() - 1 - j;
                        if (beforeIndex + dp[i][j] - 1 == i) {
                            maxLength = dp[i][j];
                            maxEndIndex = i;
                        }
                    }
                }
            }
            return s.substring(maxEndIndex - maxLength + 1, maxEndIndex + 1);
        }
    }
}
