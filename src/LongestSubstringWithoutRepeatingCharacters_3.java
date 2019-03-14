import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : spencercjh
 * @date : 2019/3/14 20:04
 */
class LongestSubstringWithoutRepeatingCharacters_3 {
    class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            int ans = 0;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j <= n; ++j) {
                    if (unique(s, i, j)) {
                        ans = Math.max(ans, j - i);
                    }
                }
            }
            return ans;
        }

        public boolean unique(String s, int start, int end) {
            Set<Character> set = new HashSet<>();
            for (int i = start; i < end; ++i) {
                char ch = s.charAt(i);
                if (set.contains(ch)) {
                    return false;
                }
                set.add(ch);
            }
            return true;
        }
    }

    class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            int i = 0, j = 0, ans = 0, n = s.length();
            Set<Character> set = new HashSet<>();
            while (i < n && j < n) {
                if (!set.contains(s.charAt(j))) {
                    set.add(s.charAt(j++));
                    ans = Math.max(ans, j - i);
                } else {
                    set.remove(s.charAt(i++));
                }
            }
            return ans;
        }
    }

    class Solution3 {
        public int lengthOfLongestSubstring(String s) {
            int ans = 0, n = s.length();
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0, j = 0; i < n && j < n; ++j) {
                if (map.containsKey(s.charAt(j))) {
                    i = Math.max(map.get(s.charAt(j)), i);
                }
                ans = Math.max(ans, j - i + 1);
                map.put(s.charAt(j), j + 1);
            }
            return ans;
        }
    }
}