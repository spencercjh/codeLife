package leetcode;

/**
 * @author spencercjh
 */
public class RemoveDuplicatesfromSortedArray_26 {
    /**
     * 数组是排序过的
     * i，j双指针；遍历到有新的数字就替换掉之前相同的
     */
    class Solution {
        public int removeDuplicates(int[] nums) {
            int i = 0;
            for (int j = 1; j < nums.length; ++j) {
                if (nums[i] != nums[j]) {
                    nums[++i] = nums[j];
                }
            }
            return i + 1;
        }
    }
}
