package leetcode;

import leetcode.util.ListNode;

/**
 * 思路from曾文浩:说是循环旋转，但其实本质上是将尾部向前数第K个元素作为头，原来的头接到原来的尾上
 *
 * @author SpencerCJH
 * @date 2019/4/8 18:45
 */
public class RotateList_61 {
    class Solution {
        public ListNode rotateRight(ListNode head, int k) {
            if (null == head) {
                return null;
            }
            ListNode tail = null, newTail = null, newHead = null, current = head;
            int newTailIndex = 0, length = 0;
            while (null != current) {
                length++;
                current = current.next;
            }
            k = k >= length ? k % length : k;
            if (k == 0 || head.next == null) {
                return head;
            }
            current = head;
            while (null != current) {
                newTailIndex++;
                if (newTailIndex == length - k) {
                    newTail = current;
                    newHead = current.next;
                }
                if (current.next == null) {
                    tail = current;
                }
                current = current.next;
            }
            assert newHead != null;
            if (newHead != tail) {
                tail.next = head;
                newTail.next = null;
            } else {
                newTail.next = null;
                newHead.next = head;
            }
            return newHead;
        }
    }
}
