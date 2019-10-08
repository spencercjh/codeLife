package exam.bytecode2019;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author spencercjh
 */
public class test3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while (n-- > 0) {
            int m = in.nextInt();
            int[] scores = new int[m];
            int[] prices = new int[m];
            for (int i = 0; i < m; ++i) {
                scores[i] = in.nextInt();
                prices[i] = 1;
            }
            for (int i = 1; i < m; ++i) {
                if (scores[i] > scores[i - 1] && prices[i] <= prices[i - 1]) {
                    prices[i] = prices[i - 1] + 1;
                } else if (scores[i] < scores[i - 1] && prices[i] >= prices[i - 1]) {
                    prices[i - 1] = prices[i] + 1;
                }
                if (scores[i] > scores[(i + 1) % m] && prices[i] <= prices[(i + 1) % m]) {
                    prices[i] = prices[(i + 1) % m] + 1;
                } else if (scores[i] < scores[(i + 1) % m] && prices[i] >= prices[(i + 1) % m]) {
                    prices[(i + 1) % m] = prices[i] + 1;
                }
            }
            int ans = 0;
            for (int i = 0; i < m; ++i) {
                ans += prices[i];
            }
            System.out.println(Arrays.toString(prices));
            System.out.println(ans);
        }
    }
}
