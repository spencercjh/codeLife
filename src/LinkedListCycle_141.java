import java.util.HashSet;
import java.util.Set;

/**
 * @author spencercjh
 */
public class LinkedListCycle_141 {
    /**
     * Definition for singly-linked list.
     */
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public class Solution1 {
        public boolean hasCycle(ListNode head) {
            if (null == head || null == head.next) {
                return false;
            }
            ListNode slow = head, fast = head.next;
            while (slow != fast) {
                if (null == fast || null == fast.next) {
                    return false;
                } else {
                    slow = slow.next;
                    fast = fast.next.next;
                }
            }
            return true;
        }

        public class Solution2 {
            public boolean hasCycle(ListNode head) {
                Set<ListNode> set = new HashSet<>();
                while (null != head && null != head.next) {
                    if (set.contains(head)) {
                        return true;
                    } else {
                        set.add(head);
                    }
                    head = head.next;
                }
                return false;
            }
        }
    }
}
