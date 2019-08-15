package chapter6;

import chapter5.List;

import java.util.function.Function;

import static chapter5.List.sum;

/**
 * @author Spencer
 */
public interface OptionLists {
    /**
     * 计算一个列表的平均值
     * @param list double list
     * @return AbstractOption<Double>
     */
    static Option<Double> average(List<Double> list) {
        return list.isEmpty() ?
                Option.none() :
                Option.some(sum(list, 0) / list.length());
    }

    /**
     * 计算一个列表的方差
     * @param list double list
     * @return AbstractOption<Double>
     */
    static Option<Double> variance(List<Double> list) {
        return average(list).flatMap((Double averageValue) -> average(list.map((Double x) -> Math.pow(x - averageValue, 2))));
    }

    /**
     * 计算一个列表的最大值
     * @param <A> value type
     * @return max value
     */
    static <A extends Comparable> Function<List<A>, Option<A>> max() {
        return (List<A> x) -> x.isEmpty() ?
                Option.none() :
                Option.some(x.foldLeft(x.head(), (A a) -> (A b) -> a.compareTo(b) > 0 ? a : b));
    }

    /**
     * 将一个AbstractList<AbstractOption<A>>转化为AbstractOption<AbstractList<A>>
     * @param list AbstractList<AbstractOption<A>>
     * @param <A>  value type
     * @return AbstractOption<AbstractList < A>>
     */
    static <A> Option<List<A>> sequence(List<Option<A>> list) {
        return traverse(list, x -> x);
    }

    /**
     * 将一个AbstractList<A>转化为AbstractOption<AbstractList<B>>，并对value使用A到AbstractOption<B>的function
     * @param list AbstractList<A>
     * @param f    function A to AbstractOption<B>
     * @param <A>  type A
     * @param <B>  return value type B
     * @return AbstractOption<AbstractList < B>>
     */
    static <A, B> Option<List<B>> traverse(List<A> list, Function<A, Option<B>> f) {
        return list.foldRight(Option.some(List.list()),
                (A x) -> (Option<List<B>> y) ->
                        Options.map2(f.apply(x), y, (B a) -> (List<B> b) -> b.construct(a)));
    }
}
