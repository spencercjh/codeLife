package exam.shangtang2019;

import java.util.Scanner;

/**
 * example:
 * input:
 * 3 3
 * 0 1 3
 * 5 10 2
 * 1 7 0
 * output:
 * 19
 * input:
 * 3 3
 * 0 -1 3
 * 5 -1 2
 * 1 7 0
 * output:
 * -1
 * <p>
 * Dijkstra and Sign
 *
 * @author : SpencerCJH
 * @date : 2019/8/19 19:29
 */
public class Test3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt(), column = scanner.nextInt();
        int[][] matrix = new int[row][column];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }
}
