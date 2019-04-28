package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class RomantoInteger_13 {
    /**
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     */
    class Solution {
        public int romanToInt(String s) {
            Map<Character, Integer> map = new HashMap<>();
            map.put('I', 1);
            map.put('V', 5);
            map.put('X', 10);
            map.put('L', 50);
            map.put('C', 100);
            map.put('D', 500);
            map.put('M', 1000);
            int ans = 0;
            for (int i = 0; i < s.length(); ++i) {
                char ch = s.charAt(i);
                ans += map.get(ch);
                if (i != 0) {
                    if ((ch == 'V' || ch == 'X') && s.charAt(i - 1) == 'I') {
                        ans -= 2 * map.get('I');
                    } else if ((ch == 'L' || ch == 'C') && s.charAt(i - 1) == 'X') {
                        ans -= 2 * map.get('X');
                    } else if ((ch == 'D' || ch == 'M') && s.charAt(i - 1) == 'C') {
                        ans -= 2 * map.get('C');
                    }
                }

            }
            return ans;
        }
    }
}
