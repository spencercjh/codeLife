package leetcode;

/**
 * @author SpencerCJH
 * @date 2019/4/8 16:27
 */
public class SpiralMatrixTwo_59 {
    class Solution {
        public int[][] generateMatrix(int n) {
            int[][] matrix = new int[n][n];
            int m = 1, i = 0, j = 0, t = 0;
            while (m <= n * n) {
                // right
                for (; j < n - t && m <= n * n; j++) {
                    matrix[i][j] = m++;
                }
                i++;
                j--;
                // down
                for (; i < n - t && m <= n * n; i++) {
                    matrix[i][j] = m++;
                }
                i--;
                j--;
                // left
                for (; j >= t && m <= n * n; j--) {
                    matrix[i][j] = m++;
                }
                i--;
                j++;
                // up
                for (; i > t && m <= n * n; i--) {
                    matrix[i][j] = m++;
                }
                i++;
                // next round
                t++;
                j++;
            }
            return matrix;
        }
    }
}
