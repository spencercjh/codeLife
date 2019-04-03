/**
 * @author spencercjh
 */
public class NextPermutation_31 {

    class Solution {
        public void nextPermutation(int[] nums) {
            int slow = nums.length - 1;
            int fast = nums.length - 2;
            while (fast >= 0) {
                if (nums[fast] >= nums[slow]) {
                    fast--;
                    slow--;
                } else {
                    slow++;
                    while (slow < nums.length && nums[fast] < nums[slow]) {
                        slow++;
                    }
                    int temp = nums[fast];
                    nums[fast] = nums[slow - 1];
                    nums[slow - 1] = temp;
                    break;

                }
            }
            int s = fast + 1;
            int e = nums.length - 1;
            while (s < e) {
                int x = nums[s];
                nums[s] = nums[e];
                nums[e] = x;
                s++;
                e--;
            }
        }
    }
}
