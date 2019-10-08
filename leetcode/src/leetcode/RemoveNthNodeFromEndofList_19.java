package leetcode;

import leetcode.util.ListNode;

/**
 * @author spencercjh
 */
public class RemoveNthNodeFromEndofList_19 {
    class Solution1 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0), first = head;
            dummy.next = head;
            int length = 0;
            while (first != null) {
                first = first.next;
                length++;
            }
            length -= n;
            first = dummy;
            while (length > 0) {
                length--;
                first = first.next;
            }
            first.next = first.next.next;
            return dummy.next;
        }
    }

    class Solution2 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode first = dummy, second = dummy;
            for (int i = 1; i <= n + 1; i++) {
                first = first.next;
            }
            while (first != null) {
                first = first.next;
                second = second.next;
            }
            second.next = second.next.next;
            return dummy.next;
        }
    }
}
