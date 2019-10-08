package exam.shangtang2019;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * cover case 93.75%
 *
 * @author : SpencerCJH
 * @date : 2019/8/19 19:13
 */
public class Test1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String numberString1 = in.next();
        String numberString2 = in.next();
        try {
            BigInteger a = new BigInteger(numberString1, 7), b = new BigInteger(numberString2, 7);
            System.out.println(a.add(b).toString(7));
        } catch (Exception e) {
            System.out.println("NA");
        }
    }
}