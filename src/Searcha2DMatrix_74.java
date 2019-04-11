/**
 * @author : SpencerCJH
 * @date : 2019/4/11 16:48
 */
public class Searcha2DMatrix_74 {
    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (null == matrix || matrix.length == 0) {
                return false;
            }
            int row = 0, column = matrix[0].length - 1;
            while (row < matrix.length && column >= 0) {
                if (target > matrix[row][column]) {
                    row++;
                } else if (target < matrix[row][column]) {
                    column--;
                } else {
                    return true;
                }
            }
            return false;
        }
    }
}
