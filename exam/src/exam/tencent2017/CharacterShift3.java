package exam.tencent2017;

import java.util.Scanner;

/**
 * @author spencercjh
 */
public class CharacterShift3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            System.out.println(str.replaceAll("[A-Z]", "") +
                    str.replaceAll("[a-z]", ""));
        }
    }
}
