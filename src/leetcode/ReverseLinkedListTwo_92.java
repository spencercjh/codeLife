package leetcode;

import leetcode.util.ListNode;

/**
 * @author : SpencerCJH
 * @date : 2019/4/12 3:41
 */
public class ReverseLinkedListTwo_92 {
    class Solution {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            int count = n - m;
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            ListNode preNode = dummy, mNode = head;
            while (--m > 0) {
                preNode = preNode.next;
                mNode = mNode.next;
            }
            while (count-- > 0) {
                ListNode nextNode = mNode.next;
                mNode.next = nextNode.next;
                nextNode.next = preNode.next;
                preNode.next = nextNode;
            }
            return dummy.next;
        }
    }
}
