package cn.tmkit.core.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester for {@linkplain LocalDateTimeUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-05-03
 */
public class LocalDateTimeUtilTest {

    @Test
    public void beginOfMonth() {
        LocalDate ld = LocalDate.of(2023, 5, 3);
        LocalDateTime ldt = LocalDateTimeUtil.beginOfMonth(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 5, 1, 0, 0, 0);
        assertEquals(expected, ldt);

        ld = LocalDate.of(2020, 2, 4);
        ldt = LocalDateTimeUtil.beginOfMonth(ld);
        expected = LocalDateTime.of(2020, 2, 1, 0, 0, 0);
        assertEquals(expected, ldt);
    }

    @Test
    public void testBeginOfMonth() {
        LocalDateTime ld = LocalDateTime.of(2023, 8, 31, 23, 59, 59);
        LocalDateTime ldt = LocalDateTimeUtil.beginOfMonth(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        assertEquals(expected, ldt);
    }

    @Test
    public void endOfMonth() {
        LocalDateTime ld = LocalDateTime.of(2023, 1, 31, 23, 59, 59);
        LocalDateTime ldt = LocalDateTimeUtil.endOfMonth(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 1, 31, 23, 59, 59);
        assertEquals(expected, ldt);
    }

    @Test
    public void testEndOfMonth() {
        LocalDate ld = LocalDate.of(2023, 4, 3);
        LocalDateTime ldt = LocalDateTimeUtil.endOfMonth(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 4, 30, 23, 59, 59);
        assertEquals(expected, ldt);

        ld = LocalDate.of(2020, 2, 4);
        ldt = LocalDateTimeUtil.endOfMonth(ld);
        expected = LocalDateTime.of(2020, 2, 29, 23, 59, 59);
        assertEquals(expected, ldt);

        ld = LocalDate.of(2021, 2, 4);
        ldt = LocalDateTimeUtil.endOfMonth(ld);
        expected = LocalDateTime.of(2021, 2, 28, 23, 59, 59);
        assertEquals(expected, ldt);

    }

    @Test
    public void startOfQuarter() {
        LocalDate ld = LocalDate.of(2023, 5, 3);
        LocalDateTime ldt = LocalDateTimeUtil.startOfQuarter(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 4, 1, 0, 0, 0);
        assertEquals(expected, ldt);
    }

    @Test
    public void testStartOfQuarter() {
        LocalDateTime ld = LocalDateTime.of(2023, 8, 31, 23, 59, 59);
        LocalDateTime ldt = LocalDateTimeUtil.startOfQuarter(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 7, 1, 0, 0, 0);
        assertEquals(expected, ldt);
    }

    @Test
    public void endOfQuarter() {
        LocalDate ld = LocalDate.of(2023, 5, 3);
        LocalDateTime ldt = LocalDateTimeUtil.endOfQuarter(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 6, 30, 23, 59, 59);
        assertEquals(expected, ldt);
    }

    @Test
    public void testEndOfQuarter() {
        LocalDateTime ld = LocalDateTime.of(2023, 1, 31, 23, 59, 59);
        LocalDateTime ldt = LocalDateTimeUtil.endOfQuarter(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 3, 31, 23, 59, 59);
        assertEquals(expected, ldt);
    }

    @Test
    public void startOfYear() {
        LocalDate ld = LocalDate.of(2023, 5, 3);
        LocalDateTime ldt = LocalDateTimeUtil.startOfYear(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        assertEquals(expected, ldt);
    }

    @Test
    public void testStartOfYear() {

    }

    @Test
    public void endOfYear() {
        LocalDate ld = LocalDate.of(2023, 5, 3);
        LocalDateTime ldt = LocalDateTimeUtil.endOfYear(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        assertEquals(expected, ldt);
    }

    @Test
    public void testEndOfYear() {

    }
}
