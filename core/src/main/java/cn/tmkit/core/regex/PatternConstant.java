package cn.tmkit.core.regex;

import cn.tmkit.core.support.SimpleCache;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 常见的正则表达式：<a href="https://any86.github.io/any-rule/">https://any86.github.io/any-rule/</a>
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class PatternConstant {

    /**
     * 十六进制
     */
    public static final String HEX_STR = "^[\\da-fA-F]+$";

    /**
     * 十六进制
     */
    public static final Pattern HEX = Pattern.compile(HEX_STR);

    /**
     * 英文字母 、数字和下划线
     */
    public static final String GENERAL_STR = "^\\w+$";

    /**
     * 英文字母 、数字和下划线
     */
    public final static Pattern GENERAL = Pattern.compile(GENERAL_STR);

    /**
     * 数值
     */
    public static final String NUMBERS_STR = "^[-+]?\\d+(.\\d+)?$";

    /**
     * 数值
     */
    public final static Pattern NUMBERS = Pattern.compile(NUMBERS_STR);

    /**
     * 纯数字
     */
    public static final String DIGITS_STR = "^\\d+$";

    /**
     * 纯数字
     */
    public static final Pattern DIGITS = Pattern.compile(DIGITS_STR);

    /**
     * 金额,2位小数点
     */
    public static final String MONEY_STR = "\\d+(.\\d{1,2})?";

    /**
     * 金额,2位小数点
     */
    public static final Pattern MONEY = Pattern.compile(MONEY_STR);

    /**
     * 任意一个中文/汉字
     */
    public static final String CHINESE_ANY_STR = "[\u4E00-\u9FFF！|，。（）《》“”？：；【】]";

    /**
     * 任意一个中文/汉字
     */
    public static final Pattern CHINESE_ANY = Pattern.compile(CHINESE_ANY_STR);

    /**
     * 多个中文
     */
    public static final String CHINESES_STR = ("[\u4E00-\u9FFF！|，。（）《》“”？：；【】]+");

    /**
     * 多个中文
     */
    public static final Pattern CHINESES = Pattern.compile(CHINESES_STR);

    /**
     * http(s) or (s)ftp
     */
    public final static String URL_HTTP_OR_FTP_STR = ("^(((ht|f)tps?)://)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    /**
     * http(s) or (s)ftp
     */
    public final static Pattern URL_HTTP_OR_FTP = Pattern.compile(URL_HTTP_OR_FTP_STR);

    /**
     * IP v4
     */
    public final static String IPV4_STR = ("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]).){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(?::(?:[0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$");

    /**
     * IP v4
     */
    public final static Pattern IPV4 = Pattern.compile(IPV4_STR);

    /**
     * IP v6
     */
    public final static String IPV6_STR = ("^(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))|\\[(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))\\](?::(?:[0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$");

    /**
     * IP v6
     */
    public final static Pattern IPV6 = Pattern.compile(IPV6_STR, Pattern.CASE_INSENSITIVE);

    /**
     * 手机机身码(IMEI)
     */
    public static final String IMEI_STR = ("^\\d{15,17}$");

    /**
     * 手机机身码(IMEI)
     */
    public static final Pattern IMEI = Pattern.compile(IMEI_STR);

    /**
     * 手机号(mobile phone)中国(严谨), 根据工信部2019年最新公布的手机号段
     */
    public static final String MOBILE_PHONE_STRICT_STR = ("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$");

    /**
     * 手机号(mobile phone)中国(严谨), 根据工信部2019年最新公布的手机号段
     */
    public static final Pattern MOBILE_PHONE_STRICT = Pattern.compile(MOBILE_PHONE_STRICT_STR);

    /**
     * 手机号(mobile phone)中国(宽松), 只要是13,14,15,16,17,18,19开头即可
     */
    public static final String MOBILE_PHONE_COMPATIBLE_STR = ("^(?:(?:\\+|00)86)?1[3-9]\\d{9}$");

    /**
     * 手机号(mobile phone)中国(宽松), 只要是13,14,15,16,17,18,19开头即可
     */
    public static final Pattern MOBILE_PHONE_COMPATIBLE = Pattern.compile(MOBILE_PHONE_COMPATIBLE_STR);

    /**
     * 手机号(mobile phone)中国(最宽松), 只要是1开头即可, 如果你的手机号是用来接收短信, 优先建议选择这一条
     */
    public static final String MOBILE_PHONE_STR = ("^(?:(?:\\+|00)86)?1\\d{10}$");

    /**
     * 手机号(mobile phone)中国(最宽松), 只要是1开头即可, 如果你的手机号是用来接收短信, 优先建议选择这一条
     */
    public static final Pattern MOBILE_PHONE = Pattern.compile(MOBILE_PHONE_STR);

    /**
     * date(日期)
     */
    public static final String DATE_STR = ("^\\d{4}(-)(1[0-2]|0?\\d)\\1([0-2]\\d|\\d|30|31)$");

    /**
     * date(日期)
     */
    public static final Pattern DATE = Pattern.compile(DATE_STR);

    /**
     * email(邮箱)
     * 正则来自：http://emailregex.com/
     */
    public static final String EMAIL_STR = ("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    /**
     * email(邮箱)
     * 正则来自：http://emailregex.com/
     */
    public static final Pattern EMAIL = Pattern.compile(EMAIL_STR, Pattern.CASE_INSENSITIVE);

    /**
     * 身份证号(1代,15位数字)
     */
    public static final Pattern ID_CARD_NUMBER_15 = Pattern.compile("^[1-9]\\d{7}(?:0\\d|10|11|12)(?:0[1-9]|[1-2][\\d]|30|31)\\d{3}$");

    /**
     * 身份证号(2代,18位数字),最后一位是校验位,可能为数字或字符X
     */
    public static final String ID_CARD_NUMBER_18_STR = ("^[1-9]\\d{5}(?:18|19|20|21)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[\\dXx]$");

    /**
     * 身份证号(2代,18位数字),最后一位是校验位,可能为数字或字符X
     */
    public static final Pattern ID_CARD_NUMBER_18 = Pattern.compile(ID_CARD_NUMBER_18_STR);

    /**
     * 身份证号, 支持1/2代(15位/18位数字)
     */
    public static final String ID_CARD_NUMBER_STR = ("(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0[1-9]|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)");

    /**
     * 身份证号, 支持1/2代(15位/18位数字)
     */
    public static final Pattern ID_CARD_NUMBER = Pattern.compile(ID_CARD_NUMBER_STR);

    /**
     * 时间正则，如
     * 12:00
     * 12:00:00
     * 9:3
     */
    public static final String TIME_STR = ("^\\d{1,2}:\\d{1,2}(:\\d{1,2})?$");

    /**
     * 时间正则，如
     * 12:00
     * 12:00:00
     * 9:3
     */
    public static final Pattern TIME = Pattern.compile(TIME_STR);

    /**
     * 生日,支持形如
     * 2022-1-2
     * 2022/1/2
     * 2022年1月2日
     * 2022.1.2
     */
    public static final String BIRTHDAY_STR = ("^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$");

    /**
     * 生日,支持形如
     * 2022-1-2
     * 2022/1/2
     * 2022年1月2日
     * 2022.1.2
     */
    public static final Pattern BIRTHDAY = Pattern.compile(BIRTHDAY_STR);

    /**
     * 经纬度中的经度正则表达式
     */
    public static final String LONGITUDE = "^[-+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]?\\d\\.\\d{1,6}|180\\.0{1,6})$";

    /**
     * 经纬度中的纬度正则表达式
     */
    public static final String LATITUDE = "^[-+]?([0-8]?\\d{1}\\.\\d{1,6}|90\\.0{1,6})$";

    // ---

    /**
     * Pattern Pool
     */
    private static final SimpleCache<RegexWithFlags, Pattern> POOL = new SimpleCache<>(128);

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return {@link Pattern}
     */
    public static Pattern get(String regex) {
        return get(regex, 0);
    }

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return {@link Pattern}
     */
    public static Pattern get(String regex, int flags) {
        return POOL.computeIfAbsent(new RegexWithFlags(regex, flags), regexWithFlags -> Pattern.compile(regex, flags));
    }

    /**
     * 移除缓存
     *
     * @param regex 正则
     * @param flags 标识
     * @return 移除的{@link Pattern}，可能为{@code null}
     */
    public static Pattern remove(String regex, int flags) {
        return POOL.remove(new RegexWithFlags(regex, flags));
    }

    /**
     * 清空缓存池
     */
    public static void clear() {
        POOL.clear();
    }

    /**
     * regex with flags
     */
    private static class RegexWithFlags {

        private final String regex;

        private final int flags;


        private RegexWithFlags(String regex, int flags) {
            this.regex = regex;
            this.flags = flags;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegexWithFlags that = (RegexWithFlags) o;
            return flags == that.flags &&
                    regex.equals(that.regex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(regex, flags);
        }
    }

}
