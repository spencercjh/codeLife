/**
 * @author SpencerCJH
 * @date 2019/4/9 10:17
 */
public class UniquePathsTwo_63 {
    class Solution {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int row = obstacleGrid.length;
            int column = obstacleGrid[0].length;
            int[][] dp = new int[row][column];
            if (obstacleGrid[0][0] == 1) {
                dp[0][0] = 0;
            } else {
                dp[0][0] = 1;
            }
            for (int i = 1; i < column; i++) {
                if (obstacleGrid[0][i] == 1) {
                    dp[0][i] = 0;
                } else {
                    dp[0][i] = dp[0][i - 1];
                }
            }
            for (int i = 1; i < row; i++) {
                if (obstacleGrid[i][0] == 1) {
                    dp[i][0] = 0;
                } else {
                    dp[i][0] = dp[i - 1][0];
                }
            }
            for (int i = 1; i < row; i++) {
                for (int j = 1; j < column; j++) {
                    if (obstacleGrid[i][j] == 1) {
                        dp[i][j] = 0;
                    } else {
                        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                    }
                }
            }
            return dp[row - 1][column - 1];
        }
    }
}
