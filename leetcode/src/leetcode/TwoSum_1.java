package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class TwoSum_1 {
    /**
     * 将可能的结果放进hashmap减少循环
     */
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> hashMap = new HashMap<>();
            for (int i = 0; i < nums.length; ++i) {
                int perhaps = target - nums[i];
                if (hashMap.containsKey(perhaps)) {
                    return new int[]{hashMap.get(perhaps), i};
                }
                hashMap.put(nums[i], i);
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }
}


