package cn.tmkit.core.date;

import cn.tmkit.core.lang.*;
import cn.tmkit.core.lang.regex.ExtraPatternConstant;
import cn.tmkit.core.lang.regex.PatternPool;
import cn.tmkit.core.lang.regex.Regexes;
import org.jetbrains.annotations.NotNull;

import java.time.Month;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Date;
import java.util.TimeZone;

/**
 * {@linkplain LocalDate}、{@linkplain LocalDateTime}、{@linkplain LocalTime}等工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
@SuppressWarnings("ConstantConditions")
public class LocalDateTimes {

    // region Date And Time Constant

    /**
     * 一天最大的时间，也就是23:59:59，与官方API的区别在于不考虑纳秒的情况
     */
    public static final LocalTime MAX_TIME = LocalTime.of(23, 59, 59);

    // endregion

    // region ============ Create LocalDate ============

    /**
     * 当前日期，默认时区
     *
     * @return {@link LocalDate}
     */
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * {@link Date}转{@link LocalDate}，使用默认时区
     *
     * @param date Date对象
     * @return {@link LocalDate}
     */
    public static LocalDate ofDate(Date date) {
        if (date == null) {
            return null;
        }
        return of(date.toInstant()).toLocalDate();
    }

    /**
     * 创建{@linkplain LocalDate}
     *
     * @param year       年，哪一年
     * @param month      月，一年的地几个月
     * @param dayOfMonth 日，一个月的第几天
     * @return {@link LocalDate}
     */
    public static LocalDate ofDate(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    // endregion

    // region ============ Create LocalDateTime ============

    /**
     * 当前日期时间，默认时区
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 当前的日期时间，忽略毫秒数
     * <p>MySQL针对带有毫秒数的日期时间可能会发生提升1秒的问题，当毫秒数超出500时。通过本方法放回的当前日期时间舍去毫秒数</p>
     *
     * @return 日期时间，舍去毫秒数
     */
    public static LocalDateTime nowRoundingMills() {
        return truncateToSecond(now());
    }

    /**
     * 当前的日期时间，忽略毫秒数
     * <p>MySQL针对带有毫秒数的日期时间可能会发生提升1秒的问题，当毫秒数超出500时。通过本方法放回的当前日期时间舍去毫秒数</p>
     *
     * @return 日期时间，舍去毫秒数
     */
    public static LocalDateTime nowWithoutMills() {
        return nowRoundingMills();
    }

    /**
     * {@link Date}转{@link LocalDateTime}，使用默认时区
     *
     * @param date Date对象
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        return of(date.toInstant());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用默认时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant) {
        return of(instant, ZoneIdConstant.SYSTEM_DEFAULT);
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用亚洲上海时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime ofUtc8(Instant instant) {
        return of(instant, ZoneIdConstant.BEIJING_ZONE_OFFSET);
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant {@link Instant}
     * @param zoneId  时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, ZoneId zoneId) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, Objects.getIfNull(zoneId, ZoneId.systemDefault()));
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant  {@link Instant}
     * @param timeZone 时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, TimeZone timeZone) {
        if (instant == null) {
            return null;
        }
        return of(instant, Objects.getIfNull(timeZone, TimeZone::getDefault).toZoneId());
    }

    /**
     * 毫秒数转日期时间对象{@linkplain LocalDateTime}，采用默认时区
     *
     * @param epochMilli 毫秒数，从1970-01-01 00:00:00开始
     * @return 日期时间对象
     */
    public static LocalDateTime of(final long epochMilli) {
        Asserts.isTrue(epochMilli >= 0, "epochMilli >= 0");
        return of(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * 毫秒数转日期时间对象{@linkplain LocalDateTime}，采用东八区
     *
     * @param epochMilli 毫秒数，从1970-01-01 00:00:00开始
     * @return 日期时间对象
     */
    public static LocalDateTime ofUtc8(final long epochMilli) {
        Asserts.isTrue(epochMilli >= 0, "epochMilli >= 0");
        return of(Instant.ofEpochMilli(epochMilli), ZoneIdConstant.UTC8);
    }

    /**
     * {@link ZonedDateTime}转{@link LocalDateTime}
     *
     * @param zonedDateTime {@link ZonedDateTime}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime of(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay();
    }

    /**
     * {@link TemporalAccessor}转{@link LocalDateTime}，使用默认时区
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        } else if (temporalAccessor instanceof LocalDate) {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        } else if (temporalAccessor instanceof LocalDateTime) {
            return (LocalDateTime) temporalAccessor;
        } else if (temporalAccessor instanceof LocalTime) {
            return LocalDate.now().atTime((LocalTime) temporalAccessor);
        } else {
            return LocalDateTime.of(
                    get(temporalAccessor, ChronoField.YEAR), get(temporalAccessor, ChronoField.MONTH_OF_YEAR),
                    get(temporalAccessor, ChronoField.DAY_OF_MONTH), get(temporalAccessor, ChronoField.HOUR_OF_DAY),
                    get(temporalAccessor, ChronoField.MINUTE_OF_HOUR),
                    get(temporalAccessor, ChronoField.SECOND_OF_MINUTE),
                    get(temporalAccessor, ChronoField.NANO_OF_SECOND));
        }
    }

    /**
     * 创建{@linkplain LocalDateTime}
     *
     * @param year       年，哪一年
     * @param month      月，一年的地几个月
     * @param dayOfMonth 日，一个月的第几天
     * @param hour       小时，一天的几点，24小时制
     * @param minute     分钟，一小时的第几分钟
     * @param second     秒，一分钟的第几秒
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, 0);
    }

    // endregion

    // region ============ Create LocalTime ============

    /**
     * 当前时间，默认时区
     *
     * @return {@link LocalTime}
     */
    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    // endregion

    // region ============ Parse ============

    /**
     * <p>如果确定格式不推荐使用该方法，直接使用{@linkplain #parse(CharSequence, DateTimeFormatter)}</p>
     * 会尽量解析日期字符串，适用于不太确定输入的日期格式，支持如下格式：<br>
     * <ol>
     *     <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     *     <li>yyyy-MM-dd HH:mm:ss</li>
     *     <li>yyyy-MM-dd HH:mm</li>
     *     <li>yyyy/MM/dd HH:mm:ss</li>
     *     <li>yyyy.MM.dd HH:mm:ss</li>
     *     <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyy/MM/dd</li>
     *     <li>yyyy.MM.dd</li>
     *     <li>yyyyMMddHHmmss</li>
     *     <li>yyyyMMddHHmmssSSS</li>
     *     <li>yyyyMMdd</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param text 日期字符串
     * @return 日期
     */
    @SuppressWarnings("DuplicatedCode")
    public static LocalDateTime parse(CharSequence text) {
        if (Strings.isBlank(text)) {
            return null;
        }
        String dateStr = Strings.trim(text);
        int len = dateStr.length();
        if (Strings.contains(dateStr, 'T')) {
            return parseUtc(text);
        } else if (NumberUtil.isDigits(text)) {
            if (len == CustomFormatter.PURE_DATETIME_MS_PATTERN.length()) {
                return parse(dateStr, CustomFormatter.PURE_DATETIME_MS_PATTERN);
            } else if (len == CustomFormatter.PURE_DATETIME_PATTERN.length()) {
                return parse(dateStr, CustomFormatter.PURE_DATETIME_PATTERN);
            } else if (len == CustomFormatter.PURE_DATE_PATTERN.length()) {
                return parse(dateStr, CustomFormatter.PURE_DATE_PATTERN);
            } else if (len == CustomFormatter.PURE_TIME_PATTERN.length()) {
                return parse(dateStr, CustomFormatter.PURE_TIME_PATTERN);
            }
        } else if (Regexes.isMatch(PatternPool.TIME, dateStr)) {
            // HH:mm:ss 或者 HH:mm 时间格式匹配单独解析
            return LocalDateTime.of(nowDate(), LocalTime.parse(dateStr, CustomFormatterCache.ofPattern(CustomFormatter.SMART_NORMAL_TIME_PATTERN)));
        }

        //规范化日期格式（包括单个数字的日期时间）
        dateStr = DateUtil.normalize(dateStr);
        if (Regexes.isMatch(ExtraPatternConstant.REGEX_NORM, dateStr)) {
            int colonCount = Strings.count(dateStr, Chars.COLON);
            if (colonCount == 0) {
                // yyyy-MM-dd
                return parse(dateStr, CustomFormatter.NORMAL_DATE_PATTERN);
            } else if (colonCount == 1) {
                // yyyy-MM-dd HH:mm
                return parse(dateStr, CustomFormatter.NORMAL_DATETIME_MINUTE_PATTERN);
            } else if (colonCount == 2) {
                if (Strings.contains(dateStr, Chars.DOT)) {
                    // yyyy-MM-dd HH:mm:ss.SSS
                    return parse(dateStr, CustomFormatter.NORMAL_DATETIME_MS_PATTERN);
                } else {
                    // yyyy-MM-dd HH:mm:ss
                    return parse(dateStr, CustomFormatter.NORMAL_DATETIME_FULL_PATTERN);
                }
            }
        }

        return null;
    }

    private static LocalDateTime parseUtc(CharSequence text) {
        if (Strings.isBlank(text)) {
            return null;
        }
        return LocalDateTime.parse(text);
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        if (Strings.isBlank(text)) {
            return null;
        }
        if (formatter == null) {
            return LocalDateTime.parse(text);
        }
        return of(formatter.parse(text));
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，格式支持日期时间、日期、时间
     *
     * @param text            日期时间字符串
     * @param customFormatter 日期格式化器，如果为{@code null}默认为{@linkplain DefaultCustomFormatter#NORMAL_DATETIME_FULL}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, CustomFormatter customFormatter) {
        CustomFormatter cf = customFormatter == null ? DefaultCustomFormatter.NORMAL_DATETIME_FULL : customFormatter;
        return parse(text, cf.getFormatter());
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd HH:mm:ss
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, String pattern) {
        if (Strings.isBlank(text)) {
            return null;
        }
        DateTimeFormatter formatter = null;
        if (Strings.hasText(pattern)) {
            // 修复yyyyMMddHHmmssSSS格式不能解析的问题
            //see https://stackoverflow.com/questions/22588051/is-java-time-failing-to-parse-fraction-of-second
            if (Strings.startsWithIgnoreCase(pattern, DefaultCustomFormatter.PURE_DATETIME_PATTERN)) {
                String fraction = Strings.deletePrefix(pattern, DefaultCustomFormatter.PURE_DATETIME_PATTERN);
                if (Regexes.isMatch("[S]{1,2}", fraction)) {
                    //将yyyyMMddHHmmssS、yyyyMMddHHmmssSS的日期统一替换为yyyyMMddHHmmssSSS格式，用0补
                    text += Strings.repeat('0', 3 - fraction.length());
                }
                formatter = new DateTimeFormatterBuilder()
                        .append(DefaultCustomFormatter.PURE_DATETIME.getFormatter())
                        .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                        .toFormatter();
            } else {
                formatter = CustomFormatterCache.ofPattern(pattern);
            }
        }
        return parse(text, formatter);
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd
     * @return {@link LocalDate}
     */
    public static LocalDate parseToDate(CharSequence text, String pattern) {
        LocalDateTime ldt = parse(text, pattern);
        return (ldt == null) ? null : ldt.toLocalDate();
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDate}
     */
    public static LocalDate parseToDate(CharSequence text, DateTimeFormatter formatter) {
        LocalDateTime ldt = parse(text, formatter);
        return (null == ldt) ? null : ldt.toLocalDate();
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}，格式支持日期时间、日期、时间
     *
     * @param text            日期时间字符串
     * @param customFormatter 日期格式化器，如果为{@code null}默认为{@linkplain DefaultCustomFormatter#NORMAL_DATE}
     * @return {@link LocalDate}
     */
    public static LocalDate parseToDate(CharSequence text, CustomFormatter customFormatter) {
        CustomFormatter cf = customFormatter == null ? DefaultCustomFormatter.NORMAL_DATE : customFormatter;
        return parseToDate(text, cf.getFormatter());
    }

    /**
     * 解析时间，如果{@code pattern}为空则默认值为{@linkplain DefaultCustomFormatter#NORMAL_TIME}
     *
     * @param text    时间字符串
     * @param pattern 时间格式，类似于<code>HH:mm:ss</code>
     * @return {@linkplain LocalTime}
     */
    public static LocalTime parseToTime(CharSequence text, String pattern) {
        if (Strings.isBlank(text)) {
            return null;
        }
        DateTimeFormatter dtf = Strings.isBlank(pattern) ? DefaultCustomFormatter.NORMAL_TIME.getFormatter() :
                CustomFormatterCache.ofPattern(pattern);
        return LocalTime.parse(text, dtf);
    }

    /**
     * 解析时间，如果{@code pattern}为空则默认值为{@linkplain DefaultCustomFormatter#NORMAL_TIME}
     *
     * @param text            时间字符串
     * @param customFormatter 时间格式
     * @return {@linkplain LocalTime}
     */
    public static LocalTime parseToTime(CharSequence text, CustomFormatter customFormatter) {
        if (Strings.isBlank(text)) {
            return null;
        }
        CustomFormatter cf = (customFormatter == null) ? DefaultCustomFormatter.NORMAL_TIME : customFormatter;
        return LocalTime.parse(text, cf.getFormatter());
    }

    // endregion

    // region ============ Format ============

    /**
     * 对当前日期时间格式化，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    public static String format() {
        return format(now());
    }

    /**
     * 对当前日期时间采用指定格式格式化
     *
     * @param pattern 格式，为空时默认采用格式：yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(String pattern) {
        return format(now(), pattern);
    }

    /**
     * 对当前日期时间采用指定格式化器格式化
     *
     * @param formatter 格式化器，为空默认采用 yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(@NotNull CustomFormatter formatter) {
        return format(now(), formatter);
    }

    /**
     * 对当前日期时间采用指定格式化器格式化
     *
     * @param formatter 格式化器，为空默认采用 yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(DateTimeFormatter formatter) {
        return format(now(), formatter);
    }

    /**
     * 格式化日期时间为yyyy-MM-dd HH:mm:ss格式
     *
     * @param value 日期时间
     * @return 格式化后的字符串或{@code null}
     */
    public static String format(LocalDateTime value) {
        return format(value, DefaultCustomFormatter.NORMAL_DATETIME_FULL);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param value   日期
     * @param pattern 格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDateTime value, String pattern) {
        if (value == null) {
            return null;
        }
        if (Strings.isBlank(pattern)) {
            return format(value, DefaultCustomFormatter.NORMAL_DATETIME_FULL);
        }
        return format(value, CustomFormatterCache.ofPattern(pattern));
    }

    /**
     * 按指定格式格式化日期
     *
     * @param value     日期
     * @param formatter 格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDateTime value, DateTimeFormatter formatter) {
        if (value == null) {
            return null;
        }
        if (formatter == null) {
            return format(value, DefaultCustomFormatter.NORMAL_DATETIME_FULL);
        }
        return value.format(formatter);
    }

    /**
     * 按照指定格式风格格式化日期
     *
     * @param value           日期
     * @param customFormatter 自定义格式风格
     * @return 格式化后的字符粗
     */
    public static String format(LocalDateTime value, @NotNull CustomFormatter customFormatter) {
        if (Objects.isNull(value)) {
            return null;
        }
        return customFormatter.getFormatter().format(value);
    }

    /**
     * 格式化日期时间为yyyy-MM-dd格式
     *
     * @param localDate 日期
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate localDate) {
        return format(localDate, DefaultCustomFormatter.NORMAL_DATE);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param value   日期
     * @param pattern 格式，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate value, String pattern) {
        if (value == null) {
            return null;
        } else if (Strings.isBlank(pattern)) {
            return format(value, DefaultCustomFormatter.NORMAL_DATE);
        } else {
            return format(value, CustomFormatterCache.ofPattern(pattern));
        }
    }

    /**
     * 按指定格式格式化日期
     *
     * @param value     日期
     * @param formatter 格式化器，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate value, DateTimeFormatter formatter) {
        if (value == null) {
            return null;
        } else if (formatter == null) {
            return format(value, DefaultCustomFormatter.NORMAL_DATE);
        } else {
            return value.format(formatter);
        }
    }

    /**
     * 按指定格式格式化日期
     *
     * @param value 日期
     * @param cf    自定义格式化器
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate value, @NotNull CustomFormatter cf) {
        if (value == null) {
            return null;
        }
        return value.format(cf.getFormatter());
    }

    // endregion

    /**
     * 将{@code LocalDateTime}转为时间戳
     *
     * @param dateTime 时间
     * @return 时间戳
     */
    public static long toEpochMilli(LocalDateTime dateTime) {
        return toInstant(dateTime).toEpochMilli();
    }

    /**
     * p
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param localDateTime 日期时间
     * @return 时刻
     */
    public static Instant toInstant(LocalDateTime localDateTime) {
        return toInstant(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     *
     * @param localDateTime 日期时间
     * @param zoneId        指定时区
     * @return 时刻
     */
    public static Instant toInstant(LocalDateTime localDateTime, ZoneId zoneId) {
        if (Objects.isAllNull(localDateTime, zoneId)) {
            return null;
        }
        return localDateTime.atZone(Objects.getIfNull(zoneId, ZoneIdConstant.UTC8)).toInstant();
    }

    /**
     * 将{@linkplain ZonedDateTime}转为{@linkplain Instant}
     *
     * @param zdt {@linkplain ZonedDateTime}对象
     * @return {@linkplain Instant}对象
     */
    public static Instant toInstant(ZonedDateTime zdt) {
        return zdt.toInstant();
    }

    /**
     * 将{@linkplain TemporalAccessor}转为{@linkplain Instant}
     *
     * @param ta {@linkplain TemporalAccessor}
     * @return {@linkplain Instant}
     */
    public static Instant toInstant(TemporalAccessor ta) {
        if (ta == null) {
            return null;
        } else if (ta instanceof Instant) {
            return (Instant) ta;
        } else if (ta instanceof LocalDateTime) {
            return toInstant((LocalDateTime) ta);
        } else if (ta instanceof ZonedDateTime) {
            return toInstant((ZonedDateTime) ta);
        } else if (ta instanceof OffsetDateTime) {
            return ((OffsetDateTime) ta).toInstant();
        } else if (ta instanceof LocalDate) {
            return toInstant(((LocalDate) ta).atStartOfDay());
        } else if (ta instanceof LocalTime) {
            return toInstant(((LocalTime) ta).atDate(LocalDate.now()));
        } else if (ta instanceof OffsetTime) {
            return ((OffsetTime) ta).atDate(LocalDate.now()).toInstant();
        } else {
            return Instant.from(ta);
        }
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}，采用默认时区
     *
     * @param localDateTime 日期时间
     * @return {@linkplain Date}
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}
     *
     * @param localDateTime 日期时间
     * @param zoneOffset    时区
     * @return {@linkplain Date}
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(Objects.getIfNull(zoneOffset, ZoneIdConstant.DEFAULT_ZONE_OFFSET)));
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15 22:10:00  LocalDateTimeUtil.isLeapYear(localDateTime); false
     * </pre>
     *
     * @param localDateTime 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(LocalDateTime localDateTime) {
        return (localDateTime != null && isLeapYear(localDateTime.toLocalDate()));
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15  LocalDateTimeUtil.isLeapYear(localDate); false
     * </pre>
     *
     * @param localDate 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(LocalDate localDate) {
        if (localDate == null) {
            return false;
        }
        return localDate.isLeapYear();
    }

    /**
     * 获取两个日期的差，其结果值为正整数或、0或{@code null}<br>
     * 返回结果为{@link Duration}对象，通过调用toXXX方法返回相差单位
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 时间差 {@link Duration}对象
     */
    public static Duration between(LocalDateTime start, LocalDateTime end) {
        return between(start, end, true);
    }

    /**
     * 获取两个日期的差，返回结果为{@link Duration}对象，通过调用toXXX方法返回相差单位<br>
     * 如果开始时间小于结束时间则返回负数
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 时间差 {@link Duration}对象
     */
    public static Duration between(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Arrays.isAnyNull(start, end)) {
            return null;
        }
        if (isAbsolute) {
            if (start.isAfter(end)) {
                return Duration.between(end, start);
            }
            return Duration.between(start, end);
        }
        return Duration.between(end, start);
    }

    /**
     * 返回两者之间相差的毫秒数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的毫秒数
     */
    public static long betweenMills(LocalDateTime start, LocalDateTime end) {
        return betweenMills(start, end, true);
    }

    /**
     * 返回两者之间相差的毫秒数，如果{@code start}小于{@code end}负数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 两者之间相差的毫秒数
     */
    public static long betweenMills(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Objects.isAnyNull(start, end)) {
            return 0;
        }
        return between(start, end, isAbsolute).toMillis();
    }

    /**
     * 返回两者之间相差的秒数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的秒数
     */
    public static int betweenSeconds(LocalDateTime start, LocalDateTime end) {
        return betweenSeconds(start, end, false);
    }

    /**
     * 返回两者之间相差的秒数，如果{@code start}小于{@code end}则为负数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 两者之间相差的秒数
     */
    public static int betweenSeconds(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Objects.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(between(start, end, isAbsolute).getSeconds());
    }

    /**
     * 返回两者之间相差的秒数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的秒数
     */
    public static int betweenSecondsTruncate(LocalDateTime start, LocalDateTime end) {
        return betweenSecondsTruncate(start, end, false);
    }

    /**
     * 返回两者之间相差的秒数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的秒数
     */
    public static int betweenSecondsTruncate(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        return betweenSeconds(truncateToSecond(start), truncateToSecond(end), isAbsolute);
    }

    /**
     * 返回两者之间相差的分钟数，如果{@code start}小于{@code end}则为负数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的分钟数
     */
    public static int betweenMinutes(LocalDateTime start, LocalDateTime end) {
        return betweenMinutes(start, end, false);
    }

    /**
     * 返回两者之间相差的分钟数，本方法会截断（重置）秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-15 12:02:00， {@code end} = 2024-01-15 12:00:01，那么本方法得到的分钟值为2
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的分钟数
     * @see #betweenMinutes(LocalDateTime, LocalDateTime)
     */
    public static int betweenMinutesTruncate(LocalDateTime start, LocalDateTime end) {
        return betweenMinutesTruncate(start, end, false);
    }

    /**
     * 返回两者之间相差的分钟数，本方法会截断（重置）秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-15 12:02:00， {@code end} = 2024-01-15 12:00:01，那么本方法得到的分钟值为2
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的分钟数
     * @see #betweenMinutes(LocalDateTime, LocalDateTime)
     */
    public static int betweenMinutesTruncate(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        return betweenMinutes(truncateToMinutes(start), truncateToMinutes(end), isAbsolute);
    }

    /**
     * 返回两者之间相差的分钟数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 两者之间相差的分钟数
     */
    public static int betweenMinutes(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Objects.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(between(start, end, isAbsolute).toMinutes());
    }

    /**
     * 返回两者之间相差的小时数，如果{@code start}小于{@code end}负数
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2019-01-01 12:00:02， {@code end} = 2019-01-01 13:02:01，那么本方法得到的分钟值为1
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的小时数
     */
    public static int betweenHours(LocalDateTime start, LocalDateTime end) {
        return betweenHours(start, end, false);
    }

    /**
     * 返回两者之间相差的小时数，本方法会截断（重置）分钟、秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-15 12:00:00， {@code end} = 2024-01-15 11:01:00，那么本方法得到的小时数值为1
     * 如果{@code start}小于{@code end}负数，如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的小时数
     * @see #betweenMinutes(LocalDateTime, LocalDateTime)
     */
    public static int betweenHoursTruncate(LocalDateTime start, LocalDateTime end) {
        return betweenHoursTruncate(start, end, false);
    }

    /**
     * 返回两者之间相差的小时数，本方法会截断（重置）分钟、秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-15 12:00:00， {@code end} = 2024-01-15 11:01:00，那么本方法得到的小时数值为1
     * 如果{@code start}小于{@code end}负数，如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的小时数
     * @see #betweenMinutes(LocalDateTime, LocalDateTime)
     */
    public static int betweenHoursTruncate(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        return betweenHours(truncateTo(start, ChronoUnit.HOURS), truncateTo(end, ChronoUnit.HOURS), isAbsolute);
    }

    /**
     * 返回两者之间相差的小时数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 两者之间相差的小时数
     */
    public static int betweenHours(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Objects.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(between(start, end, isAbsolute).toHours());
    }

    // region 计算相差的天数

    /**
     * 返回两者之间相差的天数，如果{@code start}小于{@code end}负数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的天数
     */
    public static int betweenDays(LocalDateTime start, LocalDateTime end) {
        return betweenDays(start, end, false);
    }

    /**
     * 返回两者之间相差的天数，本方法会截断（重置）小时、分钟、秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-16 12:00:00， {@code end} = 2024-01-15 132:00:00，那么本方法得到的天数值为1
     * 如果{@code start}小于{@code end}负数，如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的天数
     * @see #betweenDays(LocalDateTime, LocalDateTime)
     */
    public static int betweenDaysTruncate(LocalDateTime start, LocalDateTime end) {
        return betweenDaysTruncate(start, end, false);
    }

    /**
     * 返回两者之间相差的天数，本方法会截断（重置）小时、分钟、秒、毫秒。
     * 举个例子更为直观的说明：
     * 如果 {@code start} = 2024-01-16 12:00:00， {@code end} = 2024-01-15 132:00:00，那么本方法得到的天数值为1
     * 如果{@code start}小于{@code end}负数，如果{@code start}或{@code end}为{@code null}则返回{@code 0}
     *
     * @param start 开始时间（包含）
     * @param end   结束时间（不包含）
     * @return 两者之间相差的天数
     * @see #betweenDays(LocalDateTime, LocalDateTime)
     */
    public static int betweenDaysTruncate(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        return betweenDays(truncateTo(start, ChronoUnit.DAYS), truncateTo(end, ChronoUnit.DAYS), isAbsolute);
    }

    /**
     * 返回两者之间相差的天数
     * 如果{@code start}或{@code end}为{@code null}则返回{@code 0}，否则其值为0或正整数
     *
     * @param start      开始时间（包含）
     * @param end        结束时间（不包含）
     * @param isAbsolute 结果值是否为正数
     * @return 两者之间相差的天数
     */
    public static int betweenDays(LocalDateTime start, LocalDateTime end, boolean isAbsolute) {
        if (Objects.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(between(start, end, isAbsolute).toDays());
    }

    /**
     * 计算两个日期之间的相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenDays(Temporal start, Temporal end) {
        if (Arrays.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(ChronoUnit.DAYS.between(start, end));
    }

    /**
     * 获取从开始时间到结束时间一共有几天。
     * <p>本方法和{@linkplain #betweenDaysTruncate(LocalDateTime, LocalDateTime, boolean)}的结果相差1天</p>
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 一共有几天
     * @see #betweenDaysTruncate(LocalDateTime, LocalDateTime, boolean)
     * @see #getTotalDays(LocalDate, LocalDate)
     */
    public static int getTotalDays(LocalDateTime start, LocalDateTime end) {
        if (Arrays.isAnyNull(start, end)) {
            return 0;
        }
        return getTotalDays(start.toLocalDate(), end.toLocalDate());
    }

    /**
     * 获取从开始时间到结束时间一共有几天。
     * <p>本方法和{@linkplain #betweenDaysTruncate(LocalDateTime, LocalDateTime, boolean)}的结果相差1天</p>
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 一共有几天
     * @see #betweenDaysTruncate(LocalDateTime, LocalDateTime, boolean)
     */
    public static int getTotalDays(LocalDate start, LocalDate end) {
        if (Arrays.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.abs(betweenDays(start, end)) + 1;
    }

    // endregion


    /**
     * 计算两个日期之间的相差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static Duration between(Temporal start, Temporal end) {
        if (Arrays.isAnyNull(start, end)) {
            return null;
        }
        return Duration.between(start, end);
    }

    /**
     * 计算两个日期之间的相差小时数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenHours(Temporal start, Temporal end) {
        if (Arrays.isAnyNull(start, end)) {
            return 0;
        }
        return Numbers.toIntExact(ChronoUnit.HOURS.between(start, end));
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalDateTime date, LocalDateTime begin, LocalDateTime end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalDateTime start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalDate date, LocalDate begin, LocalDate end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalDate start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalTime date, LocalTime begin, LocalTime end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalTime start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 安全获取时间的某个属性，属性不存在返回0
     *
     * @param accessor 需要获取的时间对象
     * @param field    需要获取的属性
     * @return 时间的值，如果无法获取则默认为 0
     */
    public static int get(TemporalAccessor accessor, TemporalField field) {
        return accessor.isSupported(field) ? accessor.get(field) : ((int) field.range().getMinimum());
    }

    /**
     * 按照{@linkplain TemporalUnit}级别舍去
     *
     * @param ldt  日期时间
     * @param unit 保留的时间级别
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateTo(LocalDateTime ldt, TemporalUnit unit) {
        if (Objects.isAnyNull(ldt, unit)) {
            return ldt;
        }
        return ldt.truncatedTo(unit);
    }

    /**
     * 当前的日期时间，忽略毫秒数
     * <pre>
     *     // 假设ldt = 2020-01-01 12:34:56.789
     *     LocalDateTimes.truncateToSecond(ldt) = 2020-01-01 12:34:56
     * </pre>
     *
     * @param ldt 日期时间
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateToSecond(LocalDateTime ldt) {
        return truncateTo(ldt, ChronoUnit.SECONDS);
    }

    /**
     * 当前的日期时间，忽略秒数之后的（含秒）
     * <pre>
     *     // 假设ldt = 2020-01-01 12:34:56.789
     *     LocalDateTimes.truncateToSecond(ldt) = 2020-01-01 12:34:00
     * </pre>
     *
     * @param ldt 日期时间
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateToMinutes(LocalDateTime ldt) {
        return truncateTo(ldt, ChronoUnit.MINUTES);
    }

    /**
     * 当前的日期时间，忽略秒数之后的（含秒）
     * <pre>
     *     // 假设ldt = 2020-01-01 12:34:56.789
     *     LocalDateTimes.truncateToSecond(ldt) = 2020-01-01 12:34:00
     * </pre>
     *
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateToMinutes() {
        return truncateToMinutes(now());
    }

    /**
     * 当前的日期时间，忽略秒数之后的（含秒）
     * <pre>
     *     // 假设ldt = 2020-01-01 12:34:56.789
     *     LocalDateTimes.truncateToSecond(ldt) = 2020-01-01 12:00:00
     * </pre>
     *
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateToHours() {
        return truncateToHours(now());
    }

    /**
     * 当前的日期时间，忽略秒数之后的（含秒）
     * <pre>
     *     // 假设ldt = 2020-01-01 12:34:56.789
     *     LocalDateTimes.truncateToSecond(ldt) = 2020-01-01 12:00:00
     * </pre>
     *
     * @return 舍去后的日期时间
     */
    public static LocalDateTime truncateToHours(LocalDateTime ldt) {
        return truncateTo(ldt, ChronoUnit.HOURS);
    }

//    /**
//     * 根据日期时间生成CRON表达式
//     * <p style="font-weight: bold">特别注意，此处的CRON表达式并不是Spring Cron Expression</p>
//     *
//     * @param ldt 日期时间
//     * @return cron表达式
//     */
//    public static String getCronExpression(LocalDateTime ldt) {
//        return format(ldt, DefaultCustomFormatter.CRON_DATE);
//    }

    /**
     * 返回当前时间的秒开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56.789
     *     startOfMinute() = 2020-01-01 12:34:56
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #startOfSecond(LocalDateTime)
     */
    public static LocalDateTime startOfSecond() {
        return startOfSecond(now());
    }

    /**
     * 返回当前时间的秒开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56.789
     *     startOfMinute() = 2020-01-01 12:34:56
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #truncateToSecond(LocalDateTime)
     */
    public static LocalDateTime startOfSecond(LocalDateTime ldt) {
        return truncateToSecond(ldt);
    }

    /**
     * 返回当前时间的分钟开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56
     *     startOfMinute() = 2020-01-01 12:34:00
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #startOfMinute(LocalDateTime)
     */
    public static LocalDateTime startOfMinute() {
        return startOfMinute(now());
    }

    /**
     * 返回当前时间的分钟开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56
     *     startOfMinute() = 2020-01-01 12:34:00
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #truncateToMinutes(LocalDateTime)
     */
    public static LocalDateTime startOfMinute(LocalDateTime ldt) {
        return truncateToHours(ldt);
    }

    /**
     * 返回当前时间的小时开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56
     *     startOfHour() = 2020-01-01 12:00:00
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #startOfHour(LocalDateTime)
     */
    public static LocalDateTime startOfHour() {
        return startOfHour(now());
    }

    /**
     * 返回当前时间的小时开始时间
     * <pre>
     *     now = 2020-01-01 12:34:56
     *     startOfHour() = 2020-01-01 12:00:00
     * </pre>
     *
     * @return {@linkplain LocalDateTime}
     * @see #truncateToHours(LocalDateTime)
     */
    public static LocalDateTime startOfHour(LocalDateTime ldt) {
        return truncateToHours(ldt);
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @return 一天的开始
     */
    public static LocalDateTime startOfDay() {
        return startOfDay(now());
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @return 一天的开始
     */
    public static LocalDateTime beginOfDay() {
        return startOfDay();
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atStartOfDay();
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfDay(LocalDate date) {
        return startOfDay(date);
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return startOfDay(date.toLocalDate());
    }

    /**
     * 返回一天的开始，即当天的00:00:00
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfDay(LocalDateTime date) {
        return startOfDay(date);
    }

    /**
     * 返回指定时间秒的最大值，注意毫秒部分仍然为0·
     * <pre>
     *     ldt = 2020-01-01 12:34:56.789
     *     endOfSecond(ldt) = 2020-01-01 12:34:59
     * </pre>
     *
     * @param ldt 日期时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfSecond(LocalDateTime ldt) {
        LocalDateTime localDateTime = truncateToSecond(ldt);
        if (localDateTime != null) {
            localDateTime = localDateTime.withSecond(59);
        }
        return localDateTime;
    }

    /**
     * 返回一天的结束，即当天的23:59:59
     *
     * @return 一天的结束
     */
    public static LocalDateTime endOfDay() {
        return endOfDay(now());
    }

    /**
     * 返回一天的结束，即当天的23:59:59
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atTime(MAX_TIME);
    }

    /**
     * 返回一天的结束，即当天的23:59:59
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return endOfDay(date.toLocalDate());
    }

    /**
     * 上月
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime lastMonth() {
        return now().minusMonths(1);
    }

    /**
     * 下月
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime nextMonth() {
        return now().plusMonths(1);
    }

    /**
     * 昨天
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime yesterday() {
        return yesterday(now());
    }

    /**
     * 指定时间的昨天
     *
     * @param date 指定日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime yesterday(LocalDateTime date) {
        return offsetDay(date, 0);
    }

    /**
     * 明天
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime tomorrow() {
        return now().plusDays(1);
    }

    /**
     * 上周
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime lastWeek() {
        return now().minusWeeks(1);
    }

    /**
     * 下周
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime nextWeek() {
        return now().plusWeeks(1);
    }

    /**
     * 获取当前时间所在一周的开始时间，一周的开始是周一
     *
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime startOfWeek() {
        return beginOfWeek();
    }

    /**
     * 获取当前时间所在一周的开始时间，一周的开始是周一
     *
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime beginOfWeek() {
        return beginOfWeek(now());
    }

    /**
     * 获取指定时间所在一周的开始时间，一周的开始是周一
     *
     * @param date 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime startOfWeek(LocalDate date) {
        return beginOfWeek(date);
    }

    /**
     * 获取指定时间所在一周的开始时间，一周的开始是周一
     *
     * @param date 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime beginOfWeek(LocalDate date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return startOfDay(ld);
    }

    /**
     * 获取指定时间所在一周的开始时间，一周的开始是周一
     *
     * @param ldt 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime startOfWeek(LocalDateTime ldt) {
        return beginOfWeek(ldt);
    }

    /**
     * 获取指定时间所在一周的开始时间，一周的开始是周一
     *
     * @param ldt 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime beginOfWeek(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return startOfWeek(ldt.toLocalDate());
    }

    /**
     * 获取当前时间所在一周的结束时间，一周的开结束是周日
     *
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime endOfWeek() {
        return endOfWeek(now());
    }

    /**
     * 获取指定时间所在一周的结束时间，一周的开结束是周日
     *
     * @param date 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime endOfWeek(LocalDate date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return endOfDay(ld);
    }

    /**
     * 获取指定时间所在一周的结束时间，一周的开结束是周日
     *
     * @param date 指定时间
     * @return {@linkplain LocalDateTime}
     */
    public static LocalDateTime endOfWeek(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return endOfWeek(date.toLocalDate());
    }

    /**
     * 获取此时此刻月的开始时间
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime beginOfMonth() {
        return beginOfMonth(now());
    }

    /**
     * 获取此时此刻月的开始时间
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime startOfMonth() {
        return beginOfMonth();
    }

    /**
     * 获取某月的开始时间
     *
     * @param ldt 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime beginOfMonth(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return beginOfMonth(ldt.toLocalDate());
    }

    /**
     * 获取某月的开始时间
     *
     * @param ldt 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime startOfMonth(LocalDateTime ldt) {
        return beginOfMonth(ldt);
    }

    /**
     * 获取某月的开始时间
     *
     * @param date 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime beginOfMonth(LocalDate date) {
        if (date == null) {
            return null;
        }
        return startOfDay(date.with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 获取某月的开始时间
     *
     * @param date 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime startOfMonth(LocalDate date) {
        return beginOfMonth(date);
    }

    /**
     * 获取某月的结束时间
     *
     * @param ldt 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime endOfMonth(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return endOfMonth(ldt.toLocalDate());
    }

    /**
     * 获取某月的结束时间
     *
     * @param date 日期
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime endOfMonth(LocalDate date) {
        if (date == null) {
            return null;
        }
        return endOfDay(date.with(TemporalAdjusters.lastDayOfMonth()));
    }

    /**
     * 返回当前时间所在季度的第一天的开始时刻
     *
     * @return 一天的开始
     */
    public static LocalDateTime startOfQuarter() {
        return startOfQuarter(now());
    }

    /**
     * 返回当前时间所在季度的第一天的开始时刻
     *
     * @return 一天的开始
     */
    public static LocalDateTime beginOfQuarter() {
        return startOfQuarter();
    }

    /**
     * 返回指定时间所在季度的第一天的开始时刻
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfQuarter(LocalDate date) {
        if (date == null) {
            return null;
        }
        Month month = date.getMonth().firstMonthOfQuarter();
        return LocalDateTime.of(LocalDate.of(date.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 返回指定时间所在季度的第一天的开始时刻
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfQuarter(LocalDate date) {
        return startOfQuarter(date);
    }

    /**
     * 返回指定时间所在季度的第一天的开始时刻
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfQuarter(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return startOfQuarter(date.toLocalDate());
    }

    /**
     * 返回指定时间所在季度的第一天的开始时刻
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfQuarter(LocalDateTime date) {
        return startOfQuarter(date);
    }

    /**
     * 返回指定时间所在季度的最后一天的最后时刻
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfQuarter(LocalDate date) {
        if (date == null) {
            return null;
        }
        Month month = date.getMonth().firstMonthOfQuarter();
        month = month.plus(2);
        LocalDate ld = LocalDate.of(date.getYear(), month, date.getDayOfMonth()).with(TemporalAdjusters.lastDayOfMonth());
        return endOfDay(ld);
    }

    /**
     * 返回指定时间所在季度的最后一天的最后时刻
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfQuarter(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return endOfQuarter(date.toLocalDate());
    }

    /**
     * 返回当前时间所在年的第一天的开始
     *
     * @return 一天的开始
     */
    public static LocalDateTime startOfYear() {
        return startOfYear(now());
    }

    /**
     * 返回当前时间所在年的第一天的开始
     *
     * @return 一天的开始
     */
    public static LocalDateTime beginOfYear() {
        return startOfYear();
    }

    /**
     * 返回指定时间所在年的第一天的开始
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfYear(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 返回指定时间所在年的第一天的开始
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfYear(LocalDate date) {
        return startOfYear(date);
    }

    /**
     * 返回指定时间所在年的第一天的开始
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime startOfYear(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return startOfYear(date.toLocalDate());
    }

    /**
     * 返回指定时间所在年的第一天的开始
     *
     * @param date 某一天的时间
     * @return 一天的开始
     */
    public static LocalDateTime beginOfYear(LocalDateTime date) {
        return startOfYear(date);
    }

    /**
     * 返回指定时间所在年的最后一天最后时刻
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfYear(LocalDate date) {
        if (date == null) {
            return null;
        }
        return endOfDay(date.with(TemporalAdjusters.lastDayOfYear()));
    }

    /**
     * 返回指定时间所在年的最后一天最后时刻
     *
     * @param date 某一天的时间
     * @return 一天的结束
     */
    public static LocalDateTime endOfYear(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return endOfYear(date.toLocalDate());
    }

    // region 时间偏移（往前和往后）

    /**
     * 对当前时间移动秒数
     *
     * @param seconds 移动秒数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetSecond(long seconds) {
        return offsetSecond(now(), seconds);
    }

    /**
     * 对当前时间移动秒数
     *
     * @param date    日期
     * @param seconds 移动秒数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetSecond(LocalDateTime date, long seconds) {
        if (date == null || seconds == 0) {
            return date;
        }
        return date.plusSeconds(seconds);
    }

    /**
     * 对当前时间移动分钟数
     *
     * @param minutes 移动分钟数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetMinute(long minutes) {
        return offsetMinute(now(), minutes);
    }

    /**
     * 根据指定时间移动分钟数
     *
     * @param date    日期
     * @param minutes 移动分钟数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetMinute(LocalDateTime date, long minutes) {
        if (date == null || minutes == 0) {
            return date;
        }
        return date.plusMinutes(minutes);
    }

    /**
     * 对当前时间移动小时数
     *
     * @param hours 移动小时数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetHour(long hours) {
        return offsetHour(now(), hours);
    }

    /**
     * 对当前时间移动小时数
     *
     * @param date  日期
     * @param hours 移动小时数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetHour(LocalDateTime date, long hours) {
        if (date == null || hours == 0) {
            return date;
        }
        return date.plusHours(hours);
    }

    /**
     * 根据指定时间移动天数
     *
     * @param day 移动的天数，正数向未来移动，负数向历史移动
     * @return 移动的日期
     */
    public static LocalDateTime offsetDay(long day) {
        return offsetDay(now(), day);
    }

    /**
     * 根据指定时间移动天数
     *
     * @param date 日期
     * @param day  移动的天数，正数向未来移动，负数向历史移动
     * @return 移动的日期
     */
    public static LocalDateTime offsetDay(LocalDateTime date, long day) {
        if (date == null || day == 0) {
            return date;
        }
        return date.plusDays(day);
    }

    /**
     * 对当前时间移动月份数
     *
     * @param months 移动月份数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetMonth(long months) {
        return offsetMonth(now(), months);
    }

    /**
     * 对当前时间移动月份数
     *
     * @param date   日期
     * @param months 移动月份数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetMonth(LocalDateTime date, long months) {
        if (date == null || months == 0) {
            return date;
        }
        return date.plusMonths(months);
    }

    /**
     * 对当前时间移动年数
     *
     * @param years 移动年数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetYear(long years) {
        return offsetYear(now(), years);
    }

    /**
     * 对当前时间移动年数
     *
     * @param date  日期
     * @param years 移动年数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetYear(LocalDateTime date, long years) {
        if (date == null || years == 0) {
            return date;
        }
        return date.plusYears(years);
    }

    /**
     * 对当前时间移动周数
     *
     * @param weeks 移动周数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetWeek(long weeks) {
        return offsetWeek(now(), weeks);
    }

    /**
     * 对当前时间移动周数
     *
     * @param date  日期
     * @param weeks 移动周数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetWeek(LocalDateTime date, long weeks) {
        if (date == null || weeks == 0) {
            return date;
        }
        return date.plusWeeks(weeks);
    }

    /**
     * 对当前时间移动指定周期
     *
     * @param duration 移动周期数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetDuration(Duration duration) {
        return offsetDuration(now(), duration);
    }

    /**
     * 对当前时间移动指定周期
     *
     * @param duration 移动周期数，正数向未来移动，负数向历史移动
     * @return 移动后的日期
     */
    public static LocalDateTime offsetDuration(LocalDateTime date, Duration duration) {
        if (date == null || duration == null) {
            return date;
        }
        return date.plus(duration);
    }

    // endregion

    /**
     * 返回当前日期月份的第几天
     *
     * @return 月份的第几天
     */
    public static int dayOfMonth() {
        return dayOfMonth(now());
    }

    /**
     * 返回当前日期月份的第几天，如果参数为{@code null}则返回{@code 0}
     *
     * @param ldt 指定日期
     * @return 月份的第几天
     */
    public static int dayOfMonth(LocalDateTime ldt) {
        if (ldt == null) {
            return 0;
        }
        return ldt.getDayOfMonth();
    }

}
