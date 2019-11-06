package top.spencercjh.exercise.traverse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Spencer
 */
public class Difference {
    private static List<String> myFilter(List<String> list, Function<String, Boolean> predicate) {
        final List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (predicate.apply(s)) {
                newList.add(s);
            }
        }
        return newList;
    }

    public static void main(String[] args) {
        List<String> list = List.of("Alice", "Peter", "1", "2", "3");
        Function<String, Boolean> lambda = x -> x.length() > 1;
        System.out.println(myFilter(list, lambda));
        System.out.println(myFilter(list, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                return s.length() > 1;
            }
        }));
    }
}
