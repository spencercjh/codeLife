import java.util.HashSet;
import java.util.Set;

public class SingleNumber_136 {
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
