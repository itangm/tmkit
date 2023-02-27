package cn.tmkit.core.lang;

import cn.tmkit.core.date.DateUtil;
import cn.tmkit.core.lang.regex.ExtraPatternConstant;
import cn.tmkit.core.lang.regex.PatternPool;
import cn.tmkit.core.lang.regex.Regexes;

import java.util.regex.Matcher;

/**
 * 常见验证工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class ValidatorUtil {

    /**
     * 判断是否为空，支持字符串、数组、集合、字典类的空判断
     *
     * @param value 值
     * @return 是否为空
     * @see Objects#isEmpty(Object)
     */
    public static boolean isEmpty(Object value) {
        return Objects.isEmpty(value);
    }

    /**
     * 判断是否不为空，如果是字符串则判断是否为{@code null}或""
     *
     * @param value 值
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    /**
     * 判断字符串是否为纯数字组成
     *
     * @param value 数值字符串
     * @return {@link boolean}
     * @see Numbers#isDigits(CharSequence)
     */
    public static boolean isDigits(CharSequence value) {
        return Numbers.isDigits(value);
    }

    /**
     * 判断是否是数值,如果是则返回true,否则返回false
     *
     * @param value 数值字符串
     * @return {@linkplain boolean}
     * @see Numbers#isNumeric(CharSequence)
     */
    public static boolean isDecimal(CharSequence value) {
        return Numbers.isNumeric(value);
    }

    /**
     * 判断是否是英文字母、数字和下划线
     *
     * @param value 值
     * @return {@linkplain boolean}
     */
    public static boolean isGeneral(CharSequence value) {
        return Regexes.isMatch(PatternPool.GENERAL, value);
    }

    /**
     * 验证是否为手机号码（中国）
     *
     * @param value 值
     * @return 是否为手机号码（中国）
     */
    public static boolean isMobile(CharSequence value) {
        return Regexes.isMatch(PatternPool.MOBILE_PHONE, value);
    }

    /**
     * 判断字符串是否为生日
     *
     * @param value 字符串
     * @return 是否为生日
     */
    public static boolean isBirthday(CharSequence value) {
        if (Strings.isEmpty(value)) {
            return false;
        }
        Matcher matcher = PatternPool.BIRTHDAY.matcher(value);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1)),
                    month = Integer.parseInt(matcher.group(3)),
                    day = Integer.parseInt(matcher.group(5));
            return isBirthday(year, month, day);
        }
        return false;
    }

    /**
     * 是否为生日
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 是否为生日
     */
    public static boolean isBirthday(int year, int month, int day) {
        // year
        if (year < 1900) {
            return false;
        }

        // month
        if (month < 1 || month > 12) {
            return false;
        }

        // day
        if (day < 1 || day > 31) {
            return false;
        }
        if (month == 2) {
            if (DateUtil.isLeapYear(year)) {
                return day <= 29;
            } else {
                return day <= 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        }
        return true;
    }

    /**
     * 验证是否为可用邮箱地址
     *
     * @param value 值
     * @return {@link boolean}
     */
    public static boolean isEmail(CharSequence value) {
        return Regexes.isMatch(PatternPool.EMAIL, value);
    }

    /**
     * 判断是否是车牌
     *
     * @param value 值
     * @return {@link boolean}
     */
    public static boolean isPlateCode(CharSequence value) {
        return Regexes.isMatch(ExtraPatternConstant.PLATE_CODE, value);
    }

    /**
     * 判断是否是身份证号码，该方法仅支持18位身份证的。
     * <p>需要注意的是：该方法不验证身份证是否满足规则</p>
     *
     * @param value 值
     * @return {@link boolean}
     */
    public static boolean isIdCardNumber(CharSequence value) {
        return Regexes.isMatch(PatternPool.ID_CARD_NUMBER_18, value);
    }

    /**
     * 验证是否为IPV4地址
     *
     * @param value 值
     * @return 是否为IPV4地址
     */
    public static boolean isIpv4(CharSequence value) {
        return IPUtil.isIPv4(value);
    }

    /**
     * 验证是否为IPV6地址
     *
     * @param value 值
     * @return 是否为IPV6地址
     */
    public static boolean isIpv6(CharSequence value) {
        return IPUtil.isIPv6(value);
    }

    /**
     * 验证是否为URL
     *
     * @param value 值
     * @return 是否为URL
     * @see Urls#isUrl(CharSequence)
     */
    public static boolean isUrl(CharSequence value) {
        return Urls.isUrl(value);
    }

}
