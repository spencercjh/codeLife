package xiecheng2019;

import java.util.*;

/**
 * @author SpencerCJH
 * @date 2019/4/8 20:00
 */
public class test2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String numbersString = in.next();
        int k = in.nextInt();
        List<Integer> nums = new LinkedList<>();
        List<Integer> answer = new ArrayList<>();
        String[] numbersStrings = numbersString.substring(1, numbersString.length() - 1).split(",");
        for (String string : numbersStrings) {
            nums.add((int) string.charAt(0) - '0');
        }
        System.out.print("[");
        boolean isFirst = true;
        for (int i = 0; i < nums.size(); i += k) {
            List<Integer> subList;
            if (i + k < nums.size()) {
                subList = nums.subList(i, i + k);
            } else {
                subList = nums.subList(i, nums.size());
            }
            Collections.reverse(subList);
            for (int n :
                    subList) {
                if (isFirst) {
                    isFirst = false;
                    System.out.print(n);
                } else {
                    System.out.print("," + n);
                }
            }
        }
        System.out.print("]");
    }
}
