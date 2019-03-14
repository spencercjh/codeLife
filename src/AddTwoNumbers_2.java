/**
 * @author : spencercjh
 * @date : 2019/3/14 18:26
 */
public class AddTwoNumbers_2 {

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Solution {
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
            return node.next;
        }
    }
}

