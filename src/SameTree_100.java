import util.TreeNode;

/**
 * @author SpencerCJH
 * @date 2019/4/18 1:16
 */
public class SameTree_100 {
    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {

            if (p == null && q == null) {
                return true;
            }
            if (p != null && q != null && p.val == q.val) {
                return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
            } else {
                return false;
            }
        }
    }
}
