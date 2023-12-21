package cn.tmkit.core.date;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

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
        LocalDateTime ld = LocalDateTime.of(2023, 1, 18, 10, 10, 10);
        LocalDateTime ldt = LocalDateTimeUtil.endOfYear(ld);
        LocalDateTime expected = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        assertEquals(expected, ldt);
    }

    @Test
    public void parseToTime() {
        String text = null;
        assertNull(LocalDateTimes.parseToTime(text, (String) null));
        text = "22:01:10";
        assertEquals(LocalTime.of(22, 1, 10, 0), LocalDateTimes.parseToTime(text, DefaultCustomFormatter.NORMAL_TIME));
    }

    @Test
    public void parseToDate() {
        String text = null;
        assertNull(LocalDateTimes.parseToDate(text, (String) null));
        text = "2023-01-10";
        assertEquals(LocalDate.of(2023, 1, 10), LocalDateTimes.parseToDate(text, DefaultCustomFormatter.NORMAL_DATE));
    }

    @Test
    public void toEpochMilli() {
        assertEquals((System.currentTimeMillis() / 1000) * 1000L,
                LocalDateTimes.toEpochMilli(LocalDateTimes.nowWithoutMills()));
    }

    @Test
    public void isLeapYear() {
        LocalDateTime ldt = null;
        assertFalse(LocalDateTimeUtil.isLeapYear(ldt));
        ldt = LocalDateTimeUtil.of(2023, 9, 10, 0, 0, 0);
        assertFalse(LocalDateTimeUtil.isLeapYear(ldt));
        ldt = LocalDateTimeUtil.of(2022, 9, 10, 0, 0, 0);
        assertFalse(LocalDateTimeUtil.isLeapYear(ldt));
        ldt = LocalDateTimeUtil.of(2020, 9, 10, 0, 0, 0);
        assertTrue(LocalDateTimeUtil.isLeapYear(ldt));
    }

    @Test
    public void offsetSecond() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusSeconds(10);
        assertEquals(expected, LocalDateTimeUtil.offsetSecond(10));
        expected = now.minusSeconds(10);
        assertEquals(expected, LocalDateTimeUtil.offsetSecond(-10));
    }

    @Test
    public void offsetMinute() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusMinutes(10);
        assertEquals(expected, LocalDateTimeUtil.offsetMinute(10));
        expected = now.minusMinutes(10);
        assertEquals(expected, LocalDateTimeUtil.offsetMinute(-10));
    }

    @Test
    public void offsetHour() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusHours(10);
        assertEquals(expected, LocalDateTimeUtil.offsetHour(10));
        expected = now.minusHours(10);
        assertEquals(expected, LocalDateTimeUtil.offsetHour(-10));
    }

    @Test
    public void offsetDay() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusDays(10);
        assertEquals(expected, LocalDateTimeUtil.offsetDay(10));
        expected = now.minusDays(10);
        assertEquals(expected, LocalDateTimeUtil.offsetDay(-10));
    }

    @Test
    public void offsetMonth() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusMonths(10);
        assertEquals(expected, LocalDateTimeUtil.offsetMonth(10));
        expected = now.minusMonths(10);
        assertEquals(expected, LocalDateTimeUtil.offsetMonth(-10));
    }

    @Test
    public void offsetYear() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusYears(10);
        assertEquals(expected, LocalDateTimeUtil.offsetYear(10));
        expected = now.minusYears(10);
        assertEquals(expected, LocalDateTimeUtil.offsetYear(-10));
    }

    @Test
    public void offsetWeek() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusWeeks(10);
        assertEquals(expected, LocalDateTimeUtil.offsetWeek(10));
        expected = now.minusWeeks(10);
        assertEquals(expected, LocalDateTimeUtil.offsetWeek(-10));
    }

    @Test
    public void offsetDuration() {
        LocalDateTime now = LocalDateTimeUtil.now();
        LocalDateTime expected = now.plusDays(10);
        assertEquals(expected, LocalDateTimeUtil.offsetDuration(Duration.ofDays(10)));
    }

    @Test
    public void between() {
        LocalDateTime start = LocalDateTimes.of(2023, 12, 21, 18, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2023, 12, 21, 19, 20, 0);
        Duration duration = LocalDateTimes.between(start, end);
        assertEquals(1, duration.toHours());
        assertEquals(80, duration.toMinutes());
        duration = LocalDateTimes.between(end, start);
        assertEquals(1, duration.toHours());
        assertEquals(80, duration.toMinutes());
        duration = LocalDateTimes.between(start, end, false);
        assertEquals(-1, duration.toHours());
        assertEquals(-80, duration.toMinutes());
    }

    @Test
    public void betweenSeconds() {
        LocalDateTime start = LocalDateTimes.of(2023, 12, 21, 19, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2023, 12, 21, 19, 20, 0);
        long seconds = LocalDateTimes.betweenSeconds(start, end);
        assertEquals(1200, seconds);
        seconds = LocalDateTimes.betweenSeconds(start, end, false);
        assertEquals(-1200, seconds);
    }

    @Test
    public void aa() {
        LocalDateTime start = LocalDateTimes.of(2023, 12, 21, 19, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2023, 12, 21, 19, 20, 0);
        long minutes = LocalDateTimes.betweenMinutes(start, end);
        assertEquals(20, minutes);
        minutes = LocalDateTimes.betweenMinutes(start, end, false);
        assertEquals(-20, minutes);
    }

}
