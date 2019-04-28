package leetcode;

import leetcode.util.ListNode;

/**
 * @author spencercjh
 */
public class SwapNodesinPairs_24 {
    class Solution {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode next = head.next;
            head.next = swapPairs(next.next);
            next.next = head;
            return next;
        }
    }
}
