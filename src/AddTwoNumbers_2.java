import util.ListNode;
/**
 * @author : spencercjh
 * @date : 2019/3/14 18:26
 */
public class AddTwoNumbers_2 {
    class Solution1 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode node = new ListNode(0), current = node;
            int carry = 0;
            while (null != l1 || null != l2) {
                int number1 = l1 != null ? l1.val : 0, number2 = l2 != null ? l2.val : 0, sum = number1 + number2 + carry;
                carry = sum / 10;
                current.next = new ListNode(sum % 10);
                current = current.next;
                if (null != l1) {
                    l1 = l1.next;
                }
                if (null != l2) {
                    l2 = l2.next;
                }
            }
            if (carry > 0) {
                current.next = new ListNode(carry);
            }
            return node.next;
        }
    }

    class Solution2 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int i = 0, number1 = 0, number2 = 0;
            while (l1 != null) {
                number1 += l1.val + 10 * i++;
                l1 = l1.next;
            }
            i = 0;
            while (l2 != null) {
                number2 += l2.val + 10 * i++;
                l2 = l2.next;
            }
            int number3 = number1 + number2;
            ListNode node = null;
            while (number3 != 0) {
                int r = number3 % 10;
                number3 /= 10;
                if (node == null) {
                    node = new ListNode(r);
                } else {
                    node.next = new ListNode(r);
                    node = node.next;
                }
            }
            assert node != null;
            return node.next;
        }
    }
}

