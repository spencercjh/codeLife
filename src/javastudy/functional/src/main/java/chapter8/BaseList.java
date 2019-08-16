package chapter8;


import java.util.function.Function;

/**
 * @author Spencer
 */
interface BaseList<A> {
    /**
     * 获取列表头
     * @return A
     */
    A head();

    /**
     * 获取列表头后的剩余队列
     * @return 列表头后的剩余队列
     */
    AdvancedList<A> tail();

    /**
     * 列表是否为空
     * @return true or false
     */
    boolean isEmpty();

    /**
     * 替换列表头
     * @param head 头
     * @return list
     */
    AdvancedList<A> setHead(A head);

    /**
     * 删除列表前n个元素（假删除）
     * @param amount 数量
     * @return 去掉前n个元素后的队列
     */
    AdvancedList<A> drop(int amount);

    /**
     * 只要条件f为真就删除list的head元素
     * @param f function条件
     * @return list
     */
    AdvancedList<A> dropWhile(Function<A, Boolean> f);

    /**
     * 反转列表
     * @return list
     */
    AdvancedList<A> reverse();

    /**
     * 删除列表最后的一个元素
     * @return list
     */
    AdvancedList<A> dropLast();

    /**
     * 计算List的长度
     * @return length
     */
    int length();

    /**
     * 从右向左遍历
     * @param identity init
     * @param f        function
     * @param <B>      return type
     * @return result
     */
    <B> B foldLeft(B identity, Function<B, Function<A, B>> f);

    /**
     * 从右向左遍历
     * @param identity init
     * @param f        function
     * @param <B>      return type
     * @return result
     */
    <B> B foldRight(B identity, Function<A, Function<B, B>> f);

    /**
     * do function to every element
     * @param f   function
     * @param <B> return list element type
     * @return return list
     */
    <B> AdvancedList<B> map(Function<A, B> f);

    /**
     * 根据function过滤
     * @param f function
     * @return 过滤后list
     */
    AdvancedList<A> filter(Function<A, Boolean> f);

    /**
     * 对列表List<A>里的每一个元素应用一个从A到List<B>的function并返回一个List<B>
     * @param f   function
     * @param <B> result list type
     * @return result list 原列表和新列表的合集
     */
    <B> AdvancedList<B> flatMap(Function<A, AdvancedList<B>> f);
}
