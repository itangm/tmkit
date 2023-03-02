package cn.tmkit.core.date;

import java.time.format.DateTimeFormatter;

/**
 * 时间格式化定义
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public interface CustomFormatter {

    /**
     * 日期格式化的字符串
     *
     * @return 日期格式化的字符串
     */
    String getPattern();

    /**
     * 日期格式化的字符串
     *
     * @return {@linkplain DateTimeFormatter}
     */
    DateTimeFormatter getFormatter();

    /**
     * 标准日期格式 yyyy-MM-dd
     */
    String NORMAL_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 瘦身日期格式 yyyy-M-d
     */
    String SLIM_NORMAL_DATE_PATTERN = "yyyy-M-d";

    /**
     * 标准时间格式 HH:mm:ss
     */
    String NORMAL_TIME_PATTERN = "HH:mm:ss";

    /**
     * 瘦身时间格式 H:m:s
     */
    String SLIM_NORMAL_TIME_PATTERN = "H:m:s";

    /**
     * 标准日期时间格式，精确到分 yyyy-MM-dd HH:mm
     */
    String NORMAL_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期时间格式，精确到秒 yyyy-MM-dd HH:mm:ss
     */
    String NORMAL_DATETIME_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 智能的日期时间格式：yyyy-M-d H:m:s
     */
    String SLIM_NORMAL_DATETIME_PATTERN = "yyyy-M-d H:m:s";

    /**
     * 标准日期时间格式，精确到毫秒 yyyy-MM-dd HH:mm:ss.SSS
     */
    String NORMAL_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 标准日期格式 yyyyMMdd
     */
    String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 标准日期格式：HHmmss
     */
    String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 中文日期格式：yyyy年MM月dd日
     */
    String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    String UTC_MS_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

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
    String SMART_NORMAL_TIME_PATTERN = "H:m[:s]";

}
