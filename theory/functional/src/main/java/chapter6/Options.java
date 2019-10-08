package chapter6;

import java.util.function.Function;

/**
 * @author Spencer
 */
public interface Options {
    /**
     * 将一个从A到B的函数function包装成从AbstractOption<A>到AbstractOption<B>的函数
     * @param f   function A to B
     * @param <A> type A
     * @param <B> type B
     * @return function AbstractOption<A> to AbstractOption<B>
     */
    static <A, B> Function<Option<A>, Option<B>> lift(Function<A, B> f) {
        return (Option<A> x) -> {
            try {
                return x.map(f);
            } catch (Exception e) {
                e.printStackTrace();
                return Option.none();
            }
        };
    }

    /**
     * 将一个从A到B的函数function包装成从A到AbstractOption<B>的函数
     * @param f   function A to B
     * @param <A> type A
     * @param <B> type B
     * @return function A to AbstractOption<B>
     */
    static <A, B> Function<A, Option<B>> halfLift(Function<A, B> f) {
        return (A x) -> {
            try {
                return Option.some(x).map(f);
            } catch (Exception e) {
                e.printStackTrace();
                return Option.none();
            }
        };
    }

    /**
     * 接收两个AbstractOption A和B 以及一个从(A,B)到C的柯里化形式的函数function，返回一个AbstractOption<C>
     * @param a   AbstractOption<A>
     * @param b   AbstractOption<B>
     * @param f   function Function<A, Function<B, C>>
     * @param <A> type A
     * @param <B> type B
     * @param <C> type C
     * @return AbstractOption<C>
     */
    static <A, B, C> Option<C> map2(Option<A> a, Option<B> b, Function<A, Function<B, C>> f) {
        return a.flatMap((A ax) -> b.map((B bx) -> f.apply(ax).apply(bx)));
    }

    /**
     * 接收三个AbstractOption A B C 以及一个从(A,B)到C再到D的柯里化形式的函数function，返回一个AbstractOption<D>
     * @param a   AbstractOption<A>
     * @param b   AbstractOption<B>
     * @param c   AbstractOption<C>
     * @param f   function Function<A, Function<B, Function<C, D>>>
     * @param <A> type A
     * @param <B> type B
     * @param <C> type C
     * @param <D> type D
     * @return AbstractOption<D>
     */
    static <A, B, C, D> Option<D> map3(Option<A> a, Option<B> b, Option<C> c,
                                       Function<A, Function<B, Function<C, D>>> f) {
        return a.flatMap((A ax) -> b.flatMap((B bx) -> c.map((C cx) -> f.apply(ax).apply(bx).apply(cx))));
    }
}
