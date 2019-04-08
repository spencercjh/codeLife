import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SpencerCJH
 * @date 2019/4/8 15:28
 */
public class SpiralMatrix_54 {

    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            if (null == matrix || matrix.length == 0) {
                return Collections.emptyList();
            }
            int row = matrix.length, column = matrix[0].length, n = row * column, i = 0, j = 0, t = 0;
            List<Integer> answer = new ArrayList<>();
            while (n > 0) {
                // right
                for (; j < column - t && n > 0; j++) {
                    answer.add(matrix[i][j]);
                    n--;
                }
                i++;
                j--;
                // down
                for (; i < row - t && n > 0; i++) {
                    answer.add(matrix[i][j]);
                    n--;
                }
                i--;
                j--;
                // left
                for (; j >= t && n > 0; j--) {
                    answer.add(matrix[i][j]);
                    n--;
                }
                i--;
                j++;
                // up
                for (; i > t && n > 0; i--) {
                    answer.add(matrix[i][j]);
                    n--;
                }
                i++;
                // next round
                t++;
                j++;
            }
            return answer;
        }
    }
}
