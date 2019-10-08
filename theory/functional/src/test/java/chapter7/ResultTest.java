package chapter7;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void lift() {
        assertEquals(Result.of(2), Result.lift((Function<Integer, Integer>) x -> x + 1).apply(Result.of(1)));
    }

    @Test
    void lift2() {
        assertEquals(Result.of(2),
                Result.lift2((Function<Integer, Function<Integer, Integer>>) x -> y -> x + y)
                        .apply(Result.of(1))
                        .apply(Result.of(1)));
    }

    @Test
    void lift3() {
        assertEquals(Result.of(3),
                Result.lift3((Function<Integer, Function<Integer, Function<Integer, Integer>>>) x -> y -> z -> x + y + z)
                        .apply(Result.of(1))
                        .apply(Result.of(1))
                        .apply(Result.of(1))
        );
    }

    @Test
    void map2() {
        assertEquals(Result.of(2), Result.map2(Result.of(1), Result.of(1), (Integer x) -> (Integer y) -> x + y));
    }

    @Test
    void of() {
        assertNotNull(Result.of(1));
        assertNotNull(Result.of(null));
    }

    @Test
    void testOf() {
        assertNotNull(Result.of(1), "test not null");
        assertNotNull(Result.of(null), "test null");
    }

    @Test
    void testOf1() {
        assertNotNull(Result.of(1, x -> x % 2 == 0));
        assertNotNull(Result.of(2, x -> x % 2 == 0));
        assertNotNull(Result.of(null, (Integer x) -> x % 2 == 0));
    }

    @Test
    void testOf2() {
        assertNotNull(Result.of(1, x -> x % 2 == 0), "test not match");
        assertNotNull(Result.of(2, x -> x % 2 == 0), "test match");
        assertNotNull(Result.of(null, (Integer x) -> x % 2 == 0), "test null");
    }

    @Test
    void failure() {
        assertNotNull(Result.failure());
    }

    @Test
    void testFailure() {
        assertNotNull(Result.failure("test failure factory"));
    }

    @Test
    void testFailure1() {
        assertNotNull(Result.failure(new RuntimeException("test failure factory")));
    }

    @Test
    void success() {
        assertNotNull(Result.success(1));
    }

    @Test
    void empty() {
        assertNotNull(Result.empty());
    }

    @Test
    void ofElse() {
        assertEquals(Result.of(1), Result.of(1).ofElse(() -> Result.of(2)));
        assertEquals(Result.of(1), Result.of(null).ofElse(() -> Result.of(1)));
        assertEquals(Result.of(1), Result.empty().ofElse(() -> Result.of(1)));
    }

    @Test
    void filter() {
        assertSame(Result.of(1).filter(x -> x % 2 == 0).getClass(), Result.failure().getClass());
        assertEquals(Result.of(2), Result.of(2).filter(x -> x % 2 == 0));
        assertSame(Result.of((Integer) null).filter((Integer x) -> x % 2 == 0).getClass(), Result.failure().getClass());
        assertSame(Result.empty().filter(x -> x.toString().length() > 0).getClass(), Result.empty().getClass());
    }

    @Test
    void function() {
        List<Result<Integer>> list = Arrays.asList(
                Result.success(1), Result.success(2), Result.success(3), Result.success(4), Result.success(5));
        Result<Integer> result = list.get(0)
                .flatMap((Integer p1) -> list.get(1)
                        .flatMap((Integer p2) -> list.get(2)
                                .flatMap((Integer p3) -> list.get(3)
                                        .flatMap((Integer p4) -> list.get(4)
                                                .map((Integer p5) -> p1 + p2 + p3 + p4 + p5)))));
        assertEquals(Result.success(15), result);
    }
}