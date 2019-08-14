package chapter6;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractOptionsTest {
    @Test
    void lift() {
        Function<AbstractOption<String>, AbstractOption<String>> upperOption = AbstractOptions.lift(String::toUpperCase);
        assertEquals("test".toUpperCase(), upperOption.apply(AbstractOption.some("test")).getOrThrow());
    }

    @Test
    void halfLift() {
        assertEquals("1", AbstractOptions.halfLift(Object::toString).apply(1).getOrThrow());
    }

    @Test
    void map2() {
        AbstractOption<Integer> parseHexOption = AbstractOptions.map2(AbstractOption.some(16),
                AbstractOption.some("F"),
                (Integer radix) -> (String number) -> Integer.parseInt(number, radix));
        assertEquals(15, parseHexOption.getOrThrow());
    }

    @Test
    void map3() {
        assertEquals("11.01", AbstractOptions.map3(AbstractOption.some(1),
                AbstractOption.some(1.0),
                AbstractOption.some("1"),
                (Integer x) -> (Double y) -> (String c) -> c + y.toString() + x.toString()).getOrThrow());
    }
}