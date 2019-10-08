package leetcode;

import leetcode.util.ListNode;

/**
 * @author : SpencerCJH
 * @date : 2019/4/12 4:40
 */
public class ReverseLinkedList_206 {
    class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode current = head, previous = null;
            while (current != null) {
                ListNode next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }
            return previous;
        }
    }
}
