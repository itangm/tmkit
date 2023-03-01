package cn.tmkit.core.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * 日期时间计算器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class DateOperator {

    private transient LocalDateTime localDateTime;

    public DateOperator() {
        localDateTime = LocalDateTime.now();
    }

    public DateOperator(Date date) {
        Objects.requireNonNull(date, "date == null");
        localDateTime = LocalDateTimes.of(date);
    }

    public DateOperator(LocalDateTime localDateTime) {
        this.localDateTime = Objects.requireNonNull(localDateTime, "localDateTime == null");
    }

    public DateOperator(LocalDate localDate) {
        localDateTime = Objects.requireNonNull(localDate, "localDate == null").atStartOfDay();
    }

    public DateOperator(long epochMilli) {
        localDateTime = LocalDateTimes.of(epochMilli);
    }

    public DateOperator(int year, int month, int day, int hour, int minute, int second) {
        localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * 重新设置月份的第几天
     *
     * @param dayOfMonth 第几天
     * @return {@link DateOperator}
     */
    public DateOperator withDayOfMonth(int dayOfMonth) {
        localDateTime = localDateTime.withDayOfMonth(dayOfMonth);
        return this;
    }

    /**
     * 当前时间的月份的第一天
     *
     * @return {@link DateOperator}
     */
    public DateOperator firstDayOfMonth() {
        return withDayOfMonth(1);
    }

    /**
     * 当前时间的月份最后一天
     *
     * @return {@link DateOperator}
     */
    public DateOperator lastDayOfMonth() {
        localDateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return this;
    }

    /**
     * 对当前时间的调整
     *
     * @param adjuster 调整对象
     * @return {@link DateOperator}
     * @see TemporalAdjusters
     */
    public DateOperator with(TemporalAdjuster adjuster) {
        localDateTime = localDateTime.with(adjuster);
        return this;
    }

    /**
     * 给指定日期加（减）年份数
     *
     * @return {@link DateOperator}
     */
    public DateOperator years(int years) {
        localDateTime = localDateTime.plusYears(years);
        return this;
    }

    /**
     * 给指定日期加（减）月数
     *
     * @param months 月，如果为正整数则添加，否则相减
     * @return {@link DateOperator}
     */
    public DateOperator months(int months) {
        localDateTime = localDateTime.plusMonths(months);
        return this;
    }

    /**
     * 给指定日期加（减）天数
     *
     * @param days 天，如果为正整数则添加，否则相减
     * @return {@link DateOperator}
     */
    public DateOperator days(int days) {
        localDateTime = localDateTime.plusDays(days);
        return this;
    }

    /**
     * 给指定日期加（减）小时数
     *
     * @param hours 小时，如果为正整数则添加，否则相减
     * @return {@link DateOperator}
     */
    public DateOperator hours(int hours) {
        localDateTime = localDateTime.plusHours(hours);
        return this;
    }

    /**
     * 给指定日期加（减）分钟数
     *
     * @param minutes 分钟，如果为正整数则添加，否则相减
     * @return {@link DateOperator}
     */
    public DateOperator minutes(int minutes) {
        localDateTime = localDateTime.plusMinutes(minutes);
        return this;
    }

    /**
     * 给指定日期加（减）秒数
     *
     * @param seconds 秒，如果为正整数则添加，否则相减
     * @return {@link DateOperator}
     */
    public DateOperator seconds(int seconds) {
        localDateTime = localDateTime.plusSeconds(seconds);
        return this;
    }

    /**
     * 返回秒
     *
     * @return 秒
     */
    public int getSecond() {
        return localDateTime.getSecond();
    }

    /**
     * 返回分钟
     *
     * @return 分钟
     */
    public int getMinute() {
        return localDateTime.getMinute();
    }

    /**
     * 返回小时
     *
     * @return 小时
     */
    public int getHour() {
        return localDateTime.getHour();
    }

    /**
     * 返回几号
     *
     * @return 天（几号）
     */
    public int getDay() {
        return localDateTime.getDayOfMonth();
    }

    /**
     * 返回月份值
     *
     * @return 月份值
     */
    public int getMonthValue() {
        return localDateTime.getMonthValue();
    }

    /**
     * 返回月份
     *
     * @return 月份
     */
    public Month getMonth() {
        return Month.of(getMonthValue());
    }

    /**
     * 返回周
     *
     * @return 周
     */
    public Week getWeek() {
        return Week.of(localDateTime.getDayOfWeek().getValue());
    }

    /**
     * 返回年
     *
     * @return 年份
     */
    public int getYear() {
        return localDateTime.getYear();
    }

    /**
     * 转为{@linkplain Date}类型的日期
     *
     * @return 日期
     */
    public Date toDate() {
        return LocalDateTimes.toDate(localDateTime);
    }

    /**
     * 转为{@linkplain LocalDate}类型的日期
     *
     * @return 日期
     */
    public LocalDate toLocalDate() {
        return toLocalDateTime().toLocalDate();
    }

    /**
     * 转为{@linkplain LocalDateTime}类型的日期时间
     *
     * @return 日期时间
     */
    public LocalDateTime toLocalDateTime() {
        return localDateTime;
    }

    /**
     * 格式化日期时间
     *
     * @param pattern 格式化规则
     * @return 日期时间字符串
     */
    public String format(String pattern) {
        return LocalDateTimes.format(localDateTime, pattern);
    }

    /**
     * 格式化日期时间
     *
     * @param formatter 格式化规则
     * @return 日期时间字符串
     */
    public String format(DateTimeFormatter formatter) {
        return LocalDateTimes.format(localDateTime, formatter);
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之前
     *
     * @param other 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isBefore(LocalDateTime other) {
        return localDateTime.isBefore(Objects.requireNonNull(other, "other == null"));
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之后
     *
     * @param other 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isAfter(LocalDateTime other) {
        return localDateTime.isAfter(Objects.requireNonNull(other, "other == null"));
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之钱
     *
     * @param target 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isBefore(DateOperator target) {
        return this.localDateTime.isBefore(Objects.requireNonNull(target, "other == null").localDateTime);
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之后
     *
     * @param target 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isAfter(DateOperator target) {
        return this.localDateTime.isAfter(Objects.requireNonNull(target, "other == null").localDateTime);
    }

}
