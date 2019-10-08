package leetcode;

import java.util.*;

/**
 * @author spencercjh
 */
public class LetterCombinationsofaPhoneNumber_17 {

    private static final Map<Character, String> MAP = new HashMap<>();

    static {
        MAP.put('2', "abc");
        MAP.put('3', "def");
        MAP.put('4', "ghi");
        MAP.put('5', "jkl");
        MAP.put('6', "mno");
        MAP.put('7', "pqrs");
        MAP.put('8', "tuv");
        MAP.put('9', "wxyz");
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        getResult("", digits, 0, result);
        return result;
    }

    private void getResult(String s, String digits, int start, List<String> result) {
        if (start >= digits.length()) {
            result.add(s);
        } else {
            String str = MAP.get(digits.charAt(start));
            for (int i = 0; i < str.length(); i++) {
                getResult(s + str.charAt(i), digits, (start + 1), result);
            }
        }
    }
}
