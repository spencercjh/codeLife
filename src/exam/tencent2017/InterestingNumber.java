package exam.tencent2017;

import java.util.*;

/**
 * https://www.nowcoder.com/questionTerminal/af709ab9ca57430886632022e543d4c6
 * 有趣的数字
 *
 * @author spencercjh
 */
public class InterestingNumber {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }
            Arrays.sort(a);
            //如果数组中的值全相同，直接两两组合
            if (a[0] == a[n - 1]) {
                int tmp = (n * (n - 1)) / 2;
                System.out.println(tmp + " " + tmp);
                continue;
            }
            //map用来统计
            Map<Integer, Integer> map = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                if (map.containsKey(a[i])) {
                    map.put(a[i], map.get(a[i]) + 1);
                } else {
                    map.put(a[i], 1);
                }
            }
            //求差最小个数
            int minRes = 0;
            if (map.size() == n) {
                int min = Math.abs(a[1] - a[0]);
                for (int i = 1; i < n; i++) {
                    int tmp = Math.abs(a[i] - a[i - 1]);
                    if (tmp == min) {
                        minRes++;
                    } else if (tmp < min) {
                        min = tmp;
                        minRes = 1;
                    }
                }
            } else {
                for (Integer key : map.keySet()) {
                    int val = map.get(key);
                    if (val > 1) {
                        minRes += (val * (val - 1)) / 2;
                    }
                }
            }
            //求差最大个数
            int maxRes;
            List<Integer> keySet = new ArrayList<>(map.keySet());
            int val1 = map.get(keySet.get(0));
            int val2 = map.get(keySet.get(keySet.size() - 1));
            maxRes = val1 * val2;
            System.out.println(minRes + " " + maxRes);
        }
    }
}
