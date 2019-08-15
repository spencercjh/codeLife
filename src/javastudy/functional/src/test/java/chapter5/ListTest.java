package chapter5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AbstractList的测试类
 */
class ListTest {
    private final List<Integer> nullList = List.list();
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
    private final Integer[] numbers2 = new Integer[]{6, 7, 8, 9, 10};
    private final Double[] decimals = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};

    @Test
    void staticFoldRight() {
        assertEquals(List.list(numbers).toString(),
                BaseLists.foldRight(List.list(numbers), List.list(),
                        (Integer x) -> (List<Object> y) -> y.construct(x))
                        .toString());
    }

    @Test
    void sum() {
        List<Integer> list = List.list(numbers);
        assertEquals(15, BaseLists.sum(list, 0));
    }

    @Test
    void sumViaFoldLeft() {
        List<Integer> list = List.list(numbers);
        assertEquals(15, BaseLists.sumViaFoldLeft(list, 0));
    }

    @Test
    void productViaFoldRight() {
        Double[] numbers = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        List<Double> list = List.list(numbers);
        assertEquals(120.0, BaseLists.productViaFoldRight(list, 1));
    }

    @Test
    void lengthViaFoldLeft() {
        List<Integer> list = List.list(numbers);
        assertEquals(numbers.length, BaseLists.lengthViaFoldLeft(list));
    }

    @Test
    void reverseViaFoldLeft() {
        List<Integer> list = List.list(numbers);
        assertEquals(list.reverse().toString(), BaseLists.reverseViaFoldLeft(list).toString());
    }

    @Test
    void foldRightViaFoldLeft() {
        assertEquals(List.list(numbers).toString(),
                BaseLists.foldRightViaFoldLeft(List.list(numbers), List.list(),
                        (Integer x) -> (List<Object> y) -> y.construct(x))
                        .toString());
    }

    @Test
    void product() {
        List<Double> list = List.list(decimals);
        int sum = 1;
        for (Integer number : numbers) {
            sum *= number;
        }
        assertEquals(sum, BaseLists.product(list, 1));
    }

    @Test
    void concatViaFoldRight() {
        List<Integer> concatList = BaseLists.concatViaFoldRight(List.list(numbers),
                List.list(numbers2));
        for (int i = 0; i < numbers.length + numbers2.length; i++, concatList = concatList.tail()) {
            if (i < numbers.length) {
                assertEquals(concatList.head(), numbers[i % 5]);
            } else {
                assertEquals(concatList.head(), numbers2[i % 5]);
            }
        }
    }

    @Test
    void flatten() {
        List<List<Integer>> lists = BaseLists.foldRight(List.list(numbers),
                List.list(), (Integer useless) -> (List<List<Integer>> y) -> y.construct(List.list(numbers)));
        assertEquals(numbers.length * numbers.length, BaseLists.flatten(lists).length());
    }

    @Test
    void triple() {
        List<Integer> list = BaseLists.triple(List.list(numbers));
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            assertEquals(numbers[i] * 3, list.head());
        }
    }

    @Test
    void doubleToString() {
        assertEquals(List.list(decimals).toString(),
                BaseLists.doubleToString(List.list(decimals)).toString());
    }

    @Test
    void list() {
        List<Integer> list = List.list(numbers);
        int sum = 0;
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            sum++;
        }
        assertEquals(sum, numbers.length);
    }

    @Test
    void emptyList() {
        assertNotNull(List.list());
    }

    @Test
    void setHead() {
        List<Integer> list = List.list(1);
        list = list.setHead(0);
        assertEquals(0, list.head());
        assertThrows(IllegalStateException.class, () -> nullList.setHead(1));
    }

    @Test
    void concat() {
        List<Integer> concatList = BaseLists.concat(List.list(numbers), List.list(numbers2));
        for (int i = 0; i < numbers.length + numbers2.length; i++, concatList = concatList.tail()) {
            if (i < numbers.length) {
                assertEquals(concatList.head(), numbers[i % 5]);
            } else {
                assertEquals(concatList.head(), numbers2[i % 5]);
            }
        }
    }

    @Test
    void drop() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        List<Integer> list = List.list(numbers);
        list = list.drop(5);
        assertTrue(list.isEmpty());
        assertEquals(nullList, nullList.drop(1));
    }

    @Test
    void dropWhile() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        List<Integer> list = List.list(numbers);
        list = list.dropWhile((x) -> x != 0);
        assertTrue(list.isEmpty());
        assertEquals(nullList, nullList.dropWhile(x -> x != 0));
    }

    @Test
    void dropLast() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        List<Integer> list = List.list(numbers);
        list = list.dropLast().dropLast().dropLast().dropLast();
        assertEquals(1, list.head());
        assertEquals(nullList, nullList.dropLast());
    }


    @Test
    void length() {
        List<Integer> list = List.list(numbers);
        assertEquals(numbers.length, list.length());
        assertEquals(0, nullList.length());
    }


    @Test
    void foldLeft() {
        List<Integer> list = List.list(numbers);
        assertEquals(list.toString(),
                list.foldLeft(List.list(), (List<Object> x) -> x::construct).reverse().toString());
        assertEquals(0, nullList.foldLeft(0, null));
    }

    @Test
    void foldRight() {
        List<Integer> list = List.list(numbers);
        assertEquals(list.toString(),
                list.foldRight(List.list(), x -> y -> y.construct(x)).toString());
        assertEquals(0, nullList.foldRight(0, null));
    }

    @Test
    void head() {
        assertThrows(IllegalStateException.class, List.list()::head);
        assertNotNull(List.list(0).head());
    }

    @Test
    void tail() {
        assertThrows(IllegalStateException.class, List.list()::tail);
        assertEquals(List.list(1).tail(), List.list());
    }

    @Test
    void isEmpty() {
        assertTrue(List.list()::isEmpty);
        assertFalse(List.list(0)::isEmpty);
    }

    @Test
    void reverse() {
        assertEquals(nullList, nullList.reverse());
        final List<Integer> list = List.list(numbers);
        assertEquals(list.toString(), list.reverse().reverse().toString());
    }

    @Test
    void testToString() {
        assertNotNull(nullList.toString());
        assertNotNull(List.list(numbers).toString());
    }

    @Test
    void construct() {
        assertEquals(List.list(numbers).toString(), List.list()
                .construct(5)
                .construct(4)
                .construct(3)
                .construct(2)
                .construct(1)
                .toString());
    }

    @Test
    void map() {
        List<Integer> list = List.list(numbers).map(x -> x + 1);
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            assertEquals(numbers[i] + 1, list.head());
        }
        assertThrows(IllegalStateException.class, () -> List.<Integer>list().map(x -> x + 1));
    }

    @Test
    void filter() {
        List<Integer> list = List.list(numbers).filter(x -> x % 2 == 0);
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                assertEquals(number, list.head());
                list = list.tail();
            }
        }
        assertThrows(IllegalStateException.class, () -> List.<Integer>list().filter(x -> x % 2 == 0));
    }

    @Test
    void flatMap() {
        List<Integer> list = List.list(numbers).flatMap(x -> List.list(x, -x));
        try {
            while (list.head() != null) {
                int pre = list.head();
                list = list.tail();
                assertEquals(pre, -1 * list.head());
                list = list.tail();
            }
        } catch (IllegalStateException ignored) {
        }
        assertThrows(IllegalStateException.class, () -> List.<Integer>list().flatMap(x -> List.list(x, -x)));
    }

    @Test
    void filterViaFlatMap() {
        List<Integer> list = BaseLists.filterViaFlatMap(List.list(numbers), x -> x % 2 == 0);
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                assertEquals(number, list.head());
                list = list.tail();
            }
        }
        assertThrows(IllegalStateException.class, () -> List.<Integer>list().filter(x -> x % 2 == 0));
    }

    @Test
    void flattenViaFlatMap() {
        List<List<Integer>> lists = BaseLists.foldRight(List.list(numbers),
                List.list(), (Integer useless) -> (List<List<Integer>> y) -> y.construct(List.list(numbers)));
        assertEquals(numbers.length * numbers.length, BaseLists.flattenViaFlatMap(lists).length());
    }
}