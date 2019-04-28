package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spencercjh
 */
public class Permutations_46 {
    class Solution {
        private void getPermutations(List<List<Integer>> lists, List<Integer> list, int[] nums) {
            if (list.size() == nums.length) {
                lists.add(list);
                return;
            }
            for (int n : nums) {
                if (!list.contains(n)) {
                    List<Integer> temp = new ArrayList<>(list);
                    temp.add(n);
                    getPermutations(lists, temp, nums);
                }
            }
        }

        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> lists = new ArrayList<>();
            getPermutations(lists, new ArrayList<>(), nums);
            return lists;
        }
    }
}
