package exam.weipinhui2019;

/**
 * @author : SpencerCJH
 * @date : 2019/9/15 18:57
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        System.out.println(getDiameter(root));
    }

    private static int getDiameter(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getDepth(root.left) + getDepth(root.right), Math.max(getDiameter(root.left), getDiameter(root.right)));
    }

    private static int getDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getDepth(root.left), getDepth(root.right)) + 1;
    }

    static class TreeNode {
        TreeNode left = null;
        TreeNode right = null;
        int val;

        TreeNode(int val) {
            this.val = val;
        }
    }
}
