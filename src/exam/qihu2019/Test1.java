package exam.qihu2019;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Test1 {

    static Pattern NUMBER = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
    static Pattern INTEGER = Pattern.compile("^(-?\\d+)$");

    static int string2int(String str) {
        if (str == null || !NUMBER.matcher(str).matches()) {
            return 0;
        }
        boolean negative = false;
        if ('-' == str.charAt(0)) {
            negative = true;
            str = str.substring(1);
        }
        if (!INTEGER.matcher(str).matches()) {
            str = str.substring(0, str.indexOf('.'));
        }
        int num = 0;
        for (char ch : str.toCharArray()) {
            num = num * 10 + ch - '0';
        }
        if (negative) {
            num *= -1;
        }
        return num;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;

        String _str;
        try {
            _str = in.nextLine();
        } catch (Exception e) {
            _str = null;
        }

        res = string2int(_str);
        System.out.println(String.valueOf(res));

    }
}
