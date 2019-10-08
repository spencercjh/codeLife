package leetcode;

import java.util.*;

/**
 * @author spencercjh
 */
public class ThreeSum_15 {
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            Arrays.sort(nums);
            Set<List<Integer>> answer = new HashSet<>();
            int n = nums.length;
            for (int left = 0; left <= n - 3; left++) {
                int middle = left + 1, right = n - 1;
                while (middle < right) {
                    int sum = nums[left] + nums[middle] + nums[right];
                    if (sum > 0) {
                        right--;
                    } else if (sum < 0) {
                        middle++;
                    } else {
                        answer.add(Arrays.asList(nums[left], nums[middle], nums[right]));
                        middle++;
                        right--;
                    }
                }
            }
            return new ArrayList<>(answer);
        }
    }
}
