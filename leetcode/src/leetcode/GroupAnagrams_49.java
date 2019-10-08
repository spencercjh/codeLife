package leetcode;

import java.util.*;

/**
 * @author spencercjh
 */
public class GroupAnagrams_49 {
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> answerMap = new HashMap<>();
            for (String str : strs) {
                int[] counts = new int[26];
                for (char ch : str.toCharArray()) {
                    counts[ch - 'a']++;
                }
                String key = Arrays.toString(counts);
                if (!answerMap.containsKey(key)) {
                    answerMap.put(key, new ArrayList<>());
                }
                answerMap.get(key).add(str);
            }
            return new ArrayList<>(answerMap.values());
        }
    }
}
