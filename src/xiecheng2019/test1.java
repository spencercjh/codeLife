package xiecheng2019;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author SpencerCJH
 * @date 2019/4/8 19:35
 */
public class test1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        String[] nodes = input.split(",");
        ListNode head = new ListNode(nodes[0].charAt(0)), current = head;
        for (int i = 1; i < nodes.length; i++) {
            ListNode temp = new ListNode(nodes[i].charAt(0));
            current.next = temp;
            current = temp;
        }
        System.out.println(hasCycle(head));
    }

    private static boolean hasCycle(ListNode head) {
        Set<Character> set = new HashSet<>();
        while (null != head) {
            if (set.contains(head.val)) {
                return true;
            } else {
                set.add(head.val);
            }
            head = head.next;
        }
        return false;
    }

    static class ListNode {
        char val;
        ListNode next;

        ListNode(char x) {
            val = x;
            next = null;
        }

        @Override
        public int hashCode() {
            return Character.valueOf(val).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ListNode) {
                ListNode temp = (ListNode) obj;
                return temp.val == this.val;
            }
            return false;
        }
    }
}
