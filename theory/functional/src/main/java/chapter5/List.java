package chapter5;

import chapter4.BaseTailCell;

import java.util.function.Function;

/**
 * @author Spencer
 */
@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "WeakerAccess"})
public abstract class List<A> implements OptionLists, BaseList<A> {
    @SuppressWarnings("rawTypes")
    private static final List NIL = new Nil();

    private List() {
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Construct<>(a[i], n);
        }
        return n;
    }

    public static <T> List<T> construct(T head, List<T> tail) {
        return new Construct<>(head, tail);
    }

    /**
     * 为列表添加一个头元素
     * @param a head
     * @return list
     */
    public List<A> construct(A a) {
        return new Construct<>(a, this);
    }

    private static class Nil<A> extends List<A> {
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
        public List<A> tail() {
            throw new IllegalStateException("tail called an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A head) {
            throw new IllegalStateException("setHead called an empty list");
        }

        @Override
        public List<A> drop(int amount) {
            return this;
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> f) {
            return this;
        }

        @Override
        public List<A> reverse() {
            return this;
        }

        @Override
        public List<A> dropLast() {
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
        public <B> List<B> map(Function<A, B> f) {
            throw new IllegalStateException("map called an empty list");
        }

        @Override
        public List<A> filter(Function<A, Boolean> f) {
            throw new IllegalStateException("filter called an empty list");
        }

        @Override
        public <B> List<B> flatMap(Function<A, List<B>> f) {
            throw new IllegalStateException("flatMap called an empty list");
        }
    }

    private static class Construct<A> extends List<A> {
        private final A head;
        private final List<A> tail;

        private Construct(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public A head() {
            return head;
        }

        @Override
        public List<A> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<A> setHead(A head) {
            return new Construct<>(head, tail());
        }

        @Override
        public List<A> drop(int amount) {
            return amount <= 0 ? this : dropRecursive(this, amount).value();
        }

        private BaseTailCell<List<A>> dropRecursive(List<A> list, int amount) {
            return amount <= 0 || list.isEmpty() ?
                    BaseTailCell.ofReturn(list) :
                    BaseTailCell.ofSuspend(() -> dropRecursive(list.tail(), amount - 1));
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> f) {
            return dropWhileRecursive(this, f).value();
        }

        private BaseTailCell<List<A>> dropWhileRecursive(List<A> list, Function<A, Boolean> f) {
            return !list.isEmpty() && f.apply(list.head()) ?
                    BaseTailCell.ofSuspend(() -> dropWhileRecursive(list.tail(), f)) :
                    BaseTailCell.ofReturn(list);
        }

        @Override
        public List<A> reverse() {
            return reverseRecursive(list(), this).value();
        }

        private BaseTailCell<List<A>> reverseRecursive(List<A> acc, List<A> list) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> reverseRecursive(new Construct<>(list.head(), acc), list.tail()));
        }

        @Override
        public List<A> dropLast() {
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

        private <B> BaseTailCell<B> foldLeftRecursive(B acc, List<A> list, Function<B, Function<A, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldLeftRecursive(f.apply(acc).apply(list.head()), list.tail(), f));
        }

        @Override
        public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
            return foldRightRecursive(identity, this.reverse(), identity, f).value();
        }

        @SuppressWarnings("unused")
        private <B> BaseTailCell<B> foldRightRecursive(B acc, List<A> list, B identity, Function<A, Function<B, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldRightRecursive(f.apply(list.head()).apply(acc), list.tail(), identity, f));
        }

        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).value());
        }

        private BaseTailCell<StringBuilder> toString(StringBuilder acc, List<A> list) {
            return list.isEmpty() ?
                    BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> toString(acc.append(list.head()).append(", "), list.tail()));
        }

        @Override
        public <B> List<B> map(Function<A, B> f) {
            return foldRight(list(), (A head) -> (List<B> y) -> new Construct<>(f.apply(head), y));
        }

        @Override
        public List<A> filter(Function<A, Boolean> f) {
            return foldRight(list(), (A x) -> y -> f.apply(x) ? new Construct<>(x, y) : y);
        }

        @Override
        public <B> List<B> flatMap(Function<A, List<B>> f) {
            return foldLeft(list(), (List<B> x) -> (A y) -> BaseLists.concat(x, f.apply(y)));
        }
    }
}
