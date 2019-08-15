package chapter8;

import chapter4.BaseTailCell;
import chapter6.OptionLists;
import chapter7.Result;

import java.util.function.Function;

/**
 * @author Spencer
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AdvancedList<A> extends AdvancedLists<A> implements OptionLists, BaseList<A> {
    @SuppressWarnings("rawTypes")
    private static final AdvancedList NIL = new Nil();

    private AdvancedList() {
    }

    @SuppressWarnings("unchecked")
    public static <A> AdvancedList<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> AdvancedList<A> list(A... a) {
        AdvancedList<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Construct<>(a[i], n);
        }
        return n;
    }

    /**
     * 为列表添加一个头元素
     * @param a    head
     * @param tail tail
     * @param <B>  type
     * @return list
     */
    public static <B> AdvancedList<B> construct(B a, AdvancedList<B> tail) {
        return new Construct<>(a, tail);
    }

    /**
     * 为列表添加一个头元素
     * @param a head
     * @return list
     */
    public AdvancedList<A> construct(A a) {
        return new Construct<>(a, this);
    }

    @Override
    public Result<A> lastOption() {
        return foldLeft(Result.empty(), x -> Result::success);
    }

    private static class Nil<A> extends AdvancedList<A> {
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
        public AdvancedList<A> tail() {
            throw new IllegalStateException("tail called an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public AdvancedList<A> setHead(A head) {
            throw new IllegalStateException("setHead called an empty list");
        }

        @Override
        public AdvancedList<A> drop(int amount) {
            return this;
        }

        @Override
        public AdvancedList<A> dropWhile(Function<A, Boolean> f) {
            return this;
        }

        @Override
        public AdvancedList<A> reverse() {
            return this;
        }

        @Override
        public AdvancedList<A> dropLast() {
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
        public <B> AdvancedList<B> map(Function<A, B> f) {
            throw new IllegalStateException("map called an empty list");
        }

        @Override
        public AdvancedList<A> filter(Function<A, Boolean> f) {
            throw new IllegalStateException("filter called an empty list");
        }

        @Override
        public <B> AdvancedList<B> flatMap(Function<A, AdvancedList<B>> f) {
            throw new IllegalStateException("flatMap called an empty list");
        }

        @Override
        public int lengthMemorized() {
            return 0;
        }

        @Override
        public Result<A> headOption() {
            return Result.empty();
        }
    }

    private static class Construct<A> extends AdvancedList<A> {
        private final int length;
        private final A head;
        private final AdvancedList<A> tail;

        private Construct(A head, AdvancedList<A> tail) {
            this.head = head;
            this.tail = tail;
            this.length = tail.length() + 1;
        }

        @Override
        public A head() {
            return head;
        }

        @Override
        public AdvancedList<A> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public AdvancedList<A> setHead(A head) {
            return new Construct<>(head, tail());
        }

        @Override
        public AdvancedList<A> drop(int amount) {
            return amount <= 0 ? this : dropRecursive(this, amount).value();
        }

        private BaseTailCell<AdvancedList<A>> dropRecursive(AdvancedList<A> list, int amount) {
            return amount <= 0 || list.isEmpty() ?
                    BaseTailCell.ofReturn(list) :
                    BaseTailCell.ofSuspend(() -> dropRecursive(list.tail(), amount - 1));
        }

        @Override
        public AdvancedList<A> dropWhile(Function<A, Boolean> f) {
            return dropWhileRecursive(this, f).value();
        }

        private BaseTailCell<AdvancedList<A>> dropWhileRecursive(AdvancedList<A> list, Function<A, Boolean> f) {
            return !list.isEmpty() && f.apply(list.head()) ?
                    BaseTailCell.ofSuspend(() -> dropWhileRecursive(list.tail(), f)) :
                    BaseTailCell.ofReturn(list);
        }

        @Override
        public AdvancedList<A> reverse() {
            return reverseRecursive(list(), this).value();
        }

        private BaseTailCell<AdvancedList<A>> reverseRecursive(AdvancedList<A> acc, AdvancedList<A> list) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> reverseRecursive(new Construct<>(list.head(), acc), list.tail()));
        }

        @Override
        public AdvancedList<A> dropLast() {
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

        private <B> BaseTailCell<B> foldLeftRecursive(B acc, AdvancedList<A> list, Function<B, Function<A, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldLeftRecursive(f.apply(acc).apply(list.head()), list.tail(), f));
        }

        @Override
        public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
            return foldRightRecursive(identity, this.reverse(), identity, f).value();
        }

        private <B> BaseTailCell<B> foldRightRecursive(B acc, AdvancedList<A> list, B identity, Function<A, Function<B, B>> f) {
            return list.isEmpty() ? BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> foldRightRecursive(f.apply(list.head()).apply(acc), list.tail(), identity, f));
        }

        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).value());
        }

        private BaseTailCell<StringBuilder> toString(StringBuilder acc, AdvancedList<A> list) {
            return list.isEmpty() ?
                    BaseTailCell.ofReturn(acc) :
                    BaseTailCell.ofSuspend(() -> toString(acc.append(list.head()).append(", "), list.tail()));
        }

        @Override
        public <B> AdvancedList<B> map(Function<A, B> f) {
            return foldRight(list(), (A head) -> (AdvancedList<B> y) -> new Construct<>(f.apply(head), y));
        }

        @Override
        public AdvancedList<A> filter(Function<A, Boolean> f) {
            return foldRight(list(), (A x) -> y -> f.apply(x) ? new Construct<>(x, y) : y);
        }

        @Override
        public <B> AdvancedList<B> flatMap(Function<A, AdvancedList<B>> f) {
            return foldLeft(list(), (AdvancedList<B> x) -> (A y) -> concat(x, f.apply(y)));
        }

        @Override
        public int lengthMemorized() {
            return length;
        }

        @Override
        public Result<A> headOption() {
            return Result.success(head);
        }
    }
}
