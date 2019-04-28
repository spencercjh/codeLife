package leetcode;

/**
 * @author spencercjh
 */
public class MaximumSubarray_53 {
    class Solution {
        public int maxSubArray(int[] nums) {
            int sum = 0, max = nums[0];
            for (int i = 0; i < nums.length; ++i) {
                sum += nums[i];
                max = Math.max(max, sum);
                sum = sum < 0 ? 0 : sum;
            }
            return max;
        }
    }
}
