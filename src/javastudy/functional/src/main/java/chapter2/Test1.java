package chapter2;

import static chapter2.IntegerFunction.compose;

/**
 * @author Spencer
 * Page 30 31
 */
public class Test1 {
    public static void main(String[] args) {
        IntegerFunction triple = new IntegerFunction() {
            @Override
            public int apply(int arg) {
                return arg * 3;
            }
        };
        IntegerFunction square = new IntegerFunction() {
            @Override
            public int apply(int arg) {
                return arg * arg;
            }
        };
        System.out.println(triple.apply(3));
        System.out.println(square.apply(triple.apply(2)));
        System.out.println(compose(triple, square).apply(3));
        triple.request(triple);
    }


}
