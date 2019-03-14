/**
 * @author : spencercjh
 * @date : 2019/3/14 22:47
 */
public class ReverseInteger_7 {
    class Solution {
        public int reverse(int x) {
            int reverse = 0;
            while (x != 0) {
                int temp = reverse * 10 + x % 10;
                if (temp / 10 != reverse) {
                    return 0;
                }
                reverse = temp;
                x /= 10;
            }
            return reverse;
        }
    }
}
