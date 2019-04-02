/**
 * @author spencercjh
 */
public class MergeTwoSortedLists_21 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Solution {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            ListNode newListNode = new ListNode(0), current = newListNode;
            while (null != l1 && null != l2) {
                if (l1.val <= l2.val) {
                    current.next = new ListNode(l1.val);
                    l1 = l1.next;
                } else {
                    current.next = new ListNode(l2.val);
                    l2 = l2.next;
                }
                current = current.next;
            }
            if (null == l1) {
                current.next = l2;
            }
            if (null == l2) {
                current.next = l1;
            }
            return newListNode.next;
        }
    }
}
