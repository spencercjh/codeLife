package exam.weipinhui2019;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : SpencerCJH
 * @date : 2019/9/15 19:03
 */
public class Test2 {
    public static void main(String[] args) {
        MyLFU<Integer, Integer> cache = new MyLFU<>(3);
        cache.put(2, 2);
        cache.put(1, 1);

        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));

        cache.put(3, 3);
        cache.put(4, 4);

        System.out.println(Optional.ofNullable(cache.get(3)).orElse(-1));
        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        System.out.println(cache.get(4));

//        System.out.println("----------------------------");
//        cache.cache.forEach((key, value) -> System.out.printf("key:%s\tvalue:%s\n", key, value));
//        cache.count.forEach((key, hit) -> System.out.printf("key:%s\thitCount:%s\tlastHitTime:%s\n", hit.key, hit.hitCount, hit.lastTime));
    }

    public static class MyLFU<K, V> {
        private final int capacity;

        private Map<K, V> cache;

        private Map<K, Hit> count;


        MyLFU(int capacity) {
            this.capacity = capacity;
        }

        /**
         * 懒加载初始化两个map。容量设为2倍的实际缓存容量，算上负载因子0.75后，能到达1.5倍的缓存容量，完全阻止耗时的扩容的发生
         *
         * @param key   键
         * @param value 值
         * @throws IllegalStateException 规定不能有空键或值
         */
        public void put(K key, V value) throws IllegalStateException {
            if (key == null || value == null) {
                throw new IllegalStateException("not null key or value");
            }
            if (cache == null || count == null) {
                cache = new HashMap<>(2 * capacity);
                count = new HashMap<>(2 * capacity);
            }
            V v = cache.get(key);
            if (v == null) {
                if (cache.size() == capacity) {
                    removeCache();
                }
                count.put(key, new Hit(key, 0, System.nanoTime()));
            } else {
                addHitCount(key);
            }
            cache.put(key, value);
        }

        /**
         * @param key 键
         * @return 值
         * @throws IllegalStateException 规定不能有空键或值
         */
        public V get(K key) throws IllegalStateException {
            if (key == null) {
                throw new IllegalStateException("not null key or value");
            }
            V value = cache.get(key);
            if (value != null) {
                addHitCount(key);
                return value;
            }
            return null;
        }

        private void removeCache() {
            Hit hit = Collections.min(count.values());
            cache.remove(hit.key);
            count.remove(hit.key);
        }

        private void addHitCount(K key) {
            Hit hit = count.get(key);
            hit.hitCount = hit.hitCount + 1;
            hit.lastTime = System.nanoTime();
        }

        /**
         * 记录命中次数与最近使用时间
         */
        class Hit implements Comparable<Hit> {
            private K key;
            private int hitCount;
            private long lastTime;

            private Hit(K key, int hitCount, long lastTime) {
                this.key = key;
                this.hitCount = hitCount;
                this.lastTime = lastTime;
            }

            @Override
            public int compareTo(Hit o) {
                int compareHit = Integer.compare(this.hitCount, o.hitCount);
                return compareHit == 0 ? Long.compare(this.lastTime, o.lastTime) : compareHit;
            }
        }
    }
}
