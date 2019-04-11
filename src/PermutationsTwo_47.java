import java.util.*;

/**
 * @author spencercjh
 */
public class PermutationsTwo_47 {
    class Solution {
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

        public List<List<Integer>> permuteUnique(int[] nums) {
            Set<List<Integer>> lists = new HashSet<>();
            getPermutations(lists, new ArrayList<>(), new HashMap<>(), nums);
            return new ArrayList<>(lists);
        }
    }
}