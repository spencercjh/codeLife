package util;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 22:19
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    /**
     * 输入一个int数组返回这个数组的头结点
     *
     * @param nums nums
     * @return ListNode
     */
    public static ListNode makeListNodeChain(int[] nums) {
        ListNode head = new ListNode(-1), current = head;
        for (int i = 0; i < nums.length; i++) {
            assert current != null;
            current.val = nums[i];
            if (i == nums.length - 1) {
                current.next = null;
            } else {
                current.next = new ListNode(-1);
            }
            current = current.next;
        }
        return head;
    }
}
