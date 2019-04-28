package qihu2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Administrator
 */
public class Test2 {
    /******************************开始写代码******************************/
    static int main() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int input = in.nextInt();
            if (map.containsKey(input)) {
                map.put(input, map.get(input) + 1);
            } else {
                map.put(input, 1);
            }
        }
        int gcdValue = 1;
        for (Map.Entry<Integer, Integer> out : map.entrySet()) {
            for (Map.Entry<Integer, Integer> inner : map.entrySet()) {
                if (out == inner) {
                    continue;
                }
                gcdValue = getGCD(Math.max(out.getValue(), inner.getValue()), Math.min(out.getValue(), inner.getValue()));
                if (gcdValue == 1 && !out.getValue().equals(inner.getValue())) {
                    return 0;
                }
            }
        }
        int sum = 0;
        if (gcdValue == 1) {
            return map.size();
        } else {
            for (Integer value : map.values()) {
                sum += value / gcdValue;
            }
            return sum;
        }
    }

    private static int getGCD(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return getGCD(b, a % b);
        }
    }

    /******************************结束写代码******************************/


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;

        res = main();
        System.out.println(String.valueOf(res));

    }
}
