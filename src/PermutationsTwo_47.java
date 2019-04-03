import java.util.*;

/**
 * Line 10: error: cannot find symbol [in __Driver__.java]
 * List<List<Integer>> ret = new Solution().permuteUnique(param_1);
 * ^
 * symbol:   method permuteUnique(int[])
 * location: class Solution
 * 本地正确执行，leetcode编译错误
 *
 * @author spencercjh
 */
public class PermutationsTwo_47 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.permute(new int[]{1, 1, 2}));
    }

    static class Solution {
        private void getPermutations(Set<List<Integer>> lists, List<Integer> list, Map<Integer, Integer> entry, int[] nums) {
            if (list.size() == nums.length) {
                lists.add(list);
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (!entry.containsKey(i)) {
                    List<Integer> tempList = new ArrayList<>(list);
                    tempList.add(nums[i]);
                    Map<Integer, Integer> tempEntry = new HashMap<>(entry);
                    tempEntry.put(i, nums[i]);
                    getPermutations(lists, tempList, tempEntry, nums);
                }
            }
        }

        public List<List<Integer>> permute(int[] nums) {
            Set<List<Integer>> lists = new HashSet<>();
            getPermutations(lists, new ArrayList<>(), new HashMap<>(), nums);
            return new ArrayList<>(lists);
        }
    }
}