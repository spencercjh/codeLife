package leetcode;

import leetcode.util.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author SpencerCJH
 * @date 2019/4/17 23:58
 */
public class BinaryTreeInorderTraversal_94 {
    class Solution1 {
        private List<Integer> answer = new ArrayList<>();

        public List<Integer> inorderTraversal(TreeNode root) {
            traversal(root);
            return answer;
        }

        private void traversal(TreeNode node) {
            if (node != null) {
                traversal(node.left);
                answer.add(node.val);
                traversal(node.right);
            }
        }
    }

    class Solution2 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> answer = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode current = root;
            while (current != null || !stack.isEmpty()) {
                if (current != null) {
                    stack.push(current);
                    current = current.left;
                } else {
                    current = stack.pop();
                    answer.add(current.val);
                    current = current.right;
                }
            }
            return answer;
        }
    }
}
