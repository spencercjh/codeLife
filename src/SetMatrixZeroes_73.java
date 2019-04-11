/**
 * @author : SpencerCJH
 * @date : 2019/4/11 16:08
 */
public class SetMatrixZeroes_73 {
    class Solution2 {
        /**
         * 用第0列和第0行来替代O(m+n)的boolean数组存储需要归零的行和列信息，这一行和列需要替代的标志用O(2)的boolean变量
         *
         * @param matrix matrix
         */
        public void setZeroes(int[][] matrix) {
            // 先判断第0行是否需要最后归零
            boolean rowFlag = false;
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[0][i] == 0) {
                    rowFlag = true;
                    break;
                }
            }
            // 先判断第0列是否需要最后归零
            boolean colFlag = false;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][0] == 0) {
                    colFlag = true;
                    break;
                }
            }
            // 判断第1行/列开始有无0，把记录存储在第0行/列中
            for (int i = 1; i < matrix.length; i++) {
                for (int j = 1; j < matrix[0].length; j++) {
                    if (matrix[i][j] == 0) {
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    }
                }
            }
            // 根据第0行的信息归零对应列
            for (int i = 1; i < matrix[0].length; i++) {
                if (matrix[0][i] == 0) {
                    for (int j = 0; j < matrix.length; j++) {
                        matrix[j][i] = 0;
                    }
                }
            }
            // 根据第0列的信息归零对应行
            for (int i = 1; i < matrix.length; i++) {
                if (matrix[i][0] == 0) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j] = 0;
                    }
                }
            }
            // 归零第0行
            if (rowFlag) {
                for (int i = 0; i < matrix[0].length; i++) {
                    matrix[0][i] = 0;
                }
            }
            // 归零第0列
            if (colFlag) {
                for (int i = 0; i < matrix.length; i++) {
                    matrix[i][0] = 0;
                }
            }
        }
    }

    class Solution {
        public void setZeroes(int[][] matrix) {
            boolean[] zeroRows = new boolean[matrix.length], zeroColumns = new boolean[matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == 0) {
                        zeroRows[i] = true;
                        zeroColumns[j] = true;
                    }
                }
            }
            for (int i = 0; i < matrix.length; i++) {
                if (zeroRows[i]) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j] = 0;
                    }
                }
            }
            for (int i = 0; i < matrix[0].length; i++) {
                if (zeroColumns[i]) {
                    for (int j = 0; j < matrix.length; j++) {
                        matrix[j][i] = 0;
                    }
                }
            }
        }
    }
}
