package cn.tmkit.core.tuple;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain Tuple}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class TupleTest {

    @Test
    public void of() {
        Tuple2<Integer, String> tuple2 = Tuple.of(1, "hello");
        assertEquals(1, tuple2.one());
        assertEquals("hello", tuple2.two());

        LocalDate now = LocalDate.now();
        Tuple3<Integer, String, LocalDate> tuple3 = Tuple.of(1, "hello", now);
        assertEquals(1, tuple3.one());
        assertEquals("hello", tuple3.two());
        assertEquals(now, tuple3.three());
    }

}
