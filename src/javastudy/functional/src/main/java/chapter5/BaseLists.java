package chapter5;

import java.util.function.Function;

/**
 * @author Spencer
 */
public abstract class BaseLists {
    public static <A, B> B foldRight(List<A> list, B init, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? init : f.apply(list.head()).apply(foldRight(list.tail(), init, f));
    }

    public static double sum(List<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x + y);
    }

    public static int sum(List<Integer> list, int init) {
        return foldRight(list, init, (Integer x) -> (Integer y) -> x + y);
    }

    public static int sumViaFoldLeft(List<Integer> list, int init) {
        return list.foldLeft(init, (Integer x) -> (Integer y) -> x + y);
    }

    public static double productViaFoldRight(List<Double> list, double init) {
        return list.foldLeft(init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> int lengthViaFoldLeft(List<A> list) {
        return list.foldLeft(0, (Integer x) -> (A useless) -> x + 1);
    }

    public static <A> List<A> reverseViaFoldLeft(List<A> list) {
        return list.foldLeft(List.list(), x -> x::construct);
    }

    public static <A, B> B foldRightViaFoldLeft(List<A> list, B identity, Function<A, Function<B, B>> f) {
        return list.reverse().foldLeft(identity, (B x) -> (A y) -> f.apply(y).apply(x));
    }

    public static double product(List<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> List<A> concatViaFoldRight(List<A> list1, List<A> list2) {
        return foldRight(list1, list2, x -> y -> List.construct(x, y));
    }

    public static <A> List<A> flatten(List<List<A>> list) {
        return foldRight(list, List.list(), x -> y -> concatViaFoldRight(x, y));
    }

    public static List<Integer> triple(List<Integer> list) {
        return foldRight(list, List.list(), (Integer x) -> (List<Integer> y) -> y.construct(x * 3));
    }

    public static List<String> doubleToString(List<Double> list) {
        return foldRight(list, List.list(), (Double x) -> (List<String> y) -> y.construct(x.toString()));
    }

    public static <A> List<A> filterViaFlatMap(List<A> list, Function<A, Boolean> f) {
        return list.flatMap((A a) -> f.apply(a) ? List.list(a) : List.list());
    }

    public static <A> List<A> flattenViaFlatMap(List<List<A>> list) {
        return list.flatMap((List<A> x) -> x);
    }

    public static <A> List<A> concat(List<A> list1, List<A> list2) {
        return list1.isEmpty() ? list2 : List.construct(list1.head(), concat(list1.tail(), list2));
    }

}
