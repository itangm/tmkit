package cn.tmkit.core.regex;

import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Regexes {

    /**
     * 给定内容是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param cse   字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(String regex, CharSequence cse) {
        if (Strings.isEmpty(regex)) {
            return false;
        }
        if (cse == null) {
            return false;
        }
        return isMatch(PatternPool.get(regex), cse);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param cse     字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence cse) {
        if (Objects.isAnyNull(pattern, cse)) {
            return false;
        }
        return pattern.matcher(cse).matches();
    }

}
