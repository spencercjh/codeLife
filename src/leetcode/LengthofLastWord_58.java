package leetcode;

/**
 * @author spencercjh
 */
public class LengthofLastWord_58 {
    class Solution1 {
        public int lengthOfLastWord(String s) {
            try {
                String[] strs = s.split(" ");
                return strs[strs.length - 1].length();
            } catch (ArrayIndexOutOfBoundsException e) {
                return 0;
            }
        }
    }

    class Solution2 {
        public int lengthOfLastWord(String s) {
            int count = 0;
            boolean flag = false;
            for (int i = s.length() - 1; i >= 0; --i) {
                if (s.charAt(i) != ' ') {
                    flag = true;
                }
                if (flag && s.charAt(i) == ' ') {
                    break;
                }
                if (flag) {
                    count++;
                }
            }
            return count;
        }
    }
}
