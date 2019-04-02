/**
 * @author spencercjh
 */
public class CountandSay_38 {
    class Solution {
        public String countAndSay(int n) {
            String res = "1";
            StringBuilder temp;
            int t;
            while (--n > 0) {
                t = 0;
                temp = new StringBuilder();
                // 对当前res里每个字符进行遍历，t表当前字符下标
                while (t < res.length()) {
                    int num = 1;
                    // 数连续且相同的数有几个，计入num
                    while ((t + 1) < res.length() && res.charAt(t) == res.charAt(t + 1)) {
                        t++;
                        num++;
                    }
                    // temp最终为为下一个报数生成的值
                    temp.append(num).append(res.charAt(t));
                    t++;
                }
                // temp赋值res进行下一轮报数
                res = temp.toString();
            }
            return res;
        }
    }
}
