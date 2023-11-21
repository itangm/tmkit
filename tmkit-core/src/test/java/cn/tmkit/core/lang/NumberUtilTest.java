package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tester for {@linkplain NumberUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-05-03
 */
public class NumberUtilTest {

    @Test
    public void fmtByMoney() {
        assertEquals("1.34", Numbers.fmtByMoney(1.34125));
        assertEquals("21.34", Numbers.fmtByMoney(21.34125));
        assertEquals("21.35", Numbers.fmtByMoney(21.348125));
    }

    @Test
    public void fen2YuanStrLong() {
        Long fen = null;
        assertNull(NumberUtil.fen2YuanStr(fen));
        fen = 0L;
        assertEquals("0.00", Numbers.fen2YuanStr(fen));
        fen = 10L;
        assertEquals("0.10", Numbers.fen2YuanStr(fen));
        fen = 100L;
        assertEquals("1.00", Numbers.fen2YuanStr(fen));
    }

    @Test
    public void fen2YuanStrInteger() {
        Integer fen = null;
        assertNull(NumberUtil.fen2YuanStr(fen));
        fen = 0;
        assertEquals("0.00", Numbers.fen2YuanStr(fen));
        fen = 10;
        assertEquals("0.10", Numbers.fen2YuanStr(fen));
        fen = 100;
        assertEquals("1.00", Numbers.fen2YuanStr(fen));
    }

    @Test
    public void floorDiv() {
        int i = Math.floorDiv(-4, 3);
        System.out.println("i = " + i);
        i = Numbers.floorDiv(-4, 3);
        System.out.println("i = " + i);

    }

    @Test
    public void createBigDecimal() {
        Byte b = 1;
        BigDecimal bigDecimal = Numbers.createBigDecimal(b);
        System.out.println("bigDecimal = " + bigDecimal);
    }

    @Test
    public void add() {
        System.out.println(2.50D);
        double a = 1.234, b = 5.66788;
        assertEquals(6.90188, NumberUtil.add(a, b));
        assertEquals("6.90", NumberUtil.addAsStr(a, b));

        BigDecimal e = new BigDecimal("1.234");
        BigDecimal f = new BigDecimal("5.66788");
        assertEquals(6.90188D, NumberUtil.addAsDouble(e, f));
        assertEquals("6.90", NumberUtil.addAsStr(e, f));

        Number m = new AtomicLong(10);
        Number n = 1.23333D;
        assertEquals(11.23333, NumberUtil.addAsDouble(m, n));
        assertEquals("11.23", NumberUtil.addAsStr(m, n));

        Object x = "9.9998";
        Object y = "0.0002";
        assertEquals("10.00", NumberUtil.addAsStr(x, y));

        assertEquals(11D, NumberUtil.addAsDouble(x, y, 1));
        assertEquals("11.00", NumberUtil.addAsStr(x, y, 1));
    }

    @Test
    public void sub() {
        long a = 10, b = 3, c = 5;
        assertEquals(2, NumberUtil.sub(a, b, c));
        assertEquals(BigDecimal.ZERO, NumberUtil.sub(a, b, c, 2));

        double e = 1, f = 0.9;
        assertEquals(0.1, NumberUtil.sub(e, f));
        double g = 0.05;
        assertEquals(0.05, NumberUtil.sub(e, f, g));

        BigDecimal m = new BigDecimal(20);
        BigDecimal n = new BigDecimal(10);
        assertEquals(new BigDecimal(10), NumberUtil.sub(m, n));
        assertEquals(10D, NumberUtil.subAsDouble(m, n));

        Number p = new AtomicLong(10);
        Number q = 1.23333D;
        assertEquals(8.76667, NumberUtil.subAsDouble(p, q));

        assertEquals("4.1002", Numbers.subAsStr(4, RoundingMode.UNNECESSARY, new Object[]{9.4456, 1.222, 4.1234}));
    }

    @Test
    public void mul() {
        double a = 10.0, b = 3.0;
        assertEquals(30, NumberUtil.mul(a, b));
        a = 1.83;
        assertEquals(5.49, NumberUtil.mul(a, b));
        b = 3.3;
        assertEquals(6.039, NumberUtil.mul(a, b));
        assertEquals("6.04", NumberUtil.mulAsStr(a, b));
        assertEquals("6.0390", NumberUtil.mulAsStr(a, b, 4));
        long d = 9;
        assertEquals(54.351, NumberUtil.mulAsDouble(a, b, d));

        BigDecimal m = new BigDecimal("2.345");
        BigDecimal n = new BigDecimal("1.21");
        assertEquals(new BigDecimal("2.83745"), NumberUtil.mul(m, n));
        assertEquals("2.84", NumberUtil.mulAsStr(m, n));
        assertEquals(2.83745, NumberUtil.mulAsDouble(m, n));

        String x = "1.23";
        String y = "1.55";
        assertEquals(new BigDecimal("1.9065"), NumberUtil.mul(x, y));
        assertEquals("1.91", NumberUtil.mulAsStr(x, y));

        Number p = 1.23333D;
        Number q = new AtomicLong(10);
        assertEquals(12.3333, NumberUtil.mulAsDouble(p, q));
        assertEquals("12.33", NumberUtil.mulAsStr(p, q));

        assertEquals(new BigDecimal(1000), NumberUtil.mul(10, 10, 5, 2));
        assertEquals(500, NumberUtil.mul(10, 10, 5));
    }

    @Test
    public void div() {
        double a = 10, b = 3;
        assertEquals(3.33, NumberUtil.div(a, b));
        assertEquals("3.33", NumberUtil.divAsStr(a, b));
        assertEquals(3.333, NumberUtil.div(a, b, 3));

        a = 15.0;
        b = 4;
        assertEquals(3.75, NumberUtil.div(a, b));
        assertEquals("3.75", NumberUtil.divAsStr(a, b));

        BigDecimal m = new BigDecimal(20);
        BigDecimal n = new BigDecimal(4);
        assertEquals(new BigDecimal("5.00"), NumberUtil.div(m, n));
        assertEquals("5.00", NumberUtil.divAsStr(m, n));

        AtomicLong x = new AtomicLong(50);
        int y = 8;
        assertEquals(new BigDecimal("6.25"), NumberUtil.div(x, y));
        assertEquals(6.25, NumberUtil.divAsDouble(x, y));
        assertEquals("6.25", NumberUtil.divAsStr(x, y));

        String p = "30";
        String q = "4";
        assertEquals(new BigDecimal("7.50"), NumberUtil.div(p, q));
        assertEquals(7.5, NumberUtil.divAsDouble(p, q));
        assertEquals("7.50", NumberUtil.divAsStr(p, q));

    }

}
