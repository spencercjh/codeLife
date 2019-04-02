import java.util.Arrays;

/**
 * @author spencercjh
 */
public class ThreeSumClosest_16 {
    class Solution {
        public int threeSumClosest(int[] nums, int target) {
            Arrays.sort(nums);
            int n = nums.length, closeTarget = nums[0] + nums[1] + nums[2];
            for (int left = 0; left <= n - 3; left++) {
                int middle = left + 1, right = n - 1;
                while (middle < right) {
                    int sum = nums[left] + nums[middle] + nums[right];
                    if (Math.abs(sum - target) < Math.abs(closeTarget - target)) {
                        closeTarget = sum;
                    }
                    if (sum > target) {
                        right--;
                    } else if (sum < target) {
                        middle++;
                    } else {
                        return sum;
                    }
                }
            }
            return closeTarget;
        }
    }
}
