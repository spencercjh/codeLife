/**
 * @author spencercjh
 */
public class FindFirstandLastPositionofElementinSortedArray_34 {
    class Solution {
        public int[] searchRange(int[] nums, int target) {
            if (nums.length <= 0) {
                return new int[]{-1, -1};
            }
            return binarySearch(0, nums.length - 1, nums, target);
        }

        private int[] binarySearch(int low, int high, int[] num, int target) {
            if (low > high) {
                return new int[]{-1, -1};
            }
            int middle = (low + high) / 2;
            if (num[middle] == target) {
                int start = middle, end = middle;
                while (start >= 0 && num[start] == target) {
                    start--;
                }
                while (end < num.length && num[end] == target) {
                    end++;
                }
                return new int[]{start+1, end-1};
            } else if (target > num[middle]) {
                return binarySearch(middle + 1, high, num, target);
            } else {
                return binarySearch(low, middle - 1, num, target);
            }
        }
    }
}
