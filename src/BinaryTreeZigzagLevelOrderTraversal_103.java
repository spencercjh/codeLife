import util.TreeNode;

import java.util.*;

/**
 * @author : SpencerCJH
 * @date : 2019/4/20 0:35
 */
public class BinaryTreeZigzagLevelOrderTraversal_103 {
    class Solution {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return Collections.emptyList();
            }
            List<List<Integer>> res = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int count = queue.size();
                List<Integer> list = new ArrayList<>();
                while (count > 0) {
                    TreeNode treeNode = queue.poll();
                    assert treeNode != null;
                    list.add(treeNode.val);
                    if (treeNode.left != null) {
                        queue.add(treeNode.left);
                    }
                    if (treeNode.right != null) {
                        queue.add(treeNode.right);
                    }
                    count--;
                }
                res.add((res.size() % 2 == 0) ? list : converseSort(list));
            }
            return res;
        }

        private List<Integer> converseSort(List<Integer> list) {
            Collections.reverse(list);
            return list;
        }
    }
}
