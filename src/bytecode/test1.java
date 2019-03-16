package bytecode;

import java.util.Scanner;

/**
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