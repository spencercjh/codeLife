/**
 * @author : SpencerCJH
 * @date : 2019/4/12 0:58
 */
public class MergeSortedArray_88 {
    class Solution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int index = m-- + n-- - 1;
            while (m >= 0 && n >= 0) {
                nums1[index--] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
            }
            while (n >= 0) {
                nums1[index--] = nums2[n--];
            }
        }
    }
}
