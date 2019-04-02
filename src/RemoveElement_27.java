/**
 * @author spencercjh
 */
public class RemoveElement_27 {
    /**
     * 双指针法，不断地把非删元素去替代要删除的元素，和第26题很相似
     */
    class Solution {
        public int removeElement(int[] nums, int val) {
            int i = 0;
            for (int j = 0; j < nums.length; ++j) {
                if (nums[j] != val) {
                    nums[i++] = nums[j];
                }
            }
            return i;
        }
    }
}
