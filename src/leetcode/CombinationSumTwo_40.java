package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author spencercjh
 */
public class CombinationSumTwo_40 {
    class Solution {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> answers = new ArrayList<>();
            Arrays.sort(candidates);
            find(answers, new ArrayList<>(), candidates, target, 0);
            return answers;
        }

        private void find(List<List<Integer>> answers, List<Integer> temp, int[] candidates, int target, int index) {
            if (target == 0) {
                answers.add(temp);
                return;
            }
            if (index >= candidates.length || target < candidates[index]) {
                return;
            }
            boolean isFirst = true;
            for (int i = index; i < candidates.length && candidates[i] <= target; i++) {
                if (isFirst || candidates[i] != candidates[i - 1]) {
                    List<Integer> list = new ArrayList<>(temp);
                    list.add(candidates[i]);
                    find(answers, list, candidates, target - candidates[i], i + 1);
                }
                isFirst = false;
            }
        }
    }
}
