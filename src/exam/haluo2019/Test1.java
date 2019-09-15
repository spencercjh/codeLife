package exam.haluo2019;

import java.util.Arrays;

/**
 * @author SpencerCJH
 * @date 2019/9/13 9:46
 */
public class Test1 {
    public int crazySort(int[] heights) {
        if (heights == null || heights.length <= 1) {
            return 0;
        }
        if (heights.length == 2) {
            return Math.abs(heights[1] - heights[0]);
        }
        Arrays.sort(heights);
        int answer = 0;
        for (int low = 0, high = heights.length - 1; low <= heights.length / 2 && high >= heights.length / 2; low++, high--) {
            if (low == high) {
                answer += Math.abs(heights[high + 1] - heights[low]);
            } else {
                answer += Math.abs(heights[high] - heights[low]);
            }
            if (high != heights.length - 1 && low != heights.length / 2) {
                answer += Math.abs(heights[high + 1] - heights[low]);
            }
        }
        return answer;
    }
}
