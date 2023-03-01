package cn.tmkit.core.support;

import cn.tmkit.core.lang.Chars;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.lang.ValidatorUtil;
import cn.tmkit.core.lang.regex.PatternPool;

/**
 * 国内身份证工具类，仅支持18位的身份证号码
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class IdCardUtil {

    /**
     * 新的中国公民身份证号码长度（18位）
     */
    public static final int NEW_CHINA_ID_CARD_LENGTH = 18;

    /**
     * 每位加权因子
     */
    private static final int[] FACTORS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 判断给定的字符串是否为有效的18位身份证号码
     *
     * @param value 被校验的字符串
     * @return true / false
     */
    public static boolean isValid(String value) {
        return isValid(value, true);
    }

    /**
     * 判断给定的字符串是否为有效的18位身份证号码
     * <p>不进行校验行政区是否正确</p>
     *
     * @param value      被校验的字符串
     * @param ignoreCase 是否忽略大小写,{@code true}则忽略
     * @return true / false
     */
    public static boolean isValid(String value, boolean ignoreCase) {
        if (Strings.isEmpty(value)) {
            return false;
        }
        if (NEW_CHINA_ID_CARD_LENGTH != value.length()) {
            return false;
        }
        String substr = value.substring(0, 17);
        if (!PatternPool.DIGITS.matcher(substr).matches()) {
            return false;
        }

        // 生日
        String birthday = substr.substring(6, 14);
        if (!ValidatorUtil.isBirthday(birthday)) {
            return false;
        }

        // 判断校验码是否正确
        return Chars.equals(getCheckCode18(substr), value.charAt(17), ignoreCase);
    }

    /**
     * 获得第18位校验码
     *
     * @param code17 前17位字符
     * @return 第18位校验码字符
     */
    private static char getCheckCode18(String code17) {
        int sum = 0;
        char[] array = code17.toCharArray();
        for (int i = 0, length = array.length; i < length; i++) {
            sum += ((array[i] - '0') * FACTORS[i]);
        }
        int result = sum % 11;
        int code = (12 - result) % 11;
        return (code == 10) ? 'X' : ((char) ('0' + code));
    }

}
