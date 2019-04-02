/**
 * @author spencercjh
 */
public class AddBinary_67 {
    class Solution1 {
        public String addBinary(String a, String b) {
            StringBuilder sb = new StringBuilder();
            for (int carry = 0, i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0 || carry != 0; ) {
                if (i >= 0) {
                    carry += a.charAt(i--) - '0';
                }
                if (j >= 0) {
                    carry += b.charAt(j--) - '0';
                }
                sb.append(carry % 2);
                carry /= 2;
            }
            return sb.reverse().toString();
        }
    }

    class Solution2 {
        public String addBinary(String a, String b) {
            StringBuilder sb = new StringBuilder();
            for (int carry = 0, i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0 || carry != 0; ) {
                if (i >= 0) {
                    carry += a.charAt(i--) - '0';
                }
                if (j >= 0) {
                    carry += b.charAt(j--) - '0';
                }
                sb.insert(0, carry % 2);
                carry /= 2;
            }
            return sb.toString();
        }
    }
}
