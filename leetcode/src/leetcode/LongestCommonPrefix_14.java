package leetcode;

/**
 * @author spencercjh
 */
public class LongestCommonPrefix_14 {
    /**
     * 纵向比较字符串前缀
     */
    class Solution {
        public String longestCommonPrefix(String[] strs) {
            try {
                int minLength = strs[0].length();
                for (String str : strs) {
                    if (str.length() < minLength) {
                        minLength = str.length();
                    }
                }
                StringBuilder ans = new StringBuilder();
                for (int i = 0; i < minLength; ++i) {
                    char ch = strs[0].charAt(i);
                    for (String str : strs) {
                        if (str.charAt(i) != ch) {
                            return ans.toString();
                        }
                    }
                    ans.append(ch);
                }
                return ans.toString();
            } catch (ArrayIndexOutOfBoundsException e) {
                return "";
            }
        }
    }
}
