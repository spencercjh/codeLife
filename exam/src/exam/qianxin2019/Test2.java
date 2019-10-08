package exam.qianxin2019;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 71%/100%
 * @author : SpencerCJH
 * @date : 2019/9/9 20:08
 */
public class Test2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = scanner.nextLine();
        int pValue = scanner.nextInt(), qValue = scanner.nextInt();
        List<Integer> datas = Arrays.stream(input.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        TreeNode root = creatTree(datas, 0);
        TreeNode answer = lowestCommonAncestor(root, new TreeNode(pValue), new TreeNode(qValue));
        System.out.println(answer != null ? answer.val : -1);
    }

    private static TreeNode creatTree(List<Integer> data, int i) {
        if (i >= data.size() || data.get(i) == -1) {
            return null;
        }
        TreeNode node = new TreeNode(data.get(i));
        node.left = creatTree(data, i * 2 + 1);
        node.right = creatTree(data, i * 2 + 2);
        return node;
    }

    private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if ((root.val - p.val) * (root.val - q.val) <= 0) {
            return root;
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return lowestCommonAncestor(root.left, p, q);
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
