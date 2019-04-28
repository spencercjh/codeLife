package leetcode;

/**
 * @author spencercjh
 */
public class MultiplyStrings_43 {
    class Solution {
        public String multiply(String num1, String num2) {
            int n1 = num1.length() - 1, n2 = num2.length() - 1;
            int[] num3 = new int[n1 + n2 + 2];
            for (int i = n1; i >= 0; i--) {
                for (int j = n2; j >= 0; j--) {
                    int bitMulti = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                    bitMulti += num3[i + j + 1];
                    num3[i + j + 1] = bitMulti % 10;
                    num3[i + j] += bitMulti / 10;
                }
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < num3.length - 1 && num3[i] == 0) {
                i++;
            }
            for (; i < num3.length; i++) {
                sb.append(num3[i]);
            }
            return sb.toString();
        }
    }
}
