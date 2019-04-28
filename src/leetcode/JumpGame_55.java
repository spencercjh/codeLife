package leetcode;

/**
 * @author SpencerCJH
 * @date 2019/4/8 15:43
 */
public class JumpGame_55 {
    class Solution {
        public boolean canJump(int[] nums) {
            int end = nums.length - 1, i = end;
            for (; i >= 0; i--) {
                if (nums[i] >= end - i) {
                    end = i;
                }
            }
            return end == 0;
        }
    }
}
