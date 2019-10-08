package chapter8;

import chapter4.BaseTailCell;
import chapter7.Result;
import util.MyMap;
import util.Tuple;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @author Spencer
 */
@SuppressWarnings({"WeakerAccess", "unused", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
abstract class AdvancedLists<Z> extends BaseLists {
    /**
     * 检查一个列表是否是另一个列表的子列表
     * @param list list
     * @param sub  sub list
     * @param <T>  value type
     * @return true or false
     */
    public static <T> boolean hasSubsequence(AdvancedList<T> list, AdvancedList<T> sub) {
        return hasSubsequenceRecursive(list, sub).value();
    }

    private static <T> BaseTailCell<Boolean> hasSubsequenceRecursive(AdvancedList<T> list, AdvancedList<T> sub) {
        return list.isEmpty() ?
                BaseTailCell.ofReturn(sub.isEmpty()) :
                startWith(list, sub) ?
                        BaseTailCell.ofReturn(Boolean.TRUE) :
                        BaseTailCell.ofSuspend(() -> hasSubsequenceRecursive(list.tail(), sub));
    }

    /**
     * 子列表是否是列表的开头
     * @param list list
     * @param sub  sub list
     * @param <T>  list value type
     * @return true or false
     */
    private static <T> boolean startWith(AdvancedList<T> list, AdvancedList<T> sub) {
        return startWithRecursive(list, sub).value();
    }

    private static <T> BaseTailCell<Boolean> startWithRecursive(AdvancedList<T> list, AdvancedList<T> sub) {
        return sub.isEmpty() ?
                BaseTailCell.ofReturn(Boolean.TRUE) :
                list.isEmpty() ?
                        BaseTailCell.ofReturn(Boolean.FALSE) :
                        list.head().equals(sub.head()) ?
                                BaseTailCell.ofSuspend(() -> startWithRecursive(list.tail(), sub.tail())) :
                                BaseTailCell.ofReturn(Boolean.FALSE);
    }

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

    /**
     * 找到吸收元zero时就退出左折叠
     * @param identity init
     * @param zero     吸收元
     * @param f        function from B to A to B
     * @param <B>      result type
     * @return fold result
     */
    public abstract <B> B foldLeft(B identity, B zero, Function<B, Function<Z, B>> f);

    /**
     * 返回列表中索引对应的元素
     * @param index 索引
     * @return Result-wrapped value
     */
    public abstract Result<Z> getAt(int index);

    /**
     * 使用一个从A到B的函数将List转为Map
     * @param f   function from A to B
     * @param <B> target map value type
     * @return map
     */
    public abstract <B> MyMap<B, AdvancedList<Z>> groupBy(Function<Z, B> f);

    /**
     * 如果列表里至少有一个满足条件f的值就返回true
     * @param f function from A to Boolean
     * @return true or false
     */
    public abstract boolean exists(Function<Z, Boolean> f);

    /**
     * 如果列表中的所有元素都满足条件function就返回true
     * @param f function from A to Boolean
     * @return true or false
     */
    public abstract boolean forAll(Function<Z, Boolean> f);

    /**
     * 将一个列表拆分为多个子列表，该列表将被拆分为两部分，每个子列表递归拆分为两部分
     * @param depth 递归的步骤数
     * @return nesting List
     */
    public abstract AdvancedList<AdvancedList<Z>> divide(int depth);

    /**
     * 从index处将列表拆分成两部分
     * @param index split location
     * @return list with two lists
     */
    public abstract AdvancedList<AdvancedList<Z>> splitListAt(int index);

    /**
     * 并行化的左折叠
     * @param executorService thread pool
     * @param identity        init
     * @param f               function from A to B
     * @param merge           function for merge sub list
     * @param <B>             target type
     * @return Result-wrapped value
     */
    public abstract <B> Result<B> parallelFoldLeft(ExecutorService executorService, B identity,
                                                   Function<B, Function<Z, B>> f,
                                                   Function<B, Function<B, B>> merge);

    /**
     * 并行化的映射
     * @param executorService thread pool
     * @param f               function from A to B
     * @param <B>             target list value type
     * @return target list
     */
    public abstract <B> Result<AdvancedList<B>> parallelMap(ExecutorService executorService, Function<Z, B> f);
}
