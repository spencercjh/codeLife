package chapter8;

import java.util.function.Function;

/**
 * @author Spencer
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseLists {
    public static <A, B> B foldRight(AdvancedList<A> list, B init, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? init : f.apply(list.head()).apply(foldRight(list.tail(), init, f));
    }

    public static double sum(AdvancedList<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x + y);
    }

    public static int sum(AdvancedList<Integer> list, int init) {
        return foldRight(list, init, (Integer x) -> (Integer y) -> x + y);
    }

    public static int sumViaFoldLeft(AdvancedList<Integer> list, int init) {
        return list.foldLeft(init, (Integer x) -> (Integer y) -> x + y);
    }

    public static double productViaFoldRight(AdvancedList<Double> list, double init) {
        return list.foldLeft(init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> int lengthViaFoldLeft(AdvancedList<A> list) {
        return list.foldLeft(0, (Integer x) -> (A useless) -> x + 1);
    }

    public static <A> AdvancedList<A> reverseViaFoldLeft(AdvancedList<A> list) {
        return list.foldLeft(AdvancedList.list(), x -> x::construct);
    }

    public static <A, B> B foldRightViaFoldLeft(AdvancedList<A> list, B identity, Function<A, Function<B, B>> f) {
        return list.reverse().foldLeft(identity, (B x) -> (A y) -> f.apply(y).apply(x));
    }

    public static double product(AdvancedList<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> AdvancedList<A> concatViaFoldRight(AdvancedList<A> list1, AdvancedList<A> list2) {
        return foldRight(list1, list2, x -> y -> AdvancedList.construct(x, y));
    }

    public static <A> AdvancedList<A> flatten(AdvancedList<AdvancedList<A>> list) {
        return foldRight(list, AdvancedList.list(), x -> y -> concatViaFoldRight(x, y));
    }

    public static AdvancedList<Integer> triple(AdvancedList<Integer> list) {
        return foldRight(list, AdvancedList.list(), (Integer x) -> (AdvancedList<Integer> y) -> y.construct(x * 3));
    }

    public static AdvancedList<String> doubleToString(AdvancedList<Double> list) {
        return foldRight(list, AdvancedList.list(), (Double x) -> (AdvancedList<String> y) -> y.construct(x.toString()));
    }

    public static <A> AdvancedList<A> filterViaFlatMap(AdvancedList<A> list, Function<A, Boolean> f) {
        return list.flatMap((A a) -> f.apply(a) ? AdvancedList.list(a) : AdvancedList.list());
    }

    public static <A> AdvancedList<A> flattenViaFlatMap(AdvancedList<AdvancedList<A>> list) {
        return list.flatMap((AdvancedList<A> x) -> x);
    }

    public static <A> AdvancedList<A> concat(AdvancedList<A> list1, AdvancedList<A> list2) {
        return list1.isEmpty() ? list2 : AdvancedList.construct(list1.head(), concat(list1.tail(), list2));
    }
}
