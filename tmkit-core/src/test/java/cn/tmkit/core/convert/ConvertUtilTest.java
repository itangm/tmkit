package cn.tmkit.core.convert;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester for {@linkplain }
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
public class ConvertUtilTest {

    @Test
    public void toStr() {
        Object value = 1;
        assertEquals("1", ConvertUtil.toStr(value));
        value = 1L;
        assertEquals("1", ConvertUtil.toStr(value));
        value = true;
        assertEquals("true", ConvertUtil.toStr(value));
        value = LocalDate.of(2023, 3, 7);
        assertEquals("2023-03-07", ConvertUtil.toStr(value));
        value = LocalDateTime.of(LocalDate.of(2023, 3, 7), LocalTime.of(9, 10, 1));
        assertEquals("2023-03-07T09:10:01", ConvertUtil.toStr(value));
        value = new BigDecimal("12.111");
        assertEquals("12.111", ConvertUtil.toStr(value));
    }

    @Test
    public void toChar() {
    }

    @Test
    void testToChar() {
    }

    @Test
    void toByte() {
    }

    @Test
    void testToByte() {
    }

    @Test
    void toShort() {
    }

    @Test
    void testToShort() {
    }

    @Test
    void toInt() {
    }

    @Test
    void testToInt() {
    }

    @Test
    void toLong() {
    }

    @Test
    void testToLong() {
    }

    @Test
    void toDouble() {
    }

    @Test
    void testToDouble() {
    }

    @Test
    void toFloat() {
    }

    @Test
    void testToFloat() {
    }

    @Test
    void toBool() {
    }

    @Test
    void testToBool() {
    }

    @Test
    void toBigInteger() {
    }

    @Test
    void testToBigInteger() {
    }

    @Test
    void toBigDecimal() {
    }

    @Test
    void testToBigDecimal() {
    }

    @Test
    void toDate() {
    }

    @Test
    void testToDate() {
    }

    @Test
    void toLocalDateTime() {
    }

    @Test
    void testToLocalDateTime() {
    }

    @Test
    void toLocalDate() {
    }

    @Test
    void testToLocalDate() {
    }

    @Test
    void toLocalTime() {
    }

    @Test
    void testToLocalTime() {
    }

    @Test
    void toInstant() {
    }

    @Test
    void testToInstant() {
    }

    @Test
    void doConvert() {
    }

    @Test
    void toHexStr() {
    }

    @Test
    void testToHexStr() {
    }

    @Test
    void testToHexStr1() {
    }

    @Test
    void hexToBytes() {
    }

    @Test
    void hexToStr() {
    }

    @Test
    void convert() {
    }
}
