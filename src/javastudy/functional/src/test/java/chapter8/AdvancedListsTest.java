package chapter8;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvancedListsTest {
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};

    @Test
    void flattenResult() {
    }

    @Test
    void sequence() {
    }

    @Test
    void sequenceFilterEmpty() {
    }

    @Test
    void traverse() {
    }

    @Test
    void sequenceWithTraverse() {
    }

    @Test
    void sequenceFilterEmptyWithTraverse() {
    }

    @Test
    void zipWith() {
    }

    @Test
    void lengthMemorized() {
    }

    @Test
    void headOption() {
    }

    @Test
    void lastOption() {
    }

    @Test
    void foldLeft() {
    }

    @Test
    void getAt() {
        AdvancedList<Integer> list = AdvancedList.list(numbers);
        assertEquals(numbers[0], list.getAt(0).getOrElse(2));
    }

    @Test
    void groupBy() {
        AdvancedList<Integer> list = AdvancedList.list(numbers);
        Map<String, AdvancedList<Integer>> map = list.groupBy(x -> x % 2 == 0 ? "偶数" : "奇数");
        map.forEach((s, integerAdvancedList) ->
                System.out.println(String.format("key: %s values: %s", s, integerAdvancedList.toString())));
        assertTrue(map.containsKey("偶数"));
        assertEquals(2, map.get("偶数").lengthMemorized());
        assertTrue(map.containsKey("奇数"));
        assertEquals(3, map.get("奇数").lengthMemorized());
    }

    @Test
    void exists() {
        AdvancedList<Integer> list = AdvancedList.list(numbers);
        assertTrue(list.exists((Integer x) -> x % 2 == 0));
        assertTrue(list.exists((Integer x) -> x % 2 != 0));
    }

    @Test
    void forAll() {
    }

    @Test
    void divide() {
    }

    @Test
    void splitListAt() {
    }
}