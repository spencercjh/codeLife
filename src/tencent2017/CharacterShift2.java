package tencent2017;

import java.util.Scanner;

/**
 * @author spencercjh
 */
public class CharacterShift2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            char[] str = sc.next().toCharArray();
            int n = str.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (Character.isUpperCase(str[j]) &&
                            Character.isLowerCase(str[j + 1])) {
                        char temp = str[j];
                        str[j] = str[j + 1];
                        str[j + 1] = temp;
                    }
                }
            }
            System.out.println(str);
        }
    }
}
