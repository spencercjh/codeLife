package leetcode;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 21:38
 */
public class SearchinRotatedSortedArrayTwo_81 {
    class Solution {
        public boolean search(int[] nums, int target) {
            return binarySearch(nums, 0, nums.length - 1, target);
        }

        private boolean binarySearch(int[] nums, int low, int high, int target) {
            if (low > high) {
                return false;
            }
            int middle = (low + high) / 2;
            if (nums[middle] == target) {
                return true;
            }
            if (nums[middle] < nums[high]) {
                if (nums[middle] <= target && target <= nums[high]) {
                    return binarySearch(nums, middle + 1, high, target);
                } else {
                    return binarySearch(nums, low, middle - 1, target);
                }
            } else if (nums[middle] > nums[high]) {
                if (nums[low] <= target && target <= nums[middle]) {
                    return binarySearch(nums, low, middle - 1, target);
                } else {
                    return binarySearch(nums, middle + 1, high, target);
                }
            } else {
                if (nums[middle] == nums[low]) {
                    return binarySearch(nums, low + 1, high, target);
                } else {
                    return binarySearch(nums, low, high - 1, target);
                }
            }
        }
    }
}
