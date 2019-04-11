import util.ListNode;

/**
 * @author spencercjh
 */
public class RemoveDuplicatesfromSortedList_83 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            ListNode current = head;
            while ((null != current && null != current.next)) {
                if (current.val == current.next.val) {
                    current.next = current.next.next;
                } else {
                    current = current.next;
                }
            }
            return head;
        }
    }
}