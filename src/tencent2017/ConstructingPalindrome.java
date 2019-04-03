package tencent2017;

import java.util.Scanner;

/**
 * 构造回文
 * https://www.nowcoder.com/questionTerminal/28c1dc06bc9b4afd957b01acdf046e69
 * 需要删除的字符个数=字符串长度-LCS最长公共子串长度
 *
 * @author spencercjh
 */
public class ConstructingPalindrome {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s1 = sc.next(), s2 = new StringBuilder(s1).reverse().toString();
            int[][] dp = new int[s1.length() + 1][s2.length() + 1];
            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) ?
                            dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
            System.out.println(s1.length() - dp[s1.length()][s2.length()]);
        }
    }
}
