import java.util.ArrayList;
import java.util.List;

/**
 * @author : SpencerCJH
 * @date : 2019/4/12 16:23
 */
public class RestoreIPAddresses_93 {
    class Solution {
        public List<String> restoreIpAddresses(String s) {
            List<String> answers = new ArrayList<>();
            restore(answers, new int[4], 0, 0, s.length(), s);
            return answers;
        }

        private void restore(List<String> answers, int[] solutionSpace, int solutionSpaceIndex, int currentLength,
                             int finalLength, String s) {
            if (currentLength > finalLength) {
                return;
            }
            if (solutionSpaceIndex == 4) {
                if (currentLength == finalLength) {
                    StringBuilder sb = new StringBuilder();
                    String str;
                    int now = 0;
                    for (int n : solutionSpace) {
                        str = s.substring(now, now + n);
                        boolean isIllegal = Integer.parseInt(str) > 255 || str.length() > 1 && str.charAt(0) == '0';
                        if (isIllegal) {
                            return;
                        }
                        sb.append(str).append(".");
                        now += n;
                    }
                    answers.add(sb.substring(0, sb.length() - 1));
                }
                return;
            }
            for (int ipLength = 1; ipLength <= 3; ipLength++) {
                solutionSpace[solutionSpaceIndex] = ipLength;
                restore(answers, solutionSpace, solutionSpaceIndex + 1,
                        currentLength + ipLength, finalLength, s);
            }
        }
    }
}
