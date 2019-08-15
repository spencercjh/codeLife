package chapter7;

import chapter6.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseResultTest {

    @Test
    void getOrElse() {
        assertEquals(1, Result.of(1).getOrElse(2));
        assertEquals(1, Result.of(null).getOrElse(1));
        assertEquals(1, Result.empty().getOrElse(1));
    }

    @Test
    void testGetOrElse() {
        assertEquals(1, Result.of(1).getOrElse(() -> 2));
        assertEquals(2, Result.of(null).getOrElse(() -> 2));
        assertNull(Result.of(null).getOrElse(() -> null));
        assertEquals(1, Result.empty().getOrElse(() -> 1));
    }

    @Test
    void map() {
        assertEquals(Result.of("1"), Result.of(1).map(Object::toString));
        assertSame(Result.failure().getClass(), Result.of(null).map(Object::toString).getClass());
        assertSame(Result.empty().getClass(), Result.empty().map(Object::toString).getClass());
    }

    @Test
    void flatMap() {
        assertEquals(Result.of("1"), Result.of(1).flatMap(x -> Result.of(x.toString())));
        assertEquals(Result.failure().getClass(), Result.of(null).flatMap(x -> Result.of(x.toString())).getClass());
        assertEquals(Result.empty().getClass(), Result.empty().flatMap(x -> Result.of(x.toString())).getClass());
    }

    @Test
    void toOption() {
        assertEquals(Option.some(1), Result.of(1).toOption());
        assertEquals(Option.none(), Result.of(null).toOption());
        assertEquals(Option.none(), Result.empty().toOption());
    }

    @Test
    void mapFailure() {
        Result<Integer> success = Result.of(1);
        assertSame(success, success.mapFailure("test success"));
        assertSame(Result.empty(), Result.empty().mapFailure("test empty"));
        assertSame(Result.failure().getClass(), Result.failure().mapFailure("test empty").getClass());
    }

    @Test
    void testMapFailure() {
        Result<Integer> success = Result.of(1);
        assertSame(success, success.mapFailure(new RuntimeException("test success")));
        assertSame(Result.empty(), Result.empty().mapFailure(new RuntimeException("test empty")));
        assertSame(Result.failure().getClass(), Result.failure().mapFailure(
                new RuntimeException("test failure")).getClass());
    }

    @Test
    void testMapFailure1() {
        Result<Integer> success = Result.of(1);
        assertSame(success, success.mapFailure("test success", new RuntimeException("test success")));
        assertSame(Result.empty(), Result.empty().mapFailure("test empty", new RuntimeException("test empty")));
        assertSame(Result.failure().getClass(), Result.failure().mapFailure(
                "test failure", new RuntimeException("test failure")).getClass());
    }

    @Test
    void forEach() {
        Result.of(1).forEach(useless -> assertTrue(true));
        Result.empty().forEach(useless -> assertFalse(false));
        Result.failure().forEach(useless -> assertFalse(false));
    }

    @Test
    void forEachOrThrow() {
        Result.of(1).forEachOrThrow(useless -> assertTrue(true));
        Result.empty().forEachOrThrow(useless -> assertFalse(false));
        assertThrows(RuntimeException.class, () -> Result.failure().forEachOrThrow(useless -> {
        }));
    }

    @Test
    void forEachOrException() {
        Result.of(1).forEachOrException(useless -> assertTrue(true));
        assertSame(Result.empty(), Result.empty().forEachOrException(useless -> {
        }));
        assertSame(Result.success(new Exception()).getClass(), Result.failure().forEachOrException(useless -> {
        }).getClass());
    }
}