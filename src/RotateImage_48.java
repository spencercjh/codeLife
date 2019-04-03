/**
 * @author spencercjh
 */
public class RotateImage_48 {
    class Solution {
        public void rotate(int[][] matrix) {
            int length = matrix.length;
            for (int i = 0; i < length / 2; i++) {
                int end = length - 1 - i;
                for (int j = 0; j < end - i; j++) {
                    int temp = matrix[i][i + j];
                    matrix[i][i + j] = matrix[end - j][i];
                    matrix[end - j][i] = matrix[end][end - j];
                    matrix[end][end - j] = matrix[i + j][end];
                    matrix[i + j][end] = temp;
                }
            }
        }
    }
}
