package leetcode;

import leetcode.util.ListNode;

/**
 * @author : SpencerCJH
 * @date : 2019/4/12 0:19
 */
public class PartitionList_86 {
    class Solution {
        public ListNode partition(ListNode head, int x) {
            ListNode lowerHead = new ListNode(-1), lowerCurrent = lowerHead;
            ListNode higherHead = new ListNode(-1), higherCurrent = higherHead;
            while (head != null) {
                if (head.val < x) {
                    lowerCurrent.next = head;
                    head = head.next;
                    lowerCurrent = lowerCurrent.next;
                    lowerCurrent.next = null;
                } else {
                    higherCurrent.next = head;
                    head = head.next;
                    higherCurrent = higherCurrent.next;
                    higherCurrent.next = null;
                }
            }
            lowerCurrent.next = higherHead.next;
            return lowerHead.next;
        }
    }
}
