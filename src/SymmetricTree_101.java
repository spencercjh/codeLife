import util.TreeNode;

/**
 * @author SpencerCJH
 * @date 2019/4/18 1:30
 */
public class SymmetricTree_101 {
    class Solution {
        public boolean isSymmetric(TreeNode root) {
            return isMirror(root, root);
        }

        private boolean isMirror(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            } else if (p == null || q == null) {
                return false;
            }
            if (p.val == q.val) {
                return isMirror(p.left, q.right) && isMirror(p.right, q.left);
            }
            return false;
        }
    }
}
