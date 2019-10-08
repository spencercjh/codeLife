package exam.xiecheng2019;

        import java.util.*;

/**
 * @author SpencerCJH
 * @date 2019/4/8 20:29
 */
public class test3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<List<String>> paths = new ArrayList<>();
        Map<List<String>, Integer> pathMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            List<String> path = Arrays.asList(in.next().trim().split("/"));
            paths.add(path.subList(1, path.size()));
        }
        for (List<String> path : paths) {
            if (!pathMap.containsKey(path)) {
                pathMap.put(path, 0);
            } else {
                pathMap.put(path, pathMap.get(path) + 1);
            }
            int carry = 1 + pathMap.get(path);
            for (int i = 0; i < path.size(); i++) {
                if (i == 0 || i == path.size() - 1) {
                    System.out.print(1);
                } else {
                    System.out.print(carry);
                }
            }
            System.out.print(" ");
        }
    }
}
