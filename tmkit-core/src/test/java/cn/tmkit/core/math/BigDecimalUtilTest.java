package cn.tmkit.core.math;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain BigDecimalUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-09
 */
public class BigDecimalUtilTest {

    @Test
    public void add() {
        int a = 1, b = 1;
        BigDecimal result = BigDecimalUtil.add(a, b);
        assertEquals("2", result.toString());
        short c = 1;
        result = BigDecimalUtil.add(a, b, c);
        assertEquals("3", result.toString());
        double d = 1.24;
        result = BigDecimalUtil.add(a, b, d);
        assertEquals("3.24", result.toString());
        float f = 1.24F;
        result = BigDecimalUtil.add(a, b, f);
        assertEquals("3.24", result.toString());
        d = 1.241;
        result = BigDecimalUtil.add(a, b, d);
        assertEquals("3.24", result.toString());
        d = 1.246;
        result = BigDecimalUtil.add(a, b, d);
        assertEquals("3.25", result.toString());
        d = 1.00;//赋值后，Java存储的是1.0
        result = BigDecimalUtil.add(a, b, d);
        assertEquals("3.0", result.toString());
        result = BigDecimals.add(a, b, "1.00");
        assertEquals("3.00", result.toString());
        result = BigDecimals.add(a, b, FluentBigDecimal.of("1.00"));
        assertEquals("3.00", result.toString());

    }

    @Test
    public void subtract() {
        int a = 1, b = 1;
        BigDecimal result = BigDecimalUtil.subtract(a, b);
        assertEquals("0", result.toString());
        short c = 1;
        result = BigDecimalUtil.subtract(a, b, c);
        assertEquals("-1", result.toString());
        double d = 1.24;
        result = BigDecimalUtil.subtract(a, b, d);
        assertEquals("-1.24", result.toString());
        float f = 1.24F;
        result = BigDecimalUtil.subtract(a, b, f);
        assertEquals("-1.24", result.toString());
        d = 1.241;
        result = BigDecimalUtil.subtract(a, b, d);
        assertEquals("-1.24", result.toString());
        d = 1.246;
        result = BigDecimalUtil.subtract(a, b, d);
        assertEquals("-1.25", result.toString());
        d = 1.00;//赋值后，Java存储的是1.0
        result = BigDecimalUtil.subtract(a, b, d);
        assertEquals("-1.0", result.toString());
        result = BigDecimals.subtract(a, b, "1.00");
        assertEquals("-1.00", result.toString());
        result = BigDecimals.subtract(a, b, FluentBigDecimal.of("1.00"));
        assertEquals("-1.00", result.toString());
    }

    @Test
    public void multiply() {
        int a = 1, b = 1;
        BigDecimal result = BigDecimalUtil.multiply(a, b);
        assertEquals("1", result.toString());
        short c = 1;
        result = BigDecimalUtil.multiply(a, b, c);
        assertEquals("1", result.toString());
        double d = 1.24;
        result = BigDecimalUtil.multiply(a, b, d);
        assertEquals("1.24", result.toString());
        float f = 1.24F;
        result = BigDecimalUtil.multiply(a, b, d, f);
        assertEquals("1.54", result.toString());
        d = 1.241;
        result = BigDecimalUtil.multiply(a, b, d);
        assertEquals("1.24", result.toString());
        d = 1.246;
        result = BigDecimalUtil.multiply(a, b, d);
        assertEquals("1.25", result.toString());
        result = BigDecimals.multiply(a, b, FluentBigDecimal.of("1.00"));
        assertEquals("1.00", result.toString());
        result = BigDecimalUtil.multiply("2.56", "2.56", "2.56");
        // 每次计算都是四舍五入的
        assertEquals("16.77", result.toString());

    }

}
