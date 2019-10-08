package leetcode;

/**
 * @author Spencer
 */
public class PalindromicSubstrings_647 {
    class Solution1 {
        public int countSubstrings(String s) {
            int answer = 0;
            for (int i = 0; i < s.length(); i++) {
                answer += count(s, i, i);
                answer += count(s, i, i + 1);
            }
            return answer;
        }

        private int count(String s, int start, int end) {
            int count = 0;
            while (start >= 0 && end <= s.length() - 1 && s.charAt(start--) == s.charAt(end++)) {
                count++;
            }
            return count;
        }
    }
    class Solution2 {
        public int countSubstrings(String s) {
            int answer=0;
            for(int i=0;i<s.length();i++){
                for(int j=i;j<s.length();j++){
                    int begin=i,end=j;
                    boolean equals=true;
                    while (begin<=end){
                        if (s.charAt(begin++) != s.charAt(end--)) {
                            equals = false;
                            break;
                        }
                    }
                    if (equals){
                        answer++;
                    }
                }
            }
            return answer;
        }
    }
}
