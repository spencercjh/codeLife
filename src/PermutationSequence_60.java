import java.util.ArrayList;
import java.util.List;

/**
 * @author SpencerCJH
 * @date 2019/4/8 16:39
 */
public class PermutationSequence_60 {
    class SolutionTimeOut {
        private List<String> answers = new ArrayList<>();

        private void getPermutations(String list, int[] nums) {
            if (list.length() == nums.length) {
                answers.add(list);
                return;
            }
            for (int n : nums) {
                if (!list.contains(String.valueOf(n))) {
                    String temp = list + n;
                    getPermutations(temp, nums);
                }
            }
        }

        public String getPermutation(int n, int k) {
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = i + 1;
            }
            getPermutations("", nums);
            return answers.get(k - 1);
        }
    }

    class Solution {
        public String getPermutation(int n, int k) {
            StringBuilder sb = new StringBuilder();
            List<Integer> candidates = new ArrayList<>();
            int[] factorials = new int[n + 1];
            factorials[0] = 1;
            for (int i = 1; i <= n; ++i) {
                candidates.add(i);
                factorials[i] = factorials[i - 1] * i;
            }
            k -= 1;
            for (int i = n - 1; i >= 0; --i) {
                int index = k / factorials[i];
                sb.append(candidates.remove(index));
                k -= index * factorials[i];
            }
            return sb.toString();
        }
    }
}
