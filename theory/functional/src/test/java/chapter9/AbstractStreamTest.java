package chapter9;

import chapter7.Result;
import chapter8.AdvancedList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AbstractStreamTest {
    private static Stream<Integer> INTEGER_STREAM = null;
    private final Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};

    @BeforeAll
    static void setUpStream() {
        INTEGER_STREAM = AbstractStream.integerStream(1);
        assertNotNull(INTEGER_STREAM);
    }

    @Test
    void integerStream() {
        assertEquals(1, INTEGER_STREAM.head());
        assertEquals(2, INTEGER_STREAM.tail().head());
        assertEquals(3, INTEGER_STREAM.tail().tail().head());
        assertEquals(4, INTEGER_STREAM.tail().tail().tail().head());
    }

    @Test
    void construct() {
        assertEquals(1, AbstractStream.construct(() -> 1, () -> null).head());
        assertEquals(1, AbstractStream.construct(() -> 1, () ->
                AbstractStream.construct(() -> 2, () -> null)).head());
    }

    @Test
    void empty() {
        assertNotNull(AbstractStream.empty());
        assertTrue(AbstractStream.empty().isEmpty());
    }

    @Test
    void head() {
        assertEquals(1, INTEGER_STREAM.head());
        assertThrows(IllegalStateException.class, () -> AbstractStream.empty().head());
    }

    @Test
    void tail() {
        assertEquals(2, INTEGER_STREAM.tail().head());
        assertThrows(IllegalStateException.class, () -> AbstractStream.empty().tail());
    }

    @Test
    void isEmpty() {
        assertTrue(AbstractStream.empty().isEmpty());
        assertFalse(INTEGER_STREAM.isEmpty());
    }

    @Test
    void headOption() {
        assertEquals(Result.success("").getClass().toGenericString(), INTEGER_STREAM.headOption().getClass().toGenericString());
        assertEquals(1, INTEGER_STREAM.headOption().getOrElse(2));
        assertEquals(Result.empty(), AbstractStream.empty().headOption());
    }

    @Test
    void take() {
        assertEquals(AbstractStream.empty(), AbstractStream.empty().take(5));
        assertEquals(AdvancedList.list(numbers), INTEGER_STREAM.take(5).toList());
    }

    @Test
    void drop() {
        assertEquals(AbstractStream.empty(), AbstractStream.empty().drop(5));
        assertEquals(AdvancedList.list(), INTEGER_STREAM.take(5).drop(5).toList());
    }

    @Test
    void toList() {
        assertEquals(AdvancedList.list(), AbstractStream.empty().toList());
        assertEquals(AdvancedList.list(numbers), INTEGER_STREAM.take(5).toList());
    }

    @Test
    void takeWhile() {
        assertEquals(AbstractStream.empty(), AbstractStream.<Integer>empty().takeWhile(x -> x < 3));
        assertEquals(AdvancedList.list(numbers).filter(x -> x < 3), INTEGER_STREAM.take(5).takeWhile(x -> x < 3).toList());
    }

    @Test
    void dropWhile() {
        assertEquals(AbstractStream.empty(), AbstractStream.<Integer>empty().dropWhile(x -> x > 3));
        assertEquals(AdvancedList.list(numbers).dropWhile(x -> x > 3), INTEGER_STREAM.take(5).dropWhile(x -> x > 3).toList());
    }

    @Test
    void exists() {
        assertFalse(AbstractStream.empty().exists(x -> true));
        assertTrue(INTEGER_STREAM.exists(x -> x % 2 == 0));
    }

    @Test
    void foldRight() {
        assertEquals(15, INTEGER_STREAM.take(5).foldRight(() -> 0, x -> y -> x + y.get()));
    }

    @Test
    void headOptionViaFoldRight() {
        assertEquals(Result.success("").getClass().toGenericString(), INTEGER_STREAM.headOptionViaFoldRight().getClass().toGenericString());
        assertEquals(1, INTEGER_STREAM.headOptionViaFoldRight().getOrElse(2));
        assertEquals(Result.empty(), AbstractStream.empty().headOptionViaFoldRight());
    }

    @Test
    void map() {
        assertThrows(IllegalStateException.class, () -> AbstractStream.empty().map(Object::toString));
        assertEquals(AdvancedList.list(numbers).toString(),
                INTEGER_STREAM.take(5).map(Object::toString).toList().toString());
    }

    @Test
    void filter() {
        assertEquals(AbstractStream.empty(), AbstractStream.<Integer>empty().filter(x -> x > 3));
        assertEquals(AdvancedList.list(numbers).filter(x -> x % 2 == 0),
                INTEGER_STREAM.take(5).filter(x -> x % 2 == 0).toList());
    }

    @Test
    void append() {
        assertEquals(AdvancedList.list(numbers), AbstractStream.<Integer>empty().append(() -> INTEGER_STREAM.take(5)).toList());
        assertEquals(AdvancedList.list(1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                INTEGER_STREAM.take(5).append(() -> INTEGER_STREAM.take(10)).toList());
    }

    @Test
    void flatMap() {
        assertThrows(IllegalStateException.class, () -> AbstractStream.empty().flatMap(x ->
                AbstractStream.construct(x::toString, AbstractStream::empty)));
        assertEquals(AdvancedList.list(numbers).flatMap((Integer x) -> AdvancedList.construct(x + " test", AdvancedList.list())),
                INTEGER_STREAM.take(5).flatMap((Integer x) ->
                        AbstractStream.construct(() -> x + " test", AbstractStream::empty)).toList());
    }

    @Test
    void find() {
        assertEquals(Result.empty(), AbstractStream.empty().find(x -> true));
        assertEquals(Result.success(2), INTEGER_STREAM.take(5).find(x -> x % 2 == 0));
    }

    @Test
    void repeat() {
        assertEquals(AdvancedList.list(Result.empty(), Result.empty(), Result.empty()),
                AbstractStream.repeat(Result.empty()).take(3).toList());
    }

    @Test
    void iterator() {
        assertEquals(INTEGER_STREAM.take(5).toList(), AbstractStream.iterator(1, x -> x + 1).take(5).toList());
        assertEquals(INTEGER_STREAM.take(5).toList(), AbstractStream.iterator((Supplier<Integer>) () -> 1,
                (Function<Integer, Integer>) integer -> integer + 1).take(5).toList());
    }

    @Test
    void unfold() {
        assertEquals(INTEGER_STREAM.take(20).filter(x -> x % 2 == 0).toList(),
                AbstractStream.unfold(2, x ->
                        Result.success(new Tuple<>(x, x + 2))).take(10).toList());
    }

    @Test
    void fibs() {
        assertEquals(AdvancedList.list(1, 1, 2, 3, 5, 8, 13, 21, 34, 55), AbstractStream.fibs().take(10).toList());
    }
}