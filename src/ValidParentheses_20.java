import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author spencercjh
 */
public class ValidParentheses_20 {
    class Solution {
        public boolean isValid(String s) {
            Map<Character, Character> map = new HashMap<>();
            map.put('}', '{');
            map.put(']', '[');
            map.put(')', '(');
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); ++i) {
                char ch = s.charAt(i);
                if (map.containsKey(ch)) {
                    char top = stack.isEmpty() ? '?' : stack.pop();
                    if (top != map.get(ch)) {
                        return false;
                    }
                } else {
                    stack.push(ch);
                }
            }
            return stack.isEmpty();
        }
    }
}
