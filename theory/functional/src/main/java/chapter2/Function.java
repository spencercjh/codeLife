package chapter2;

/**
 * @author Spencer
 * Page 32
 */
interface Function<T, U> {
    static Function<Integer, Integer> composeWithLambda(final Function<Integer, Integer> function1, final Function<Integer, Integer> function2) {
        return x -> function1.apply(function2.apply(x));
    }

    static Function<Integer, Integer> composeWithAnonymousClass(final Function<Integer, Integer> function1, final Function<Integer, Integer> function2) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return function1.apply(function2.apply(arg));
            }
        };
    }

    static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherComposeWithLambda() {
        return (Function<U, V> uvFunction) -> (Function<T, U> tuFunction) -> (T t) -> uvFunction.apply(tuFunction.apply(t));
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThenWithLambda() {
        return (Function<T, U> tuFunction) -> (Function<U, V> uvFunction) -> (T t) -> uvFunction.apply(tuFunction.apply(t));
    }

    static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherComposeWithAnonymousClass() {
        return new Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>>() {
            @Override
            public Function<Function<T, U>, Function<T, V>> apply(Function<U, V> uvFunction) {
                return new Function<Function<T, U>, Function<T, V>>() {
                    @Override
                    public Function<T, V> apply(Function<T, U> tuFunction) {
                        return new Function<T, V>() {
                            @Override
                            public V apply(T t) {
                                return uvFunction.apply(tuFunction.apply(t));
                            }
                        };
                    }
                };
            }
        };
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThenWithAnonymousClass() {
        return new Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>>() {
            @Override
            public Function<Function<U, V>, Function<T, V>> apply(Function<T, U> tuFunction) {
                return new Function<Function<U, V>, Function<T, V>>() {
                    @Override
                    public Function<T, V> apply(Function<U, V> uvFunction) {
                        return new Function<T, V>() {
                            @Override
                            public V apply(T t) {
                                return uvFunction.apply(tuFunction.apply(t));
                            }
                        };
                    }
                };
            }
        };
    }

    @SuppressWarnings({"AliAccessStaticViaInstance", "AlibabaLowerCamelCaseVariableNaming"})
    static <A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> function) {
        return function.apply(a);
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> function) {
        return (A a) -> function.apply(a).apply(b);
    }

    /**
     * 柯里化练习
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @return
     */
    static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> curryExercise() {
        return (A a) -> (B b) -> (C c) -> (D d) -> a.toString() + b.toString() + c.toString() + d.toString();
    }

    static <T, U, V> Function<U, Function<T, V>> reverseArgs(Function<T, Function<U, V>> function) {
        return u -> t -> function.apply(t).apply(u);
    }

    /**
     * 一个操作
     * @param arg
     * @return
     */
    U apply(T arg);
}
