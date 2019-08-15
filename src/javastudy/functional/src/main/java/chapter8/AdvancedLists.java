package chapter8;

import chapter4.BaseTailCell;
import chapter7.Result;

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

    public static <A, B, C> AdvancedList<C> zipWith(AdvancedList<A> list1, AdvancedList<B> list2, Function<A, Function<B, C>> f) {
        return zipWithRecursive(AdvancedList.list(), list1, list2, f).value().reverse();
    }

    private static <C, A, B> BaseTailCell<AdvancedList<C>> zipWithRecursive(AdvancedList<C> acc, AdvancedList<A> list1, AdvancedList<B> list2, Function<A, Function<B, C>> f) {
        return list1.isEmpty() || list2.isEmpty() ?
                BaseTailCell.ofReturn(acc) :
                BaseTailCell.ofSuspend(() ->
                        zipWithRecursive(AdvancedList.construct(f.apply(list1.head()).apply(list2.head()), acc),
                                list1.tail(), list2.tail(), f));
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
