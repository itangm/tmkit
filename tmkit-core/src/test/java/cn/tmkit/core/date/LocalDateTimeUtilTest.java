package cn.tmkit.core.date;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

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
        assertEquals(LocalDateTimes.truncateTo(expected, ChronoUnit.MINUTES),
                LocalDateTimes.truncateTo(LocalDateTimeUtil.offsetDay(10), ChronoUnit.MINUTES));
        expected = now.minusDays(10);
        assertEquals(LocalDateTimes.truncateTo(expected, ChronoUnit.MINUTES),
                LocalDateTimes.truncateTo(LocalDateTimeUtil.offsetDay(-10), ChronoUnit.MINUTES));
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
        assertEquals(-1200, seconds);
        seconds = LocalDateTimes.betweenSeconds(start, end, true);
        assertEquals(1200, seconds);
    }

    @Test
    public void betweenMinutes() {
        LocalDateTime start = LocalDateTimes.of(2024, 1, 15, 12, 2, 0);
        LocalDateTime end = LocalDateTimes.of(2024, 1, 15, 12, 0, 1);
        assertEquals(1, LocalDateTimes.betweenMinutes(start, end));
        assertEquals(2, LocalDateTimes.betweenMinutesTruncate(start, end));
    }

    @Test
    public void betweenHours() {
        LocalDateTime start = LocalDateTimes.of(2024, 1, 15, 12, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2024, 1, 15, 11, 1, 0);
        assertEquals(0, LocalDateTimes.betweenHours(start, end));
        assertEquals(1, LocalDateTimes.betweenHoursTruncate(start, end));
    }

    @Test
    public void betweenDays() {
        LocalDateTime start = LocalDateTimes.of(2024, 1, 16, 12, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2024, 1, 15, 13, 0, 0);
        assertEquals(0, LocalDateTimes.betweenDays(start, end));
        assertEquals(1, LocalDateTimes.betweenDaysTruncate(start, end));
    }

    @Test
    public void betweenDaysTruncate() {

        LocalDateTime start = LocalDateTimes.of(2024, 1, 26, 0, 0, 0);
        LocalDateTime end = LocalDateTimes.of(2024, 2, 1, 23, 59, 0);
        assertEquals(7, LocalDateTimes.betweenDaysTruncate(end, start));
    }

    @Test
    public void beginOfYear() {
        LocalDateTime ldt = LocalDateTime.of(2024, 1, 16, 0, 0, 0);
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0, 0), LocalDateTimeUtil.beginOfYear(ldt));
        LocalDate ld = LocalDate.of(2024, 2, 2);
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0, 0), LocalDateTimeUtil.beginOfYear(ld));
        LocalDateTime now = LocalDateTimeUtil.now();
        assertEquals(LocalDateTimeUtil.of(now.getYear(), 1, 1, 0, 0, 0),
                LocalDateTimeUtil.beginOfYear());
    }

    @Test
    public void dayOfMonth() {
        LocalDateTime now = LocalDateTimeUtil.now();
        assertEquals(now.getDayOfMonth(), LocalDateTimeUtil.dayOfMonth());
    }

    @Test
    public void startOfMonth() {
        LocalDateTime now = LocalDateTimeUtil.now();
        assertEquals(LocalDateTimes.of(now.getYear(), now.getMonthValue(), 1, 0, 0, 0), LocalDateTimes.startOfMonth());
    }

    @Test
    public void truncateToSecond() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 34, 56);
        ldt.with(ChronoField.MILLI_OF_SECOND, 789);
        System.out.println("ldt = " + ldt);
        assertEquals(LocalDateTime.of(2020, 1, 1, 12, 34, 56), LocalDateTimeUtil.truncateToSecond(ldt));
    }

    @Test
    public void truncateToMinutes() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 34, 56);
        ldt.with(ChronoField.MILLI_OF_SECOND, 789);
        System.out.println("ldt = " + ldt);
        LocalDateTime truncateToMinutes = LocalDateTimeUtil.truncateToMinutes(ldt);
        assertEquals(LocalDateTime.of(2020, 1, 1, 12, 34, 0), truncateToMinutes);
        System.out.println("truncateToMinutes = " + truncateToMinutes);
    }

}
