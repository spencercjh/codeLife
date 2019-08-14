package chapter6;

import chapter5.AbstractList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractListsTest {
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
    private final Double[] decimals = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};

    private double getAverage(Integer[] array) {
        double average = 0;
        for (Integer number : array) {
            average += number;
        }
        average /= numbers.length;
        return average;
    }

    @Test
    void average() {
        assertEquals(getAverage(numbers), AbstractLists.average(AbstractList.list(decimals)).getOrThrow());
    }

    @Test
    void variance() {
        double average = getAverage(numbers);
        double variance = 0;
        for (Integer number : numbers) {
            variance += Math.pow(number - average, 2);
        }
        variance /= numbers.length;
        assertEquals(variance, AbstractLists.variance(AbstractList.list(decimals)).getOrThrow());
    }

    @Test
    void max() {
        int max1 = (int) AbstractLists.max().apply(AbstractList.list(numbers)).getOrThrow();
        assertEquals(numbers[numbers.length - 1], max1);
    }

    @Test
    void sequence() {
        Option<AbstractList<Integer>> listOption = AbstractLists.sequence(AbstractList.list(numbers).map(Option::some));
        assertTrue(listOption.getOrThrow().length() > 0);
        assertNotNull(listOption.getOrThrow().head());
        assertNotNull(listOption.getOrThrow().tail());
    }

    @Test
    void traverse() {
        Option<AbstractList<String>> listOption = AbstractLists.traverse(AbstractList.list(numbers),
                (Integer x) -> Option.some(x.toString()));
        assertTrue(listOption.getOrThrow().length() > 0);
        assertEquals("1", listOption.getOrThrow().head());
    }
}