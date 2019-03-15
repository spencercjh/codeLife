public class Searcha2DMatrixII_240 {
    /**
     * 从右上角斜向左下方向寻找，比target大就下一行，比它小就左一列
     * 二分法遍历的元素还是过多
     */
    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            try {
                for (int i = 0, j = matrix[0].length - 1; i < matrix.length && j >= 0; ) {
                    if (target > matrix[i][j]) {
                        i++;
                    } else if (target < matrix[i][j]) {
                        j--;
                    } else {
                        return true;
                    }
                }
                return false;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
    }
}
