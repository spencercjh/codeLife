package exam.bytecode2019;

import java.util.Scanner;

/**
 * Z国的货币系统包含面值1元、4元、16元、64元共计4种硬币，以及面值1024元的纸币。现在小Y使用1024元的纸币购买了一件价值为的商品，请问最少他会收到多少硬币？
 * https://www.nowcoder.com/question/next?pid=16516564&qid=362294&tid=24564608
 *
 * @author spencercjh
 */
public class test1 {
    private static int[] cash = new int[10];

    static {
        cash[0] = 64;
        cash[1] = 16;
        cash[2] = 4;
        cash[3] = 1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (n == 1024) {
            System.out.println(0);
            return;
        }
        int rest = 1024 - n, sum = 0;
        while (rest != 0) {
            int cash = getMaxCash(rest);
            sum += rest / cash;
            rest %= cash;
        }
        System.out.println(sum);
    }

    private static int getMaxCash(int rest) {
        for (int i = 0; i < cash.length; ++i) {
            if (rest >= cash[i]) {
                return cash[i];
            }
        }
        return cash[3];
    }
}