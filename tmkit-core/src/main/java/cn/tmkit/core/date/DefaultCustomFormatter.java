package cn.tmkit.core.date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * 常见的日期格式化常量
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
@Getter
@RequiredArgsConstructor
public enum DefaultCustomFormatter implements CustomFormatter {

    /**
     * 日期时间的CRON表达式
     */
    CRON_DATE(CRON_DATE_PATTERN, DateTimeFormatter.ofPattern(CRON_DATE_PATTERN)),

    /**
     * 标准日期格式 yyyy-MM-dd
     */
    NORMAL_DATE(NORMAL_DATE_PATTERN, DateTimeFormatter.ofPattern(NORMAL_DATE_PATTERN)),

    /**
     * 智能日期格式 yyyy-M-d
     */
    SLIM_NORMAL_DATE(SLIM_NORMAL_DATE_PATTERN, DateTimeFormatter.ofPattern(SLIM_NORMAL_DATE_PATTERN)),

    /**
     * 标准时间格式 {@link DateTimeFormatter} HH:mm:ss
     */
    NORMAL_TIME(NORMAL_TIME_PATTERN, DateTimeFormatter.ofPattern(NORMAL_TIME_PATTERN)),

    /**
     * 标准时间格式 {@link DateTimeFormatter} HH:mm
     */
    NORMAL_MINUTE(NORMAL_MINUTE_PATTERN, DateTimeFormatter.ofPattern(NORMAL_MINUTE_PATTERN)),

    /**
     * 瘦身时间格式 H:m:s
     */
    SLIM_NORMAL_TIME(SLIM_NORMAL_TIME_PATTERN, DateTimeFormatter.ofPattern(SLIM_NORMAL_TIME_PATTERN)),


    /**
     * 标准日期时间格式，精确到分 {@link DateTimeFormatter} yyyy-MM-dd HH:mm
     */
    NORMAL_DATETIME_MINUTE(NORMAL_DATETIME_MINUTE_PATTERN, DateTimeFormatter.ofPattern(NORMAL_DATETIME_MINUTE_PATTERN)),

    /**
     * 标准日期时间格式，精确到秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss
     */
    NORMAL_DATETIME_FULL(NORMAL_DATETIME_FULL_PATTERN, DateTimeFormatter.ofPattern(NORMAL_DATETIME_FULL_PATTERN)),

    /**
     * 智能的日期时间格式：yyyy-M-d H:m:s
     */
    SLIM_NORMAL_DATETIME(SLIM_NORMAL_DATETIME_PATTERN, DateTimeFormatter.ofPattern(SLIM_NORMAL_DATETIME_PATTERN)),

    /**
     * 标准日期时间格式，精确到毫秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss.SSS
     */
    NORMAL_DATETIME_MS(NORMAL_DATETIME_MS_PATTERN, DateTimeFormatter.ofPattern(NORMAL_DATETIME_MS_PATTERN)),

    /**
     * 标准日期格式 {@link DateTimeFormatter} yyyyMMdd
     */
    PURE_DATE(PURE_DATE_PATTERN, DateTimeFormatter.ofPattern(PURE_DATE_PATTERN)),

    /**
     * 标准日期格式 {@link DateTimeFormatter}：HHmmss
     */
    PURE_TIME(PURE_TIME_PATTERN, DateTimeFormatter.ofPattern(PURE_TIME_PATTERN)),

    /**
     * 标准日期格式 {@link DateTimeFormatter}：HHmm
     */
    PURE_MINUTE(PURE_MINUTE_PATTERN, DateTimeFormatter.ofPattern(PURE_MINUTE_PATTERN)),

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmss
     */
    PURE_DATETIME(PURE_DATETIME_PATTERN, DateTimeFormatter.ofPattern(PURE_DATETIME_PATTERN)),

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmssSSS
     */
    PURE_DATETIME_MS(PURE_DATETIME_MS_PATTERN, DateTimeFormatter.ofPattern(PURE_DATETIME_MS_PATTERN)),

    /**
     * 中文日期格式：yyyy年MM月dd日
     */
    CHINESE_DATE(CHINESE_DATE_PATTERN, DateTimeFormatter.ofPattern(CHINESE_DATE_PATTERN)),

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    UTC_SIMPLE(UTC_SIMPLE_PATTERN, DateTimeFormatter.ofPattern(UTC_SIMPLE_PATTERN)),

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    UTC_MS_SIMPLE(UTC_MS_SIMPLE_PATTERN, DateTimeFormatter.ofPattern(UTC_MS_SIMPLE_PATTERN)),

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    UTC(UTC_PATTERN, DateTimeFormatter.ofPattern(UTC_PATTERN)),

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    UTC_MS(UTC_MS_PATTERN, DateTimeFormatter.ofPattern(UTC_MS_PATTERN)),

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    UTC_MS_WITH_ZONE_OFFSET(UTC_MS_WITH_ZONE_OFFSET_PATTERN, new DateTimeFormatterBuilder()
            .appendPattern(UTC_MS_WITH_ZONE_OFFSET_PATTERN)
            .appendZoneId().toFormatter()),

    /**
     * 智能时间格式 H:m[:s]
     * 可解析如下字符串
     * 2:3
     * 2:30
     * 12:30
     * 12:3
     * 12:03
     * 12:03:1
     * 12:03:11
     */
    SMART_NORMAL_TIME(SMART_NORMAL_TIME_PATTERN, DateTimeFormatter.ofPattern(SMART_NORMAL_TIME_PATTERN)),

    ;

    private final String pattern;

    private final DateTimeFormatter formatter;

}
