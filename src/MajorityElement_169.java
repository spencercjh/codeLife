import java.util.HashMap;
import java.util.Map;

public class MajorityElement_169 {
    /**
     * 普通hash法
     */
    class Solution1 {
        public int majorityElement(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; ++i) {
                if (map.containsKey(nums[i])) {
                    int count = map.get(nums[i]);
                    count++;
                    map.put(nums[i], count);
                } else {
                    map.put(nums[i], 1);
                }
            }
            int ans = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() > nums.length / 2) {
                    ans = entry.getKey();
                }
            }
            return ans;
        }
    }

    /**
     * 摩尔投票法
     */
    class Solution2 {
        public int majorityElement(int[] nums) {
            int count = 0, answer = 0;
            for (int i = 0; i < nums.length; ++i) {
                if (count == 0) {
                    answer = nums[i];
                    ++count;
                } else if (nums[i] == answer) {
                    ++count;
                } else {
                    --count;
                }
            }
            return answer;
        }
    }
}
