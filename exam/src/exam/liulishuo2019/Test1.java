package exam.liulishuo2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : SpencerCJH
 * @date : 2019/9/11 21:25
 */
public class Test1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), aAmount = in.nextInt(), bAmount = in.nextInt();
        List<Node> goods = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Node temp = new Node(in.nextInt(), in.nextInt());
            goods.add(temp);
        }
        boolean aMore = false, bMore = false;
        if (bAmount > aAmount) {
            goods.sort((o1, o2) -> myCompare(o2.bCost, o1.bCost));
            bMore = true;
        } else {
            goods.sort((o1, o2) -> myCompare(o2.aCost, o1.aCost));
            aMore = true;
        }
        // goods.forEach(good -> System.out.println(good.aCost + "\t" + good.bCost));
        int sum = 0;
        for (int i = 0; i < goods.size(); i++) {
            if (aMore && i < aAmount) {
                sum += goods.get(i).aCost;
            } else if (bMore && i < bAmount) {
                sum += goods.get(i).bCost;
            } else if (aMore) {
                sum += goods.get(i).bCost;
            } else if (bMore) {
                sum += goods.get(i).aCost;
            }
        }
        System.out.println(sum);
    }

    private static int myCompare(int x, int y) {
        return Integer.compare(y, x);
    }

    static class Node {
        int aCost;
        int bCost;

        Node(int aCost, int bCost) {
            this.aCost = aCost;
            this.bCost = bCost;
        }
    }
}
