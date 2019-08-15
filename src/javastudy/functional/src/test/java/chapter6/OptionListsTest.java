package chapter6;

import chapter5.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionListsTest {
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
        assertEquals(getAverage(numbers), OptionLists.average(List.list(decimals)).getOrThrow());
    }

    @Test
    void variance() {
        double average = getAverage(numbers);
        double variance = 0;
        for (Integer number : numbers) {
            variance += Math.pow(number - average, 2);
        }
        variance /= numbers.length;
        assertEquals(variance, OptionLists.variance(List.list(decimals)).getOrThrow());
    }

    @Test
    void max() {
        int max1 = (int) OptionLists.max().apply(List.list(numbers)).getOrThrow();
        assertEquals(numbers[numbers.length - 1], max1);
    }

    @Test
    void sequence() {
        Option<List<Integer>> listOption = OptionLists.sequence(List.list(numbers).map(Option::some));
        assertTrue(listOption.getOrThrow().length() > 0);
        assertNotNull(listOption.getOrThrow().head());
        assertNotNull(listOption.getOrThrow().tail());
    }

    @Test
    void traverse() {
        Option<List<String>> listOption = OptionLists.traverse(List.list(numbers),
                (Integer x) -> Option.some(x.toString()));
        assertTrue(listOption.getOrThrow().length() > 0);
        assertEquals("1", listOption.getOrThrow().head());
    }
}