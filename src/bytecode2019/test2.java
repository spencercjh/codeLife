package bytecode2019;

import java.util.Scanner;

/**
 * @author spencercjh
 */
public class test2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (n == 0) {
            return;
        }
        while (n-- > 0) {
            String str = in.next();
            String origin;
            StringBuilder edit;
            while (true) {
                boolean flag = false;
                for (int i = 0; i < str.length(); ) {
                    try {
                        if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i) == str.charAt(i + 2)) {
                            origin = str.substring(i, i + 3);
                            edit = new StringBuilder();
                            edit.append(str.charAt(i)).append(str.charAt(i + 1));
                            str = str.replace(origin, edit);
                            i += 3;
                            flag = true;
                        }
                        if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i + 2) == str.charAt(i + 3) && str.charAt(i) != str.charAt(i + 2)) {
                            origin = str.substring(i, i + 4);
                            edit = new StringBuilder();
                            edit.append(str.charAt(i)).append(str.charAt(i + 1)).append(str.charAt(i + 2));
                            str = str.replace(origin, edit);
                            i += 4;
                            flag = true;
                        }
                        if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i + 2) == str.charAt(i + 3) && str.charAt(i) != str.charAt(i + 2) && str.charAt(i + 4) == str.charAt(i + 5) && str.charAt(i + 2) != str.charAt(i + 4)) {
                            origin = str.substring(i, i + 6);
                            edit = new StringBuilder();
                            edit.append(str.charAt(i)).append(str.charAt(i + 1)).append(str.charAt(i + 2)).append(str.charAt(i + 4)).append(str.charAt(i + 5));
                            str = str.replace(origin, edit);
                            i += 6;
                            flag = true;
                        }
                    } catch (Exception e) {
                        break;
                    }
                    if (!flag) {
                        ++i;
                    }
                    if (i + 2 >= str.length()) {
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            System.out.println(str);
        }
    }
}
