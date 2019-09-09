package exam.qianxin2019;

import java.util.*;

/**
 * 91%
 * @author : SpencerCJH
 * @date : 2019/9/9 19:41
 */
public class Test1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pidStringA = scanner.nextLine(), pidStringB = scanner.nextLine();
        int killPid = scanner.nextInt();
        String[] pids = pidStringA.split(" ");
        String[] parents = pidStringB.split(" ");
        int[] pidArray = new int[pids.length];
        int[] parentArray = new int[parents.length];
        for (int i = 0; i < pids.length; i++) {
            pidArray[i] = Integer.parseInt(pids[i]);
            parentArray[i] = Integer.parseInt(parents[i]);
        }
        Map<Integer, List<Integer>> tree = new HashMap<>();
        for (int i = 0; i < pidArray.length; i++) {
            if (tree.get(parentArray[i]) == null || tree.get(parentArray[i]).isEmpty()) {
                List<Integer> list = new ArrayList<>();
                list.add(pidArray[i]);
                tree.put(parentArray[i], list);
            } else {
                List<Integer> list = tree.get(parentArray[i]);
                list.add(pidArray[i]);
                tree.put(parentArray[i], list);
            }
        }
        /*tree.forEach((integer, integers) -> {
            System.out.print(integer + "\tlist:");
            integers.forEach(x -> System.out.print(x + "\t"));
            System.out.println();
        });*/
        System.out.println(count(tree, killPid)+1);
    }

    private static int count(Map<Integer, List<Integer>> map, int key) {
        int sum = 0;
        List<Integer> list = map.get(key);
        if (list != null && !list.isEmpty()) {
            for (Integer x : map.get(key)) {
                sum++;
                sum += count(map, x);
            }
        }
        return sum;
    }
}
