package chapter6;

import chapter5.AbstractList;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static chapter6.AbstractLists.max;
import static org.junit.jupiter.api.Assertions.*;

class AbstractOptionTest {
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};

    private static int throwException() {
        throw new NoSuchElementException("get default exception");
    }

    private static int getDefault() {
        return 1;
    }

    @Test
    void some() {
        assertNotNull(AbstractOption.some(1));
    }

    @Test
    void none() {
        assertNotNull(AbstractOption.none());
    }

    @Test
    void getOrThrow() {
        int max1 = (int) max().apply(AbstractList.list(numbers)).getOrThrow();
        assertEquals(numbers[numbers.length - 1], max1);
        assertThrows(IllegalStateException.class, () -> max().apply(AbstractList.list()).getOrThrow());
        assertThrows(IllegalStateException.class, () -> AbstractOption.none().getOrThrow());
    }

    @Test
    void getOrElse() {
        int max1 = (int) max().apply(AbstractList.list(numbers)).getOrElse(AbstractOptionTest::throwException);
        assertEquals(numbers[numbers.length - 1], max1);
        assertThrows(NoSuchElementException.class, () -> max().apply(AbstractList.list()).getOrElse(AbstractOptionTest::throwException));
        assertEquals(1, AbstractOption.none().getOrElse(AbstractOptionTest::getDefault));
    }

    @Test
    void map() {
        AbstractOption<Integer> integerOption = AbstractOption.some(1);
        AbstractOption<String> stringOption = integerOption.map(Object::toString);
        assertEquals("1", stringOption.getOrThrow());
        assertEquals(AbstractOption.none(), AbstractOption.none().map(Object::toString));
    }

    @Test
    void flatMap() {
        AbstractOption<Integer> integerOption = AbstractOption.some(1);
        AbstractOption<String> stringOption = integerOption.flatMap((Integer x) -> AbstractOption.some(x.toString()));
        assertEquals("1", stringOption.getOrThrow());
        assertEquals(AbstractOption.none(), AbstractOption.none().flatMap(AbstractOption::some));
    }

    @Test
    void testToString() {
        assertEquals("None{}", AbstractOption.none().toString());
        assertEquals(String.format("Some(%s)", getDefault()), AbstractOption.some(getDefault()).toString());
    }

    @Test
    void filter() {
        assertEquals(2, AbstractOption.some(2).filter(x -> x % 2 == 0).getOrThrow());
    }

    @Deprecated
    @Test
    void orElse() {

    }
}