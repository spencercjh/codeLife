import util.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SpencerCJH
 * @date 2019/4/18 0:37
 */
public class UniqueBinarySearchTreesTwo_95 {
    class Solution {
        public List<TreeNode> generateTrees(int n) {
            if (n == 0) {
                return new ArrayList<>();
            }
            return generateTrees(1, n);
        }

        private List<TreeNode> generateTrees(int start, int end) {
            List<TreeNode> res = new ArrayList<>();
            if (start > end) {
                res.add(null);
                return res;
            }
            for (int i = start; i <= end; i++) {
                List<TreeNode> subLeftTree = generateTrees(start, i - 1);
                List<TreeNode> subRightTree = generateTrees(i + 1, end);
                for (TreeNode left : subLeftTree) {
                    for (TreeNode right : subRightTree) {
                        TreeNode node = new TreeNode(i);
                        node.left = left;
                        node.right = right;
                        res.add(node);
                    }
                }
            }
            return res;
        }
    }
}
