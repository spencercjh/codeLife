package leetcode;

import java.util.*;

/**
 * @author spencercjh
 */
public class FourSum_18 {
    class Solution {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            if (nums == null || nums.length < 4) {
                return Collections.emptyList();
            }
            Arrays.sort(nums);
            Set<List<Integer>> answer = new HashSet<>();
            int n = nums.length;
            for (int i = 0; i <= n - 4; i++) {
                for (int j = i + 1; j <= n - 3; j++) {
                    int l = j + 1, m = n - 1;
                    while (l < m) {
                        int sum = nums[i] + nums[j] + nums[l] + nums[m];
                        if (sum > target) {
                            m--;
                        } else if (sum < target) {
                            l++;
                        } else {
                            answer.add(Arrays.asList(nums[i], nums[j], nums[l], nums[m]));
                            l++;
                            m--;
                        }
                    }
                }
            }
            return new ArrayList<>(answer);
        }
    }
}
