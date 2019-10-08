package chapter6;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionsTest {
    @Test
    void lift() {
        Function<Option<String>, Option<String>> upperOption = Options.lift(String::toUpperCase);
        assertEquals("test".toUpperCase(), upperOption.apply(Option.some("test")).getOrThrow());
    }

    @Test
    void halfLift() {
        assertEquals("1", Options.halfLift(Object::toString).apply(1).getOrThrow());
    }

    @Test
    void map2() {
        Option<Integer> parseHexOption = Options.map2(Option.some(16),
                Option.some("F"),
                (Integer radix) -> (String number) -> Integer.parseInt(number, radix));
        assertEquals(15, parseHexOption.getOrThrow());
    }

    @Test
    void map3() {
        assertEquals("11.01", Options.map3(Option.some(1),
                Option.some(1.0),
                Option.some("1"),
                (Integer x) -> (Double y) -> (String c) -> c + y.toString() + x.toString()).getOrThrow());
    }
}