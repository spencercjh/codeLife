package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spencercjh
 */
public class GenerateParentheses_22 {
    class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> ans = new ArrayList<>();
            generateCore(ans, "", 0, 0, n);
            return ans;
        }

        private void generateCore(List<String> ans, String tmsAns, int left, int right, int len) {
            if (left + right > 2 * len || right > left) {
                return;
            }
            if (left == right && left == len) {
                ans.add(tmsAns);
            }
            generateCore(ans, tmsAns + "(", left + 1, right, len);
            generateCore(ans, tmsAns + ")", left, right + 1, len);
        }
    }
}
