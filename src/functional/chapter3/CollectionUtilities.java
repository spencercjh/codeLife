package functional.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Spencer
 * page 81 82 83 85 86 87 89 94
 */
public class CollectionUtilities {
    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T singleton) {
        return Collections.singletonList(singleton);
    }

    public static <T> List<T> list(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    @SafeVarargs
    public static <T> List<T> list(T... list) {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(list, list.length)));
    }

    public static <T> T getHead(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("head of empty list");
        }
        return list.get(0);
    }

    public static <T> List<T> getTail(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("tail of empty list");
        }
        List<T> workList = copy(list);
        workList.remove(0);
        return Collections.unmodifiableList(workList);
    }

    private static <T> List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    public static <T> List<T> append(List<T> list, T element) {
        List<T> workList = copy(list);
        workList.add(element);
        return Collections.unmodifiableList(workList);
    }

    public static Integer fold(List<Integer> list, Integer initialValue, Function<Integer, Function<Integer, Integer>> f) {
        int result = initialValue;
        for (Integer n : list) {
            result = f.apply(result).apply(n);
        }
        return result;
    }

    public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T, U>> f) {
        U result = identity;
        for (T t : list) {
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> f) {
        U result = identity;
        for (int i = list.size() - 1; i >= 0; i--) {
            result = f.apply(list.get(i)).apply(result);
        }
        return result;
    }

    public static <T, U> U recursiveFoldRight(List<T> list, U identity, Function<T, Function<U, U>> f) {
        return list.isEmpty() ? identity : f.apply(getHead(list)).apply(recursiveFoldRight(getTail(list), identity, f));
    }

    private static <T> List<T> prepend(T element, List<T> list) {
        return foldLeft(list, list(element), (List<T> a) -> (T b) -> append(a, b));
    }

    public static <T> List<T> reverse(List<T> list) {
        return foldLeft(list, list(), (List<T> x) -> (T y) -> prepend(y, x));
    }

    public static List<Integer> rangeWithFor(int start, int end) {
        List<Integer> result = new ArrayList<>(end - start + 1);
        int temp = start;
        while (temp < end) {
            result = append(result, temp++);
        }
        return result;
    }

    public static <T> List<T> unfold(T start, Function<T, T> f, Function<T, Boolean> p) {
        List<T> result = new ArrayList<>();
        T temp = start;
        while (p.apply(temp)) {
            result = append(result, temp);
            temp = f.apply(temp);
        }
        return result;
    }

    public static List<Integer> range(int start, int end) {
        return unfold(start, x -> x + 1, x -> x < end);
    }

    public static List<Integer> rangeWithPrepend(int start, int end) {
        return end <= start ? list() : prepend(start, rangeWithFor(start + 1, end));
    }

    public static <T, U> List<U> mapViaFoldLeft(List<T> list, Function<T, U> f) {
        return foldLeft(list, list(), (List<U> x) -> (T y) -> append(x, f.apply(y)));
    }

    public static <T, U> List<U> mapViaFoldRight(List<T> list, Function<T, U> f) {
        return foldRight(list, list(), (T x) -> (List<U> y) -> prepend(f.apply(x), y));
    }
}
