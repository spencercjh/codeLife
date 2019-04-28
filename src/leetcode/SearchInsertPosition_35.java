package leetcode;

/**
 * @author : spencercjh
 * @date : 2019/3/15 21:45
 */
public class SearchInsertPosition_35 {
    /**
     * 二分查找
     */
    class Solution {
        public int searchInsert(int[] nums, int target) {
            try {
                int low = 0, high = nums.length;
                while (low < high) {
                    int mid = low + (high - low) / 2;
                    if (nums[mid] > target) {
                        high = mid;
                    } else if (nums[mid] < target) {
                        low = mid + 1;
                    } else {
                        return mid;
                    }
                }
                return low;
            } catch (ArrayIndexOutOfBoundsException e) {
                return 0;
            }
        }
    }
}
