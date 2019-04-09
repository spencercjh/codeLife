/**
 * @author SpencerCJH
 * @date 2019/4/9 10:36
 */
public class PlusOne_66 {
    class Solution {
        public int[] plusOne(int[] digits) {
            int carry = 1, n = digits.length;
            for (int i = n - 1; i >= 0; i--) {
                digits[i] = digits[i] + carry;
                carry = digits[i] / 10;
                digits[i] = digits[i] - carry * 10;
            }
            if (carry != 0) {
                int[] extendsArray = new int[n + 1];
                extendsArray[0] = carry;
                System.arraycopy(digits, 0, extendsArray, 1, n);
                return extendsArray;
            } else {
                return digits;
            }
        }
    }
}
