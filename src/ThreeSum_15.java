import java.util.*;

public class ThreeSum_15 {
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> lists = new ArrayList<>();
            Set<List<Integer>> hashSet = new HashSet<>();
            Arrays.sort(nums);
            int len = nums.length;
            for (int time = 0; time <= len - 3; time++) {
                int left = time + 1, right = len - 1;
                while (left < right) {
                    int sum = nums[time] + nums[left] + nums[right];
                    if (sum < 0) {
                        left++;
                    } else if (sum > 0) {
                        right--;
                    } else {
                        List<Integer> answer = Arrays.asList(nums[time], nums[left], nums[right]);
                        hashSet.add(answer);
                        left++;
                        right--;
                    }
                }
            }
            if (!hashSet.isEmpty()) {
                lists.addAll(hashSet);
            }
            return lists;
        }
    }
}
