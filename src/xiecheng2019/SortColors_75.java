package xiecheng2019;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 16:58
 */
public class SortColors_75 {
    class Solution2 {
        /**
         * 0往前交换2往后交换
         *
         * @param nums nums
         */
        public void sortColors(int[] nums) {
            if (nums.length < 2) {
                return;
            }
            int zero = 0;
            int two = nums.length - 1;
            for (int i = 0; i <= two; ) {
                if (nums[i] == 1) {
                    i++;
                } else if (nums[i] == 2) {
                    swap(nums, i, two);
                    two--;
                } else {
                    swap(nums, zero, i);
                    zero++;
                    i++;
                }
            }
        }

        private void swap(int[] arr, int i, int j) {
            if (i == j) {
                return;
            }
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    class Solution {
        /**
         * 标记填充
         *
         * @param nums nums
         */
        public void sortColors(int[] nums) {
            int zeroCount = 0, oneCount = 0, twoCount = 0;
            for (int num : nums) {
                switch (num) {
                    case 0:
                        zeroCount++;
                        break;
                    case 1:
                        oneCount++;
                        break;
                    case 2:
                        twoCount++;
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < zeroCount; i++) {
                nums[i] = 0;
            }
            for (int i = zeroCount; i < zeroCount + oneCount; i++) {
                nums[i] = 1;
            }
            for (int i = zeroCount + oneCount; i < nums.length; i++) {
                nums[i] = 2;
            }
        }
    }
}
