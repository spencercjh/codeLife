package chapter6;

import chapter5.List;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static chapter5.OptionLists.max;
import static org.junit.jupiter.api.Assertions.*;

class OptionTest {
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};

    private static int throwException() {
        throw new NoSuchElementException("get default exception");
    }

    private static int getDefault() {
        return 1;
    }

    @Test
    void some() {
        assertNotNull(Option.some(1));
    }

    @Test
    void none() {
        assertNotNull(Option.none());
    }

    @Test
    void getOrThrow() {
        int max1 = (int) max().apply(List.list(numbers)).getOrThrow();
        assertEquals(numbers[numbers.length - 1], max1);
        assertThrows(IllegalStateException.class, () -> max().apply(List.list()).getOrThrow());
        assertThrows(IllegalStateException.class, () -> Option.none().getOrThrow());
    }

    @Test
    void getOrElse() {
        int max1 = (int) max().apply(List.list(numbers)).getOrElse(OptionTest::throwException);
        assertEquals(numbers[numbers.length - 1], max1);
        assertThrows(NoSuchElementException.class, () -> max().apply(List.list()).getOrElse(OptionTest::throwException));
        assertEquals(1, Option.none().getOrElse(OptionTest::getDefault));
    }

    @Test
    void map() {
        Option<Integer> integerOption = Option.some(1);
        Option<String> stringOption = integerOption.map(Object::toString);
        assertEquals("1", stringOption.getOrThrow());
        assertEquals(Option.none(), Option.none().map(Object::toString));
    }

    @Test
    void flatMap() {
        Option<Integer> integerOption = Option.some(1);
        Option<String> stringOption = integerOption.flatMap((Integer x) -> Option.some(x.toString()));
        assertEquals("1", stringOption.getOrThrow());
        assertEquals(Option.none(), Option.none().flatMap(Option::some));
    }

    @Test
    void testToString() {
        assertEquals("None{}", Option.none().toString());
        assertEquals(String.format("Some(%s)", getDefault()), Option.some(getDefault()).toString());
    }

    @Test
    void filter() {
        assertEquals(2, Option.some(2).filter(x -> x % 2 == 0).getOrThrow());
    }

    @Deprecated
    @Test
    void orElse() {

    }
}