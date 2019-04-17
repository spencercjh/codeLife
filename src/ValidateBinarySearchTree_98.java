import util.TreeNode;

/**
 * 卡边界：[-2147483648]
 *
 * @author SpencerCJH
 * @date 2019/4/18 0:59
 */
public class ValidateBinarySearchTree_98 {
    class Solution {
        private double previous = -Double.MAX_VALUE;

        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            if (isValidBST(root.left)) {
                if (previous < root.val) {
                    previous = root.val;
                    return isValidBST(root.right);
                }
            }
            return false;
        }
    }
}
