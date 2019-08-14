package chapter5;

import chapter4.BaseTailCell;
import chapter6.AbstractLists;

import java.util.function.Function;

/**
 * @author Spencer
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractList<A> implements AbstractLists {
    @SuppressWarnings("rawTypes")
    private static final AbstractList NIL = new Nil();

    private AbstractList() {
    }

    public static <A, B> B foldRight(AbstractList<A> list, B init, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? init : f.apply(list.head()).apply(AbstractList.foldRight(list.tail(), init, f));
    }

    public static double sum(AbstractList<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x + y);
    }

    public static int sum(AbstractList<Integer> list, int init) {
        return foldRight(list, init, (Integer x) -> (Integer y) -> x + y);
    }

    public static int sumViaFoldLeft(AbstractList<Integer> list, int init) {
        return list.foldLeft(init, (Integer x) -> (Integer y) -> x + y);
    }

    public static double productViaFoldRight(AbstractList<Double> list, double init) {
        return list.foldLeft(init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> int lengthViaFoldLeft(AbstractList<A> list) {
        return list.foldLeft(0, (Integer x) -> (A useless) -> x + 1);
    }

    public static <A> AbstractList<A> reverseViaFoldLeft(AbstractList<A> list) {
        return list.foldLeft(AbstractList.list(), x -> x::construct);
    }

    public static <A, B> B foldRightViaFoldLeft(AbstractList<A> list, B identity, Function<A, Function<B, B>> f) {
        return list.reverse().foldLeft(identity, (B x) -> (A y) -> f.apply(y).apply(x));
    }

    public static double product(AbstractList<Double> list, double init) {
        return foldRight(list, init, (Double x) -> (Double y) -> x * y);
    }

    public static <A> AbstractList<A> concatViaFoldRight(AbstractList<A> list1, AbstractList<A> list2) {
        return AbstractList.foldRight(list1, list2, x -> y -> new Construct<>(x, y));
    }

    public static <A> AbstractList<A> flatten(AbstractList<AbstractList<A>> list) {
        return foldRight(list, AbstractList.list(), x -> y -> concatViaFoldRight(x, y));
    }

    public static AbstractList<Integer> triple(AbstractList<Integer> list) {
        return foldRight(list, AbstractList.list(), (Integer x) -> (AbstractList<Integer> y) -> y.construct(x * 3));
    }

    public static AbstractList<String> doubleToString(AbstractList<Double> list) {
        return AbstractList.foldRight(list, AbstractList.list(), (Double x) -> (AbstractList<String> y) -> y.construct(x.toString()));
    }

    public static <A> AbstractList<A> filterViaFlatMap(AbstractList<A> list, Function<A, Boolean> f) {
        return list.flatMap((A a) -> f.apply(a) ? AbstractList.list(a) : AbstractList.list());
    }

    public static <A> AbstractList<A> flattenViaFlatMap(AbstractList<AbstractList<A>> list) {
        return list.flatMap((AbstractList<A> x) -> x);
    }

    @SuppressWarnings("unchecked")
    public static <A> AbstractList<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> AbstractList<A> list(A... a) {
        AbstractList<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Construct<>(a[i], n);
        }
        return n;
    }

    public static <A> AbstractList<A> concat(AbstractList<A> list1, AbstractList<A> list2) {
        return list1.isEmpty() ? list2 : new Construct<A>(list1.head(), concat(list1.tail(), list2));
    }

    /**
     * 为列表添加一个头元素
     * @param a head
     * @return list
     */
    public AbstractList<A> construct(A a) {
        return new Construct<>(a, this);
    }

    /**
     * 获取列表头
     * @return A
     */
    public abstract A head();

    /**
     * 获取列表头后的剩余队列
     * @return 列表头后的剩余队列
     */
    public abstract AbstractList<A> tail();

    /**
     * 列表是否为空
     * @return true or false
     */
    public abstract boolean isEmpty();

    /**
     * 替换列表头
     * @param head 头
     * @return list
     */
    public abstract AbstractList<A> setHead(A head);

    /**
     * 删除列表前n个元素（假删除）
     * @param amount 数量
     * @return 去掉前n个元素后的队列
     */
    public abstract AbstractList<A> drop(int amount);

    /**
     * 只要条件f为真就删除list的head元素
     * @param f function条件
     * @return list
     */
    public abstract AbstractList<A> dropWhile(Function<A, Boolean> f);

    /**
     * 反转列表
     * @return list
     */
    public abstract AbstractList<A> reverse();

    /**
     * 删除列表最后的一个元素
     * @return list
     */
    public abstract AbstractList<A> dropLast();

    /**
     * 计算List的长度
     * @return length
     */
    public abstract int length();

    /**
     * 从右向左遍历
     * @param identity init
     * @param f        function
     * @param <B>      return type
     * @return result
     */
    public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);

    /**
     * 从右向左遍历
     * @param identity init
     * @param f        function
     * @param <B>      return type
     * @return result
     */
    public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);

    /**
     * do function to every element
     * @param f   function
     * @param <B> return list element type
     * @return return list
     */
    public abstract <B> AbstractList<B> map(Function<A, B> f);

    /**
     * 根据function过滤
     * @param f function
     * @return 过滤后list
     */
    public abstract AbstractList<A> filter(Function<A, Boolean> f);

    /**
     * 对列表List<A>里的每一个元素应用一个从A到List<B>的function并返回一个List<B>
     * @param f   function
     * @param <B> result list type
     * @return result list 原列表和新列表的合集
     */
    public abstract <B> AbstractList<B> flatMap(Function<A, AbstractList<B>> f);

    private static class Nil<A> extends AbstractList<A> {
        private Nil() {

        }

        @Override
        public String toString() {
            return "[Nil]";
        }

        @Override
        public A head() {
            throw new IllegalStateException("head called an empty list");
        }

        @Override
        public AbstractList<A> tail() {
            throw new IllegalStateException("tail called an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public AbstractList<A> setHead(A head) {
            throw new IllegalStateException("setHead called an empty list");
        }

        @Override
        public AbstractList<A> drop(int amount) {
            return this;
        }

        @Override
        public AbstractList<A> dropWhile(Function<A, Boolean> f) {
            return this;
        }

        @Override
        public AbstractList<A> reverse() {
            return this;
        }

        @Override
        public AbstractList<A> dropLast() {
            return this;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
            return identity;
        }

        @Override
        public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
            return identity;
        }

        @Override
        public <B> AbstractList<B> map(Function<A, B> f) {
            throw new IllegalStateException("map called an empty list");
        }

        @Override
        public AbstractList<A> filter(Function<A, Boolean> f) {
            throw new IllegalStateException("filter called an empty list");
        }

        @Override
        public <B> AbstractList<B> flatMap(Function<A, AbstractList<B>> f) {
            throw new IllegalStateException("flatMap called an empty list");
        }
    }

    private static class Construct<A> extends AbstractList<A> {
        private final A head;
        private final AbstractList<A> tail;

        private Construct(A head, AbstractList<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public A head() {
            return head;
        }

        @Override
        public AbstractList<A> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public AbstractList<A> setHead(A head) {
            return new Construct<>(head, tail());
        }

        @Override
        public AbstractList<A> drop(int amount) {
            return amount <= 0 ? this : dropRecursive(this, amount).value();
        }

        private BaseTailCell<AbstractList<A>> dropRecursive(AbstractList<A> list, int amount) {
            return amount <= 0 || list.isEmpty() ?
                    BaseTailCell.ofReturn(list) :
                    BaseTailCell.ofSuspend(() -> dropRecursive(list.tail(), amount - 1));
        }

        @Override
        public AbstractList<A> dropWhile(Function<A, Boolean> f) {
            return dropWhileRecursive(this, f).value();
        }

        private BaseTailCell<AbstractList<A>> dropWhileRecursive(AbstractList<A> list, Function<A, Boolean> f) {
            return !list.isEmpty() && f.apply(list.head()) ?
                    BaseTailCell.ofSuspend(() -> dropWhileRecursive(list.tail(), f)) :
                    BaseTailCell.ofReturn(list);
        }

        @Override
        public AbstractList<A> reverse() {
            return reverseRecursive(list(), this).value();
        }

        private BaseTailCell<AbstractList<A>> reverseRecursive(AbstractList<A> acc, AbstractList<A> list) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> reverseRecursive(new Construct<>(list.head(), acc), list.tail()));
        }

        @Override
        public AbstractList<A> dropLast() {
            return reverse().tail().reverse();
        }

        @Override
        public int length() {
            return foldRight(0, (A x) -> (Integer y) -> y + 1);
        }

        @Override
        public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
            return foldLeftRecursive(identity, this, f).value();
        }

        private <B> BaseTailCell<B> foldLeftRecursive(B acc, AbstractList<A> list, Function<B, Function<A, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldLeftRecursive(f.apply(acc).apply(list.head()), list.tail(), f));
        }

        @Override
        public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
            return foldRightRecursive(identity, this.reverse(), identity, f).value();
        }

        private <B> BaseTailCell<B> foldRightRecursive(B acc, AbstractList<A> list, B identity, Function<A, Function<B, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldRightRecursive(f.apply(list.head()).apply(acc), list.tail(), identity, f));
        }

        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).value());
        }

        private BaseTailCell<StringBuilder> toString(StringBuilder acc, AbstractList<A> list) {
            return list.isEmpty() ?
                    BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> toString(acc.append(list.head()).append(", "), list.tail()));
        }

        @Override
        public <B> AbstractList<B> map(Function<A, B> f) {
            return foldRight(list(), (A head) -> (AbstractList<B> y) -> new Construct<>(f.apply(head), y));
        }

        @Override
        public AbstractList<A> filter(Function<A, Boolean> f) {
            return foldRight(list(), (A x) -> y -> f.apply((A) x) ? new Construct<>(x, y) : y);
        }

        @Override
        public <B> AbstractList<B> flatMap(Function<A, AbstractList<B>> f) {
            return foldLeft(list(), (AbstractList<B> x) -> (A y) -> concat(x, f.apply(y)));
        }
    }
}
