package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author spencercjh
 */
public class SingleNumber_136 {
    /**
     * 使用集合对数列进行去重，最后仍在集合里的即单独的一个数
     */
    class Solution1 {
        public int singleNumber(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < nums.length; ++i) {
                if (set.contains(nums[i])) {
                    set.remove(nums[i]);
                } else {
                    set.add(nums[i]);
                }
            }
            assert set.size() == 1;
            return (int) set.toArray()[0];
        }
    }

    /**
     * 异或运算有交换律；2个相同的数字异或等于0,0和任意数字异或是其本身，这样跑完整个数列就能得到单独的那个数
     */
    class Solution2 {
        public int singleNumber(int[] nums) {
            int num = 0;
            for (int i = 0; i < nums.length; ++i) {
                num ^= nums[i];
            }
            return num;
        }
    }
}
