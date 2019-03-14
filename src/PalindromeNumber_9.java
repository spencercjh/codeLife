/**
 * @author : spencercjh
 * @date : 2019/3/14 23:15
 */
public class PalindromeNumber_9 {
    class Solution1 {
        public boolean isPalindrome(int x) {
            if (x < 0 || x % 10 == 0 && x != 0) {
                return false;
            }
            String number = String.valueOf(x);
            char[] n = number.toCharArray();
            StringBuilder reverse = new StringBuilder();
            for (int i = n.length - 1; i >= 0; --i) {
                reverse.append(n[i]);
            }
            return reverse.toString().equals(number);
        }
    }

    class Solution2 {
        public boolean isPalindrome(int x) {
            if (x < 0 || x % 10 == 0 && x != 0) {
                return false;
            }
            int reverse = 0;
            while (x > reverse) {
                reverse = reverse * 10 + x % 10;
                x /= 10;
            }
            return x == reverse || x == reverse / 10;
        }
    }
}
