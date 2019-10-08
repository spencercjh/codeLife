package util;

import java.util.HashMap;

public class MyMap<K, V> extends HashMap<K, V> {
    private static final MyMap EMPTY_MAP = new MyMap<>();

    @SuppressWarnings("unchecked")
    public static <K, V> MyMap<K, V> emptyMap() {
        return (MyMap<K, V>) EMPTY_MAP;
    }
}
