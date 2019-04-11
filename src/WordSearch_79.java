/**
 * @author : SpencerCJH
 * @date : 2019/4/11 20:42
 */
public class WordSearch_79 {
    class Solution {
        public boolean exist(char[][] board, String word) {
            boolean result;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == word.charAt(0)) {
                        result = dfs(board, word, i, j, 0);
                        if (result) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private boolean dfs(char[][] board, String word, int i, int j, int index) {
            if (index == word.length()) {
                return true;
            }
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return false;
            }
            if (board[i][j] != word.charAt(index)) {
                return false;
            }
            board[i][j] <<= 2;
            boolean result = dfs(board, word, i - 1, j, index + 1) ||
                    dfs(board, word, i + 1, j, index + 1) ||
                    dfs(board, word, i, j - 1, index + 1) ||
                    dfs(board, word, i, j + 1, index + 1);
            board[i][j] >>= 2;
            return result;
        }
    }
}
