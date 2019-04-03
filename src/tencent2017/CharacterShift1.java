package tencent2017;

import java.util.Scanner;

/**
 * https://www.nowcoder.com/questionTerminal/7e8aa3f9873046d08899e0b44dac5e43
 * 字符移位
 *
 * @author spencercjh
 */
public class CharacterShift1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            for (Character ch : str.toCharArray()) {
                if (Character.isLowerCase(ch)) {
                    System.out.print(ch);
                }
            }
            for (Character ch : str.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    System.out.print(ch);
                }
            }
            System.out.println();
        }
    }
}
