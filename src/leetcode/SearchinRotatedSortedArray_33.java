package leetcode;

/**
 * @author spencercjh
 */
public class SearchinRotatedSortedArray_33 {
    class Solution {
        public int search(int[] nums, int target) {
            return binarySearch(nums, 0, nums.length - 1, target);
        }

        private int binarySearch(int[] nums, int low, int high, int target) {
            if (low > high) {
                return -1;
            }
            int middle = (low + high) / 2;
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[middle] < nums[high]) {
                if (nums[middle] <= target && target <= nums[high]) {
                    return binarySearch(nums, middle + 1, high, target);
                } else {
                    return binarySearch(nums, low, middle - 1, target);
                }
            } else {
                if (nums[low] <= target && target <= nums[middle]) {
                    return binarySearch(nums, low, middle - 1, target);
                } else {
                    return binarySearch(nums, middle + 1, high, target);
                }
            }
        }
    }
}
