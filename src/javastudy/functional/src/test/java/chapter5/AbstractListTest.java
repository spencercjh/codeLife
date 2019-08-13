package chapter5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AbstractList的测试类
 */
class AbstractListTest {
    private final AbstractList<Integer> nullList = AbstractList.list();
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
    private final Integer[] numbers2 = new Integer[]{6, 7, 8, 9, 10};
    private final Double[] decimals = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};

    @Test
    void staticFoldRight() {
        assertEquals(AbstractList.list(numbers).toString(),
                AbstractList.foldRight(AbstractList.list(numbers), AbstractList.list(),
                        (Integer x) -> (AbstractList<Object> y) -> y.construct(x))
                        .toString());
    }

    @Test
    void sum() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(15, AbstractList.sum(list, 0));
    }

    @Test
    void sumViaFoldLeft() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(15, AbstractList.sumViaFoldLeft(list, 0));
    }

    @Test
    void productViaFoldRight() {
        Double[] numbers = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        AbstractList<Double> list = AbstractList.list(numbers);
        assertEquals(120.0, AbstractList.productViaFoldRight(list, 1));
    }

    @Test
    void lengthViaFoldLeft() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(numbers.length, AbstractList.lengthViaFoldLeft(list));
    }

    @Test
    void reverseViaFoldLeft() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(list.reverse().toString(), AbstractList.reverseViaFoldLeft(list).toString());
    }

    @Test
    void foldRightViaFoldLeft() {
        assertEquals(AbstractList.list(numbers).toString(),
                AbstractList.foldRightViaFoldLeft(AbstractList.list(numbers), AbstractList.list(),
                        (Integer x) -> (AbstractList<Object> y) -> y.construct(x))
                        .toString());
    }

    @Test
    void product() {
        AbstractList<Double> list = AbstractList.list(decimals);
        int sum = 1;
        for (Integer number : numbers) {
            sum *= number;
        }
        assertEquals(sum, AbstractList.product(list, 1));
    }

    @Test
    void concatViaFoldRight() {
        AbstractList<Integer> concatList = AbstractList.concatViaFoldRight(AbstractList.list(numbers),
                AbstractList.list(numbers2));
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
        AbstractList<AbstractList<Integer>> lists = AbstractList.foldRight(AbstractList.list(numbers),
                AbstractList.list(), (Integer useless) -> (AbstractList<AbstractList<Integer>> y) -> y.construct(AbstractList.list(numbers)));
        assertEquals(numbers.length * numbers.length, AbstractList.flatten(lists).length());
    }

    @Test
    void triple() {
        AbstractList<Integer> list = AbstractList.triple(AbstractList.list(numbers));
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            assertEquals(numbers[i] * 3, list.head());
        }
    }

    @Test
    void doubleToString() {
        assertEquals(AbstractList.list(decimals).toString(),
                AbstractList.doubleToString(AbstractList.list(decimals)).toString());
    }

    @Test
    void list() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        int sum = 0;
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            sum++;
        }
        assertEquals(sum, numbers.length);
    }

    @Test
    void emptyList() {
        assertNotNull(AbstractList.list());
    }

    @Test
    void setHead() {
        AbstractList<Integer> list = AbstractList.list(1);
        list = list.setHead(0);
        assertEquals(0, list.head());
        assertThrows(IllegalStateException.class, () -> nullList.setHead(1));
    }

    @Test
    void concat() {
        AbstractList<Integer> concatList = AbstractList.concat(AbstractList.list(numbers), AbstractList.list(numbers2));
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
        AbstractList<Integer> list = AbstractList.list(numbers);
        list = list.drop(5);
        assertTrue(list.isEmpty());
        assertEquals(nullList, nullList.drop(1));
    }

    @Test
    void dropWhile() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        AbstractList<Integer> list = AbstractList.list(numbers);
        list = list.dropWhile((x) -> x != 0);
        assertTrue(list.isEmpty());
        assertEquals(nullList, nullList.dropWhile(x -> x != 0));
    }

    @Test
    void dropLast() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        AbstractList<Integer> list = AbstractList.list(numbers);
        list = list.dropLast().dropLast().dropLast().dropLast();
        assertEquals(1, list.head());
        assertEquals(nullList, nullList.dropLast());
    }


    @Test
    void length() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(numbers.length, list.length());
        assertEquals(0, nullList.length());
    }


    @Test
    void foldLeft() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(list.toString(),
                list.foldLeft(AbstractList.list(), (AbstractList<Object> x) -> x::construct).reverse().toString());
        assertEquals(0, nullList.foldLeft(0, null));
    }

    @Test
    void foldRight() {
        AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(list.toString(),
                list.foldRight(AbstractList.list(), x -> y -> y.construct(x)).toString());
        assertEquals(0, nullList.foldRight(0, null));
    }

    @Test
    void head() {
        assertThrows(IllegalStateException.class, AbstractList.list()::head);
        assertNotNull(AbstractList.list(0).head());
    }

    @Test
    void tail() {
        assertThrows(IllegalStateException.class, AbstractList.list()::tail);
        assertEquals(AbstractList.list(1).tail(), AbstractList.list());
    }

    @Test
    void isEmpty() {
        assertTrue(AbstractList.list()::isEmpty);
        assertFalse(AbstractList.list(0)::isEmpty);
    }

    @Test
    void reverse() {
        assertEquals(nullList, nullList.reverse());
        final AbstractList<Integer> list = AbstractList.list(numbers);
        assertEquals(list.toString(), list.reverse().reverse().toString());
    }

    @Test
    void testToString() {
        assertNotNull(nullList.toString());
        assertNotNull(AbstractList.list(numbers).toString());
    }

    @Test
    void construct() {
        assertEquals(AbstractList.list(numbers).toString(), AbstractList.list()
                .construct(5)
                .construct(4)
                .construct(3)
                .construct(2)
                .construct(1)
                .toString());
    }

    @Test
    void map() {
        AbstractList<Integer> list = AbstractList.list(numbers).map(x -> x + 1);
        for (int i = 0; i < numbers.length; i++, list = list.tail()) {
            assertEquals(numbers[i] + 1, list.head());
        }
        assertThrows(IllegalStateException.class, () -> AbstractList.<Integer>list().map(x -> x + 1));
    }

    @Test
    void filter() {
        AbstractList<Integer> list = AbstractList.list(numbers).filter(x -> x % 2 == 0);
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                assertEquals(number, list.head());
                list = list.tail();
            }
        }
        assertThrows(IllegalStateException.class, () -> AbstractList.<Integer>list().filter(x -> x % 2 == 0));
    }

    @Test
    void flatMap() {
        AbstractList<Integer> list = AbstractList.list(numbers).flatMap(x -> AbstractList.list(x, -x));
        try {
            while (list.head() != null) {
                int pre = list.head();
                list = list.tail();
                assertEquals(pre, -1 * list.head());
                list = list.tail();
            }
        } catch (IllegalStateException ignored) {
        }
        assertThrows(IllegalStateException.class, () -> AbstractList.<Integer>list().flatMap(x -> AbstractList.list(x, -x)));
    }

    @Test
    void filterViaFlatMap() {
        AbstractList<Integer> list = AbstractList.filterViaFlatMap(AbstractList.list(numbers), x -> x % 2 == 0);
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                assertEquals(number, list.head());
                list = list.tail();
            }
        }
        assertThrows(IllegalStateException.class, () -> AbstractList.<Integer>list().filter(x -> x % 2 == 0));
    }

    @Test
    void flattenViaFlatMap() {
        AbstractList<AbstractList<Integer>> lists = AbstractList.foldRight(AbstractList.list(numbers),
                AbstractList.list(), (Integer useless) -> (AbstractList<AbstractList<Integer>> y) -> y.construct(AbstractList.list(numbers)));
        assertEquals(numbers.length * numbers.length, AbstractList.flattenViaFlatMap(lists).length());
    }
}