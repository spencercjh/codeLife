package leetcode;

import leetcode.util.ListNode;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 21:52
 */
public class RemoveDuplicatesfromSortedListTwo_82 {
    public static void main(String[] args) {
        Solution2.deleteDuplicates(ListNode.makeListNodeChain(new int[]{1, 2, 3, 3, 3, 4, 4, 5}));
    }

    static class Solution2 {
        static public ListNode deleteDuplicates(ListNode head) {
            ListNode dummyNode = new ListNode(-1), current = head;
            dummyNode.next = head;
            ListNode previous = dummyNode;
            int count = 0;
            while (current != null && current.next != null) {
                if (current.val == current.next.val) {
                    count++;
                } else {
                    if (count > 0) {
                        previous.next = current.next;
                    } else {
                        previous = previous.next;
                    }
                    count = 0;
                }
                current = current.next;
            }
            if (count > 0) {
                previous.next = null;
            }
            return dummyNode.next;
        }
    }

    class Solution1 {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode previousNode = null, currentNode = head;
            while (currentNode != null) {
                ListNode nextNode = currentNode.next;
                if (currentNode.next != null && currentNode.val == nextNode.val) {
                    ListNode toDeleteNode = currentNode;
                    while (toDeleteNode != null && toDeleteNode.val == currentNode.val) {
                        nextNode = toDeleteNode.next;
                        toDeleteNode = toDeleteNode.next;
                    }
                    if (previousNode == null) {
                        head = nextNode;
                    } else {
                        previousNode.next = nextNode;
                    }
                    currentNode = nextNode;
                } else {
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
            }
            return head;
        }
    }
}
