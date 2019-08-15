package chapter8;

import chapter4.BaseTailCell;
import chapter7.Result;
import util.Tuple;

import java.util.function.Function;

/**
 * @author Spencer
 */
public abstract class AdvancedLists<Z> extends BaseLists {

    /**
     * 包含原始列表中的所有Success，忽略Failure和Empty
     * @param list List with Result-wrapped element
     * @param <T>  element type
     * @return pure list
     */
    public static <T> AdvancedList<T> flattenResult(AdvancedList<Result<T>> list) {
        return BaseLists.flatten(list.foldRight(AdvancedList.list(),
                (Result<T> x) ->
                        (AdvancedList<AdvancedList<T>> y) ->
                                y.construct(x.map(AdvancedList::list)
                                        .getOrElse(AdvancedList.list()))));
    }

    /**
     * 如果原始列表中的所有值都是Success则返回Success with List，否则就返回Failure with List
     * @param list list with Result-wrapped element
     * @param <T>  element type
     * @return Result-wrapped list
     */
    public static <T> Result<AdvancedList<T>> sequence(AdvancedList<Result<T>> list) {
        return list.foldRight(Result.success(AdvancedList.list()),
                (Result<T> x) -> (Result<AdvancedList<T>> y) ->
                        Result.map2(x, y, (T a) -> (AdvancedList<T> b) -> b.construct(a)));
    }

    /**
     * 如果原始列表中的所有值都是Success则返回Success with List，否则就返回Failure with List（过滤Empty）
     * @param list list with Result-wrapped element
     * @param <T>  element type
     * @return Result-wrapped list
     */
    public static <T> Result<AdvancedList<T>> sequenceFilterEmpty(AdvancedList<Result<T>> list) {
        return list.filter((Result<T> a) -> !a.isEmpty())
                .foldRight(Result.success(AdvancedList.list()),
                        (Result<T> x) -> (Result<AdvancedList<T>> y) ->
                                Result.map2(x, y, (T a) -> (AdvancedList<T> b) -> b.construct(a)));
    }

    /**
     * 遍历列表，同时应用从U到Result with V的函数并生成Result with List V
     * @param list list with U element
     * @param f    function from U to Result-wrapped V
     * @param <U>  origin type
     * @param <V>  target type
     * @return Result-wrapped list with V element
     */
    public static <U, V> Result<AdvancedList<V>> traverse(AdvancedList<U> list, Function<U, Result<V>> f) {
        return list.foldRight(Result.success(AdvancedList.list()),
                (U x) -> (Result<AdvancedList<V>> y) ->
                        Result.map2(f.apply(x), y, (V a) -> (AdvancedList<V> b) -> b.construct(a)));
    }

    /**
     * 如果原始列表中的所有值都是Success则返回Success with List，否则就返回Failure with List。
     * 这个方法使用{@link #traverse(AdvancedList, Function)}}实现
     * @param list list with Result-wrapped element
     * @param <T>  element type
     * @return Result-wrapped list
     */
    public static <T> Result<AdvancedList<T>> sequenceWithTraverse(AdvancedList<Result<T>> list) {
        return traverse(list, (Result<T> x) -> x);
    }

    /**
     * 如果原始列表中的所有值都是Success则返回Success with List，否则就返回Failure with List（过滤Empty）
     * 这个方法使用{@link #traverse(AdvancedList, Function)}}实现
     * @param list list with Result-wrapped element
     * @param <T>  element type
     * @return Result-wrapped list
     */
    public static <T> Result<AdvancedList<T>> sequenceFilterEmptyWithTraverse(AdvancedList<Result<T>> list) {
        return traverse(list.filter((Result<T> x) -> !x.isEmpty()), (Result<T> x) -> x);
    }

    /**
     * 给定一个函数，复合两个不同类型的列表元素产生一个新的列表
     * @param list1 list1
     * @param list2 list2
     * @param f     function from A to B to C
     * @param <A>   list1 value type
     * @param <B>   list2 value type
     * @param <C>   target list value type
     * @return new list with C type value
     */
    public static <A, B, C> AdvancedList<C> zipWith(AdvancedList<A> list1,
                                                    AdvancedList<B> list2,
                                                    Function<A, Function<B, C>> f) {
        return zipWithRecursive(AdvancedList.list(), list1, list2, f).value().reverse();
    }

    private static <A, B, C> BaseTailCell<AdvancedList<C>> zipWithRecursive(AdvancedList<C> acc,
                                                                            AdvancedList<A> list1,
                                                                            AdvancedList<B> list2,
                                                                            Function<A, Function<B, C>> f) {
        return list1.isEmpty() || list2.isEmpty() ?
                BaseTailCell.ofReturn(acc) :
                BaseTailCell.ofSuspend(() ->
                        zipWithRecursive(AdvancedList.construct(f.apply(list1.head()).apply(list2.head()), acc),
                                list1.tail(), list2.tail(), f));
    }

    /**
     * 生成一个包含两个列表中获取的所有可能组合
     * @param list1 list1
     * @param list2 list2
     * @param f     function from A to B to C
     * @param <A>   list1 value type
     * @param <B>   list2 value type
     * @param <C>   target list value type
     * @return new list
     */
    public static <A, B, C> AdvancedList<C> product(AdvancedList<A> list1, AdvancedList<B> list2, Function<A, Function<B, C>> f) {
        return list1.flatMap((A a) -> list2.map((B b) -> f.apply(a).apply(b)));
    }

    /**
     * 把一个元组列表转换为列表元组
     * @param list 元组列表
     * @param <A>  tuple first type
     * @param <B>  tuple second type
     * @return tuple with two lists
     */
    public static <A, B> Tuple<AdvancedList<A>, AdvancedList<B>> unzip(AdvancedList<Tuple<A, B>> list) {
        return list.foldRight(new Tuple<>(AdvancedList.list(), AdvancedList.list()),
                (Tuple<A, B> tuple) -> (Tuple<AdvancedList<A>, AdvancedList<B>> t) ->
                        new Tuple<>(t.first.construct(tuple.first), t.second.construct(tuple.second)));
    }

    /**
     * 记忆化版的length方法
     * @return list length
     */
    public abstract int lengthMemorized();

    /**
     * 安全访问list的head
     * @return Result with head value
     */
    public abstract Result<Z> headOption();

    /**
     * 从左向右遍历获取列表最后一个元素的Result包装
     * @return Result with last value
     */
    public abstract Result<Z> lastOption();
}
