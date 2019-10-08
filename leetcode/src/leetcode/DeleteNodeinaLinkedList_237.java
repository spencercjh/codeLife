package leetcode;

import leetcode.util.ListNode;

/**
 * @author spencercjh
 */
public class DeleteNodeinaLinkedList_237 {
    class Solution {
        public void deleteNode(ListNode node) {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }
}
