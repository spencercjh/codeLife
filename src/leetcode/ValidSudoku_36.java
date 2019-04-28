package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class ValidSudoku_36 {
    class Solution {
        private int n = 9;

        public boolean isValidSudoku(char[][] board) {
            Map<Integer, Integer>[] rowMaps = new Map[n];
            Map<Integer, Integer>[] columMaps = new Map[n];
            Map<Integer, Integer>[] subBoxMaps = new Map[n];
            for (int i = 0; i < n; i++) {
                rowMaps[i] = new HashMap<>();
                columMaps[i] = new HashMap<>();
                subBoxMaps[i] = new HashMap<>();
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ('.' != board[i][j]) {
                        int number = (int) board[i][j], boxIndex = (i / 3) * 3 + (j / 3);
                        rowMaps[i].put(number, rowMaps[i].getOrDefault(number, 0) + 1);
                        columMaps[j].put(number, columMaps[j].getOrDefault(number, 0) + 1);
                        subBoxMaps[boxIndex].put(number, subBoxMaps[boxIndex].getOrDefault(number, 0) + 1);
                        if (rowMaps[i].get(number) > 1 ||
                                columMaps[j].get(number) > 1 ||
                                subBoxMaps[boxIndex].get(number) > 1) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
}
