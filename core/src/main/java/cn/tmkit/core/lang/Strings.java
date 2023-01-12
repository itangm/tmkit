package cn.tmkit.core.lang;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Strings {

    // region 定义的公共的字符串相关的常量

    /**
     * null字符串
     */
    public static final String NULL = "null";

    public static final String UNDEFINED = "undefined";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 英文逗号字符串
     */
    public static final String COMMA = EMPTY_STRING + Chars.COMMA;

    /**
     * 下划线
     */
    public static final String UNDERLINE = EMPTY_STRING + Chars.UNDERLINE;

    /**
     * 中划线/破折号
     */
    public static final String DASH = EMPTY_STRING + Chars.DASH;

    /**
     * 点
     */
    public static final String DOT = EMPTY_STRING + Chars.DOT;

    /**
     * 左边大括号
     */
    public static final String LEFT_CURLY_BRACKET = EMPTY_STRING + Chars.LEFT_CURLY_BRACKET;

    /**
     * 分隔符（左） {
     */
    public static final String DELIMITER_START = LEFT_CURLY_BRACKET;

    /**
     * 右边大括号
     */
    public static final String RIGHT_CURLY_BRACKET = EMPTY_STRING + Chars.RIGHT_CURLY_BRACKET;

    /**
     * 分隔符（右） }
     */
    public static final String DELIMITER_END = RIGHT_CURLY_BRACKET;

    /**
     * 空的JSON字符串
     */
    public static final String EMPTY_JSON = LEFT_CURLY_BRACKET + RIGHT_CURLY_BRACKET;

    /**
     * 正斜杠
     */
    public static final String SLASH = EMPTY_STRING + Chars.SLASH;

    /**
     * 反斜杠
     */
    public static final String BACKSLASH = EMPTY_STRING + Chars.BACKSLASH;

    /**
     * &符号
     */
    public static final String AMP = EMPTY_STRING + Chars.AMP;

    /**
     * :符号
     */
    public static final String COLON = EMPTY_STRING + Chars.COLON;

    /**
     * 等号
     */
    public static final String EQUALS = EMPTY_STRING + Chars.EQUALS;

    /**
     * 空格
     */
    public static final String SPACE = EMPTY_STRING + Chars.SPACE;

    // endregion


    // region 判断字符串是否为空或不为空

    /**
     * 字符串是否为空白，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return true;
        }
        int length = cse.length();
        for (int i = 0; i < length; i++) {
            if (!Chars.isBlankChar(cse.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 字符粗
     * @return 是否不为空
     */
    public static boolean isNotBlank(CharSequence cse) {
        return !isBlank(cse);
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     * <p>在Web环境下，字符串"null"或"undefined"也是为空</p>
     *
     * @param cse 字符粗
     * @return 是否不为空
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isNotBlankInWeb(CharSequence cse) {
        if (Strings.isNotBlank(cse)) {
            String str = cse.toString();
            return !(Strings.NULL.equals(str) || Strings.UNDEFINED.equals(str));
        }
        return false;
    }

    /**
     * 数组的任意元素是否为空白
     *
     * @param array 数组
     * @return 如果数组为空或元素中为空白则返回{@code true}，否则返回{@code  false}
     * @see #isBlank(CharSequence)
     */
    public static boolean isAnyBlank(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isBlank(cse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的所有元素都为空白
     *
     * @param array 数组
     * @return 数组是否都为空
     */
    public static boolean isAllBlank(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isNotBlank(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组的任意一个字符串不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isAnyNotBlank(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return false;
        }
        for (CharSequence cse : array) {
            if (isNotBlank(cse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的所有元素都不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isAllNotBlank(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return false;
        }
        for (CharSequence cse : array) {
            if (isBlank(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(CharSequence)
     */
    public static boolean isAnyNotBlank(Collection<CharSequence> strColl) {
        if (Collections.isEmpty(strColl)) {
            return false;
        }
        for (CharSequence cs : strColl) {
            if (isNotBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(CharSequence)
     */
    public static boolean isAllNotBlank(Collection<CharSequence> strColl) {
        if (Collections.isEmpty(strColl)) {
            return false;
        }
        for (CharSequence str : strColl) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param cse 被检测的字符序列
     * @return 字符串是否为空
     */
    public static boolean isEmpty(CharSequence cse) {
        return cse == null || cse.length() == 0;
    }

    /**
     * 判断字符串列表中有任何一个字符串是否为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中任一元素<code>null</code></li>
     * <li>数组中任一元素为""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     Strings.isAnyEmpty();               = true
     *     Strings.isAnyEmpty("");             = true
     *     Strings.isAnyEmpty("","1");         = true
     *     Strings.isAnyEmpty("1",null,"");    = true
     *     Strings.isAnyEmpty("a","b");        = false
     * </pre>
     *
     * @param array 被检测的字符串列表
     * @return 字符串列表中是否有任一字符串为空
     * @see Strings#isEmpty(CharSequence)
     */
    public static boolean isAnyEmpty(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (CharSequence ele : array) {
            if (isEmpty(ele)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表是否都为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中所有元素<code>null</code>或则""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     Strings.isAllEmpty();               = true
     *     Strings.isAllEmpty("");             = true
     *     Strings.isAllEmpty("","1");         = false
     *     Strings.isAllEmpty("",null,"");     = true
     *     Strings.isAllEmpty("a","b");        = false
     * </pre>
     *
     * @param array 字符串列表
     * @return 字符串列表所有元素是否都为空
     * @see #isEmpty(CharSequence)
     */
    public static boolean isAllEmpty(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isNotEmpty(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param cse 被检测的字符序列
     * @return 字符串是否不为空
     */
    public static boolean isNotEmpty(CharSequence cse) {
        return hasLength(cse);
    }

    /**
     * 判断字符串不是空字符串，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     * <pre>
     *     Strings.hasLength(null);              = false
     *     Strings.hasLength("");                = false
     *     Strings.hasLength(" ");               = true
     *     Strings.hasLength("Hi");              = true
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 非空字符串
     */
    public static boolean hasLength(CharSequence cse) {
        return !isEmpty(cse);
    }

    /**
     * 判断字符串是否存在文本字符，文本字符串的定义：
     * <ul>
     * <li>不是{@code null}</li>
     * <li>不是""</li>
     * <li>字符串中的任意一个字符不是''</li>
     * </ul>
     * <pre>
     *     Strings.hasText(null);              = false
     *     Strings.hasText("");                = false
     *     Strings.hasText(" ");               = false
     *     Strings.hasText("  ");              = false
     *     Strings.hasText(" a");              = true
     *     Strings.hasText("a");               = true
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 非空白字符串
     * @see #isNotBlank(CharSequence)
     */
    public static boolean hasText(CharSequence cse) {
        return isNotBlank(cse);
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     * <pre>
     *     Strings.containsBlank(null);            = false
     *     Strings.containsBlank("");              = false
     *     Strings.containsBlank(" ");             = true
     *     Strings.containsBlank("ab d");          = true
     *     Strings.containsBlank("abcd");          = false
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see Character#isWhitespace(char)
     */
    public static boolean containsBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return false;
        }
        for (int len = cse.length(), i = 0; i < len; i++) {
            if (Character.isWhitespace(cse.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     *
     * @param cse 被检测的字符串
     * @return true or false
     * @see #containsBlank(CharSequence)
     */
    public static boolean containsWhitespace(CharSequence cse) {
        return containsBlank(cse);
    }

    // endregion


    // region General

    /**
     * 字符串，如果字符串为空则返回默认值
     *
     * @param str          待检测的字符串
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String getIfEmpty(CharSequence str, CharSequence defaultValue) {
        return isEmpty(str) ? str(defaultValue) : str(str);
    }

    /**
     * 字符串，如果字符串为空则返回默认值
     *
     * @param str          待检测的字符串
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String getIfBlank(CharSequence str, CharSequence defaultValue) {
        return isBlank(str) ? str(defaultValue) : str(str);
    }

    /**
     * 构建字符串
     * 如果编码为{@code  null}则采用系统编码
     *
     * @param data     字节数组
     * @param encoding 编码，可以为空
     * @return 字符串
     */
    public static String str(byte[] data, Charset encoding) {
        if (data == null) {
            return null;
        }
        return (encoding == null) ? new String(data) : new String(data, encoding);
    }

    /**
     * {@link CharSequence} 转为字符串
     *
     * @param cse {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cse) {
        return null == cse ? null : cse.toString();
    }

    // endregion


    // region 去除字符串头尾部的空白部分

    /**
     * 去除字符串左右两侧的空白符
     * <pre>
     *     Strings.trim(null);               = null
     *     Strings.trim("");              = ""
     *     Strings.trim("    ")           = ""
     *     Strings.trim("  a b  ");       = "a b"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回已经去除左右两边的空白的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trim(CharSequence cse) {
        return trim(cse, 0);
    }

    /**
     * 去除字符串左侧的空白字符
     * <pre>
     *     Strings.ltrim(null);            = null
     *     Strings.ltrim("ab");            = "ab"
     *     Strings.ltrim("  ab c");        = "ab c"
     *     Strings.ltrim("  ab c ");       = "ab c "
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     */
    public static String trimLeft(CharSequence cse) {
        return trim(cse, -1);
    }

    /**
     * 去除字符串右侧的空白字符
     * <pre>
     *     Strings.rtrim(null);            = null
     *     Strings.rtrim("ab");            = "ab"
     *     Strings.rtrim(" ab c");         = " ab c"
     *     Strings.rtrim(" ab c ");        = " ab c"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     */
    public static String trimRight(CharSequence cse) {
        return trim(cse, 1);
    }

    /**
     * 去除字符串头尾部空白部分
     * <ul>
     * <li>当{@code mode}=-1去除字符串左侧部分空白</li>
     * <li>当{@code mode}=0去除字符串左右两侧部分空白</li>
     * <li>当{@code mode}=1去除字符串右侧部分空白</li>
     * </ul>
     *
     * @param cse  字符串
     * @param mode 去除模式
     * @return 去除空白后的字符串
     */
    public static String trim(CharSequence cse, int mode) {
        if (cse == null) {
            return null;
        }

        int length = cse.length(), start = 0, end = length;

        if (mode <= 0) {
            //trim by left
            while (start < end && Chars.isBlankChar(cse.charAt(start))) {
                start++;
            }
        }

        if (mode >= 0) {
            //trim by right
            while (start < end && Chars.isBlankChar(cse.charAt(end - 1))) {
                end--;
            }
        }

        if (start > 0 || end < length) {
            return cse.toString().substring(start, end);
        }
        return cse.toString();
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param array 原数组
     * @return 新数组，不为{@code null}
     */
    public static @NotNull String[] trim(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return Arrays.EMPTY_STRING_ARRAY;
        }
        return trimToList(array).toArray(Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param array 原数组
     * @return 新集合，不为{@code null}
     */
    public static @NotNull List<String> trimToList(CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return Collections.emptyList();
        }
        return java.util.Arrays.stream(array).map(Strings::trim).collect(Collectors.toList());
    }

    /**
     * 去除字符串集合中的每个元素左右两侧空白部分
     *
     * @param list 原数组，不为{@code null}
     */
    public static @NotNull List<String> trim(List<CharSequence> list) {
        return Collections.isEmpty(list) ? Collections.emptyList() : list.stream().map(Strings::trim).collect(Collectors.toList());
    }


    /**
     * 去除字符串中所有的空白字符
     * <pre>
     *     Strings.trimAllBlank(null);            = null
     *     Strings.trimAllBlank(" a ");           = "a"
     *     Strings.trimAllBlank(" a b c ");       = "abc"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trimAllBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        StringBuilder sb = new StringBuilder(cse);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * 去除字符串中所有的空白字符
     *
     * @param cse 字符串
     * @return 去除空白字符的字符串
     * @see #trimAllBlank(CharSequence)
     */
    public static String trimAllWhitespace(CharSequence cse) {
        return trimAllBlank(cse);
    }

    // endregion


    // region 字符串比较

    /**
     * 判断字符串中是否以给定的字符串开头，不考虑大小写问题
     * <pre>
     *     Strings.startsWithIgnoreCase("abcd",null);            = false
     *     Strings.startsWithIgnoreCase("abcd","ab");            = true
     *     Strings.startsWithIgnoreCase("abcd","AB");            = true
     *     Strings.startsWithIgnoreCase("abcd","bc");            = false
     *     Strings.startsWithIgnoreCase(null,"ab");              = false
     * </pre>
     *
     * @param cse    字符串
     * @param prefix 给定的字符串，以该字符串开头
     * @return 如果字符串{code cse}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     */
    public static boolean startsWithIgnoreCase(CharSequence cse, CharSequence prefix) {
        if (isAnyEmpty(cse, prefix)) {
            return false;
        }
        if (cse.length() < prefix.length()) {
            return false;
        }
        String value = str(cse), prefixCs = str(prefix);
        if (value.startsWith(prefixCs)) {
            return true;
        }
        String lowerCaseStr = value.substring(0, prefix.length()).toLowerCase();
        String lowerCasePrefix = prefixCs.toLowerCase();
        return lowerCasePrefix.equals(lowerCaseStr);
    }

    /**
     * 判断字符串中是否以给定的字符串结尾,不考虑大小写问题
     * <pre>
     *     Strings.endsWithIgnoreCase("ddcbab",null);            = false
     *     Strings.endsWithIgnoreCase("ddcbab","ab");            = false
     *     Strings.endsWithIgnoreCase("ddcbab","AB");            = true
     *     Strings.endsWithIgnoreCase("ddcbab","bc");            = false
     *     Strings.endsWithIgnoreCase(null,"ab");                = false
     * </pre>
     *
     * @param cse    字符串
     * @param suffix 给定的字符串，以该字符串开头
     * @return 如果字符串{code cse}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     * @see String#endsWith(String)
     */
    public static boolean endsWithIgnoreCase(CharSequence cse, CharSequence suffix) {
        if (cse == null || suffix == null) {
            return false;
        }
        if (cse.length() < suffix.length()) {
            return false;
        }
        String value = str(cse), suffixCs = str(suffix);
        if (value.endsWith(suffixCs)) {
            return true;
        }
        String lowerStr = value.substring(cse.length() - suffix.length()).toLowerCase();
        String lowerSuffix = suffixCs.toLowerCase();
        return lowerSuffix.equals(lowerStr);
    }

    /**
     * 判断连个字符串是否相等
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 判断连个字符串是否相等,忽略大小写
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 判断连个字符串是否相等
     *
     * @param str1       字符串1
     * @param str2       字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 相等为{@code true},否则为{@code false}
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (str1 == null) {
            //只有两个都为null才判断相等
            return str2 == null;
        }
        if (str2 == null) {
            return false;
        }
        return ignoreCase ? str(str1).equalsIgnoreCase(str(str2)) : str1.equals(str2);
    }

    /**
     * 指定范围内查找指定字符
     *
     * @param cse        字符串
     * @param searchChar 被查找的字符
     * @return 字符所在的位置
     */
    public static int indexOf(CharSequence cse, char searchChar) {
        return indexOf(cse, searchChar, 0);
    }

    /**
     * 指定范围内查找指定字符
     *
     * @param cse        字符串
     * @param searchChar 被查找的字符
     * @param startPos   起始位置，如果小于0，从0开始查找
     * @return 字符所在的位置
     */
    public static int indexOf(CharSequence cse, char searchChar, int startPos) {
        if (cse instanceof String) {
            return ((String) cse).indexOf(searchChar, startPos);
        } else {
            return indexOf(cse, searchChar, startPos, -1);
        }
    }

    /**
     * 在指定范围之内查找字符所在的位置
     *
     * @param cse        字符串
     * @param searchChar 被搜索的字符
     * @param startPos   开始位置，小于0则从0开始
     * @param endPos     结束位置，小于0或大于字符串长度则均为字符串末尾
     * @return 字符所在的位置
     */
    public static int indexOf(CharSequence cse, char searchChar, int startPos, int endPos) {
        if (isEmpty(cse)) {
            return Arrays.INDEX_NOT_FOUND;
        }
        final int len = cse.length();
        if (startPos <= 0 || startPos > len) {
            startPos = 0;
        }
        if (endPos > len || endPos < 0) {
            endPos = len;
        }
        for (int i = startPos; i < endPos; i++) {
            if (cse.charAt(i) == searchChar) {
                return i;
            }
        }
        return Arrays.INDEX_NOT_FOUND;
    }

    /**
     * 指定字符是否在字符串中出现过
     *
     * @param cse        字符串
     * @param searchChar 被查找的字符
     * @return 是否包含
     */
    public static boolean contains(CharSequence cse, char searchChar) {
        return indexOf(cse, searchChar) >= 0;
    }

    /**
     * 校验要匹配的字符数组是否均出现在字符串中
     *
     * @param cse        被检测的字符串
     * @param matchChars 匹配的字符
     * @return 如果都出现则返回{@code true}，否则返回{@code false}
     */
    public static boolean containsAll(CharSequence cse, char... matchChars) {
        if (Objects.isNull(cse) || matchChars == null || matchChars.length == 0) {
            return false;
        }
        for (char matchChar : matchChars) {
            if (!contains(cse, matchChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串{@code search}是否包含在字符串{@code cse}中
     *
     * @param cse    被检测的字符串
     * @param search 搜索的字符串
     * @return 存在返回{@code true}，否则返回{@code false}
     * @see #contains(CharSequence, CharSequence, boolean)
     */
    public static boolean contains(CharSequence cse, CharSequence search) {
        return contains(cse, search, false);
    }

    /**
     * 判断字符串{@code search}是否包含在字符串{@code cse}中
     *
     * @param cse        被检测的字符串
     * @param search     搜索的字符串
     * @param ignoreCase 是否忽略大小写
     * @return 存在返回{@code true}，否则返回{@code false}
     */
    public static boolean contains(CharSequence cse, CharSequence search, boolean ignoreCase) {
        if (cse == null || search == null) {
            return false;
        }
        String str = cse.toString(), result = search.toString();
        if (ignoreCase) {
            str = str.toLowerCase(Locale.ROOT);
            result = result.toLowerCase(Locale.ROOT);
        }
        return str.contains(result);
    }

    /**
     * 判断字符串是否在给定的数组中
     *
     * @param cse   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsAny(CharSequence cse, CharSequence... array) {
        return containsAny(cse, false, array);
    }

    /**
     * 判断字符串是否在给定的数组中
     *
     * @param cse        字符串
     * @param array      字符串数组
     * @param ignoreCase 是否忽略大小写
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsAny(CharSequence cse, boolean ignoreCase, CharSequence... array) {
        if (Arrays.isEmpty(array)) {
            return false;
        }
        return Arrays.stream(array).anyMatch(element -> contains(element, cse, ignoreCase));
    }

    /**
     * 判断字符串是否被包含在给定的数组中,忽略大小写
     *
     * @param cse   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsAnyIgnoreCase(CharSequence cse, CharSequence... array) {
        return containsAny(cse, true, array);
    }

    /**
     * 判断字符串是否被包含在给定的数组中,忽略大小写
     *
     * @param cse   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsAllIgnoreCase(CharSequence cse, CharSequence... array) {
        return containsAll(cse, true, array);
    }

    /**
     * 判断字符串是否被包含在给定的数组中,忽略大小写
     *
     * @param cse   字符串
     * @param array 字符串数组
     * @return 存在为{@code true},否则为{@code false}
     */
    public static boolean containsAll(CharSequence cse, boolean ignoreCase, CharSequence... array) {
        if (Objects.isNull(cse) || Arrays.isEmpty(array)) {
            return false;
        }
        String search = ignoreCase ? cse.toString().toLowerCase(Locale.ROOT) : cse.toString();
        return Arrays.stream(array).allMatch(element -> contains(element, search, ignoreCase));
    }

    // endregion


    // region 字符串替换或删除操作

    /**
     * 替换指定字符串的指定区间内字符为"*"
     *
     * @param cse   字符串
     * @param start 开始位置（包含）
     * @param end   结束位置（包含）
     * @return 替换后的字符串
     */
    public static String hide(CharSequence cse, int start, int end) {
        return replace(cse, start, end, '*');
    }

    /**
     * 替换指定字符串的指定区别的字符为'*'
     *
     * @param cse    字符串
     * @param start  开始位置（包含）
     * @param length 要被替换的长度
     * @return 替换后的字符串
     * @see #hide(CharSequence, int, int)
     */
    public static String hideLength(CharSequence cse, int start, int length) {
        return hide(cse, start, start + length - 1);
    }

    /**
     * 替换指定字符串的指定区间内字符为固定字符
     *
     * @param cse          字符串
     * @param start        开始位置（包含）
     * @param end          结束位置（包含）
     * @param replacedChar 被替换的字符
     * @return 替换后的字符串
     */
    public static String replace(CharSequence cse, int start, int end, char replacedChar) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        final int strLength = cse.length();
        if (start > strLength) {
            return str(cse);
        }
        if (end < 0) {
            end = strLength + end;
        }
        if (end >= strLength) {
            end = strLength;
        }
        if (start > end) {
            // 如果起始位置大于结束位置不替换
            return str(cse);
        }

        final char[] chars = new char[strLength];
        for (int i = 0; i < strLength; i++) {
            if (i >= start && i <= end) {
                chars[i] = replacedChar;
            } else {
                chars[i] = cse.charAt(i);
            }
        }
        return new String(chars);
    }

    /**
     * 将字符串中所有出现{@code oldStr}替换为{@code newStr}
     * 替换方法不支持正则，因此效率高于{@linkplain String#replaceAll(String, String)}
     * <pre>
     *     Strings.replace("","l","x");                                  = ""
     *     Strings.replace("com.mzlion.core.lang","","x");               = "com.mzlion.core.lang"
     *     Strings.replace("com.mzlion.core.lang",".","/");              = "com/mzlion/core/lang"
     *     Strings.replace("m z l i o n"," ","");                        = "mzlion"
     * </pre>
     *
     * @param cse    字符串
     * @param oldStr 需要替换的字符串
     * @param newStr 新的字符串
     * @return 返回替换后的字符串
     */
    public static String replace(CharSequence cse, CharSequence oldStr, CharSequence newStr) {
        if (isAnyEmpty(cse, oldStr) || newStr == null) {
            return str(cse);
        }

        String str = str(cse), oldStrRes = str(oldStr);
        StringBuilder sb = new StringBuilder(str.length());
        int pos = 0;
        int index = str.indexOf(oldStrRes, pos);
        int patternLen = oldStr.length();

        while (index >= 0) {
            sb.append(str, pos, index);
            sb.append(newStr);
            pos = index + patternLen;
            index = str.indexOf(oldStrRes, pos);
        }
        sb.append(str.substring(pos));
        return sb.toString();
    }

    /**
     * 将字符数组中出现的字符全部替换为指定的字符串
     *
     * @param cse         待处理的字符串
     * @param chars       需要替换的字符数组
     * @param replacedStr 替换成的字符串
     * @return 新的字符串
     */
    public static String replace(CharSequence cse, char[] chars, CharSequence replacedStr) {
        if (isEmpty(cse) || Arrays.isEmpty(chars)) {
            return str(cse);
        }
        int length = cse.length();
        StringBuilder builder = new StringBuilder(length + chars.length * replacedStr.length());
        for (int i = 0; i < length; i++) {
            if (contains(chars, cse.charAt(i))) {
                builder.append(replacedStr);
            } else {
                builder.append(cse.charAt(i));
            }
        }
        return builder.toString();
    }

    private static boolean contains(char[] array, char search) {
        for (char ch : array) {
            if (ch == search) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串替换
     *
     * @param cse   待处理的字符串
     * @param oldCh 原字符
     * @param newCh 替换后的字符
     * @return 替换后的字符串
     */
    public static String replace(CharSequence cse, char oldCh, char newCh) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        StringBuilder sb = new StringBuilder(cse.length());
        char ch;
        for (int i = 0, length = cse.length(); i < length; i++) {
            ch = cse.charAt(i);
            if (ch == oldCh) {
                sb.append(newCh);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串{@code cse}中的出现的子串{@code deleteStr}全部删除，删除的字符串不支持正则表达式.
     * <pre>
     *     Strings.delete("hello world","l");                    = "heo word"
     * </pre>
     *
     * @param cse       字符串
     * @param deleteStr 要删除的字符串
     * @return 返回删除后的字符串
     * @see #replace(CharSequence, CharSequence, CharSequence)
     */
    public static String delete(CharSequence cse, CharSequence deleteStr) {
        return replace(cse, deleteStr, EMPTY_STRING);
    }

    /**
     * 删除字符串指定位置区域的字符
     *
     * @param cse   字符串
     * @param start 开始位置（包含）
     * @param end   结束位置（包含）
     * @return 新的字符串
     */
    public static String delete(CharSequence cse, int start, int end) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        final int strLength = cse.length();
        if (start > strLength) {
            return str(cse);
        }
        if (end < 0) {
            end = strLength + end;
        }
        if (end >= strLength) {
            end = strLength;
        }
        if (start > end) {
            // 如果起始位置大于结束位置不替换
            return str(cse);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            if (!(i >= start && i <= end)) {
                sb.append(cse.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 删除字符串的前缀，忽略前缀大小写，如果前缀不匹配则返回原始字符串
     *
     * @param cse    待处理的字符串
     * @param prefix 前缀字符串
     * @return 处理后的字符串
     */
    public static String deletePrefixIgnoreCase(CharSequence cse, CharSequence prefix) {
        if (startsWithIgnoreCase(cse, prefix)) {
            return str(cse).substring(prefix.length());
        }
        return str(cse);
    }

    /**
     * 删除字符串的前缀，忽略前缀大小写，如果前缀不匹配则返回原始字符串
     *
     * @param cse    待处理的字符串
     * @param prefix 前缀字符串
     * @return 处理后的字符串
     */
    public static String deletePrefix(CharSequence cse, CharSequence prefix) {
        if (isAnyEmpty(cse, prefix)) {
            return str(cse);
        }
        String str = cse.toString();
        if (str.startsWith(prefix.toString())) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * 删除指定后缀
     *
     * @param cse    字符串
     * @param suffix 后缀字符串
     * @return 新的字符串
     */
    public static String deleteSuffix(CharSequence cse, CharSequence suffix) {
        if (isAnyEmpty(cse, suffix)) {
            return str(cse);
        }
        String str = cse.toString();
        if (str.endsWith(suffix.toString())) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toUpperCase(char)}中，否则就不会改变)转换为大写
     * <pre>
     *     Strings.capitalize("she is just a kid");              = "She is just a kid"
     *     Strings.capitalize("She is just a kid");              = "She is just a kid"
     *     Strings.capitalize("我只是一个孩纸");                   = "我只是一个孩纸"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回字符串中的第一个字符转换为大写
     * @see #changeCharacterCase(CharSequence, int, boolean)
     */
    public static String capitalize(CharSequence cse) {
        return changeCharacterCase(cse, 0, true);
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toLowerCase(char)}中，否则就不会改变)转换为小写
     * <pre>
     *     Strings.unCapitalize("she is just a kid");              = "she is just a kid"
     *     Strings.unCapitalize("She is just a kid");              = "she is just a kid"
     *     Strings.unCapitalize("Happy New Year");                 = "happy New Year"
     *     Strings.unCapitalize("我只是一个孩纸");                   = "我只是一个孩纸"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回字符串中的第一个字符转换为小写
     * @see #changeCharacterCase(CharSequence, int, boolean)
     */
    public static String unCapitalize(CharSequence cse) {
        return changeCharacterCase(cse, 0, false);
    }

    /**
     * 将字符串中指定位置({@code index})的字符(必须在{@linkplain Character#toLowerCase(char)})转为大(小)写,否则就不会改变
     * <pre>
     *     Strings.changeCharacterCase("Java",1,true);           = "JAva"
     * </pre>
     *
     * @param cse     字符串
     * @param index   大小写更改位置
     * @param capital 大写还是小写，当值为{@code true}时则大写，值为{@code false}时则小写
     * @return 返回修改后的字符串
     */
    public static String changeCharacterCase(CharSequence cse, int index, boolean capital) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        int pos;
        if (index < 0) {
            pos = cse.length() + index;
        } else {
            pos = index;
        }
        StringBuilder sb = new StringBuilder(cse.length());
        sb.append(cse.subSequence(0, pos));
        if (capital) {
            sb.append(Character.toUpperCase(cse.charAt(pos)));
        } else {
            sb.append(Character.toLowerCase(cse.charAt(pos)));
        }
        sb.append(cse.toString().substring(pos + 1));
        return sb.toString();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线字符串
     * <pre>
     *     Strings.camelCaseToUnderline("helloWorld");          = hello_word
     * </pre>
     *
     * @param cse 驼峰式的字符串
     * @return 返回下划线的字符串
     */
    public static String camelCaseToUnderline(CharSequence cse) {
        if (cse == null) {
            return null;
        }
        final int length = cse.length();
        if (length == 0) {
            return str(cse);
        }

        final StringBuilder builder = new StringBuilder(length + 10);
        char ch;
        int nextPos;
        for (int i = 0; i < length; i++) {
            ch = cse.charAt(i);
            if (Character.isUpperCase(ch)) {
                nextPos = i + 1;
                if (nextPos == length) {
                    builder.append(Character.toLowerCase(ch));
                    continue;
                }
                if (Character.isLowerCase(cse.charAt(nextPos))) {
                    if (i != 0) {
                        builder.append(UNDERLINE);
                    }
                }
                builder.append(Character.toLowerCase(cse.charAt(i)));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * 将下划线命名的字符串转换为驼峰式
     * <pre>
     *     Strings.underlineToCamel("hello_world");           = "helloWorld"
     * </pre>
     *
     * @param name 下划线式字符串
     * @return 返回驼峰式字符串
     */
    public static String underlineToCamel(CharSequence name) {
        if (isEmpty(name)) {
            return str(name);
        }

        final int length = name.length();
        final char[] charArray = name.toString().toCharArray();
        final StringBuilder builder = new StringBuilder(length);

        int pos = 0;
        while (pos < length && charArray[pos] == Chars.UNDERLINE) {
            builder.append(charArray[pos]);
            pos++;
            if (pos < length && charArray[pos] != Chars.UNDERLINE) {
                break;
            }
        }

        char ch;
        while (pos < length) {
            ch = charArray[pos];
            if (ch != Chars.UNDERLINE) {
                builder.append(ch);
            } else {
                pos++;
                if (pos == length) {
                    builder.append(ch);
                    continue;
                }
                if (charArray[pos] != Chars.UNDERLINE) {
                    builder.append(Character.toUpperCase(charArray[pos]));
                } else {
                    builder.append(charArray[pos - 1]).append(charArray[pos]);
                }
            }
            pos++;
        }
        return builder.toString();
    }

    /**
     * 重复某个字符
     *
     * <pre>
     * Strings.repeat('e', 0)  = ""
     * Strings.repeat('e', 3)  = "eee"
     * Strings.repeat('e', -2) = ""
     * </pre>
     *
     * @param ch    被重复的字符
     * @param times 重复的次数，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char ch, int times) {
        if (times <= 0) {
            return EMPTY_STRING;
        }
        char[] result = new char[times];
        for (int i = 0; i < times; i++) {
            result[i] = ch;
        }
        return String.valueOf(result);
    }

    /**
     * 根据长度截取
     *
     * @param cse    字符串
     * @param length 长度
     * @return 截取后的字符串
     */
    public static String substring(CharSequence cse, int length) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        if (length <= 0) {
            return EMPTY_STRING;
        }
        String str = cse.toString();
        int realLength = str.length();
        return (realLength >= length) ? cse.toString().substring(0, length) : str;
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param cse   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     */
    public static String removeAll(CharSequence cse, char... chars) {
        if (isEmpty(cse) || chars == null || chars.length == 0) {
            return str(cse);
        }
        final int length = cse.length();
        final StringBuilder sb = new StringBuilder(length);
        char ch;
        for (int i = 0; i < length; i++) {
            ch = cse.charAt(i);
            if (!contains(chars, ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    /**
     * 截取分隔符字符串之前的字符串，不包含分隔符字符串，分隔符字符串出现多次匹配第一次找到的。
     * 如果给定的字符串为空(null或"")或分隔符为null，则返回原字符串
     * 如果分隔符字符串为""，则返回空串，如果分隔符字符串未找到，则返回原字符串。
     * <pre>
     * Strings.substrBefore(null, *, false)      = null
     * Strings.substrBefore("", *, false)        = ""
     * Strings.substrBefore("abc", "a", false)   = ""
     * Strings.substrBefore("abcba", "b", false) = "a"
     * Strings.substrBefore("abc", "c", false)   = "ab"
     * Strings.substrBefore("abc", "d", false)   = "abc"
     * Strings.substrBefore("abc", "", false)    = ""
     * Strings.substrBefore("abc", null, false)  = "abc"
     * </pre>
     *
     * @param charSeq         被查找的字符串
     * @param separator       分隔符字符串（不包含）
     * @param isLastSeparator 是否从后往前找分隔符字符串
     * @return 切割后的字符串
     */
    public static String substrBefore(CharSequence charSeq, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(charSeq) || separator == null) {
            return charSeq == null ? null : charSeq.toString();
        }

        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return EMPTY_STRING;
        }

        final String str = charSeq.toString();
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (-1 == pos) {
            return str;
        }
        if (pos == 0) {
            return EMPTY_STRING;
        }
        return str.substring(0, pos);
    }

    // endregion


    // region 分割字符串

    /**
     * 用英文逗号分割字符串
     *
     * @param cse 被分割的字符串
     * @return 分割后的字符串数组
     */
    public static String[] split(CharSequence cse) {
        return split(cse, Chars.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param cse       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串数组
     */
    public static String[] split(CharSequence cse, char separator) {
        return split(cse, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串数组
     */
    public static String[] split(CharSequence cse, char separator, boolean ignoreEmpty) {
        return split(cse, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *     Strings.split(null)                                                                  =   null
     *     Strings.split("")                                                                    =   []
     *     Strings.split("\t")                                                                  =   [\t]
     *     Strings.split("hello,world")                                                         =   [hello,world]
     *     Strings.split("hello,")                                                              =   [hello,]
     *     Strings.split(",hello,,")                                                            =   [,hello,,]
     *     Strings.split(",hello,,",CharUtil.COMMA,true)                                       =   [hello]
     *     Strings.split(", hello a ,\teverything , will ,be\n,ok",CharUtil.COMMA,true,true)   =   [hello a,everything,will,be,ok]
     *
     * </pre>
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串数组
     */
    @SuppressWarnings("Duplicates")
    public static String[] split(CharSequence cse, char separator, boolean ignoreEmpty, boolean isTrim) {
        if (cse == null) {
            return null;
        }

        final List<String> list = splitToList(cse, separator, ignoreEmpty, isTrim);

        return list.toArray(Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 分割字符串
     *
     * @param cse       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串数组
     */
    public static String[] split(CharSequence cse, CharSequence separator) {
        return split(cse, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串数组
     */
    public static String[] split(CharSequence cse, CharSequence separator, boolean ignoreEmpty) {
        return split(cse, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *    Strings.split("Best wish for you\nThanks.",null)      =   [Best,wish,for,you,Thanks.]
     *    Strings.split("Mac##iPad#iPad Pro##iPhone","##")      =   [Mac,iPad#iPad Pro,iPhone]
     *    Strings.split("\t筷子 || 笔记 || 随|便||",true,true)    =   [筷子,笔记,随|便]
     * </pre>
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串数组
     */
    @SuppressWarnings("Duplicates")
    public static String[] split(CharSequence cse, CharSequence separator, boolean ignoreEmpty, boolean isTrim) {
        if (cse == null) {
            return null;
        }

        final List<String> list = splitToList(cse, separator, ignoreEmpty, isTrim);

        return list.toArray(Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 基于收个匹配的字符分隔，匹配成功得到的是长度为2的数组
     * 如果分隔符不匹配则返回{@code null}
     *
     * @param str       待分隔的字符串
     * @param separator 分隔符
     * @return 分隔后的字符串
     */
    public static String[] splitAtFirst(CharSequence str, char separator) {
        if (str == null) {
            return null;
        }
        String value = str(str);
        int index = value.indexOf(separator);
        if (index == -1) {
            return null;
        }
        if (index == 0) {
            return new String[]{Strings.EMPTY_STRING, value};
        }
        return new String[]{value.substring(0, index), value.substring(index + 1)};
    }

    /**
     * 用英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(CharSequence str) {
        return splitToList(str, Chars.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(CharSequence str, char separator) {
        return splitToList(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(CharSequence str, char separator, boolean ignoreEmpty) {
        return splitToList(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *     Strings.split(null)                                                                  =   null
     *     Strings.split("")                                                                    =   []
     *     Strings.split("\t")                                                                  =   [\t]
     *     Strings.split("hello,world")                                                         =   [hello,world]
     *     Strings.split("hello,")                                                              =   [hello,]
     *     Strings.split(",hello,,")                                                            =   [,hello,,]
     *     Strings.split(",hello,,",CharUtil.COMMA,true)                                       =   [hello]
     *     Strings.split(", hello a ,\teverything , will ,be\n,ok",CharUtil.COMMA,true,true)   =   [hello a,everything,will,be,ok]
     *
     * </pre>
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<String> splitToList(CharSequence cse, char separator, boolean ignoreEmpty, boolean isTrim) {
        if (cse == null) {
            return null;
        }
        final int length = cse.length();
        if (length == 0) {
            return Collections.emptyList();
        }
        String str = cse.toString();

        final List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == separator) {
                addToList(list, str.substring(start, i), ignoreEmpty, isTrim);
                start = i + 1;
            }
        }

        addToList(list, str.substring(start, length), ignoreEmpty, isTrim);

        return list;
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(CharSequence str, CharSequence separator) {
        return splitToList(str, separator, false);
    }

    /**
     * 分割字符串
     *
     * @param str         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @return 分割后的字符串集合列表
     */
    public static List<String> splitToList(CharSequence str, CharSequence separator, boolean ignoreEmpty) {
        return splitToList(str, separator, ignoreEmpty, false);
    }

    /**
     * 分割字符串
     * <pre>
     *    Strings.split("Best wish for you\nThanks.",null)      =   [Best,wish,for,you,Thanks.]
     *    Strings.split("Mac##iPad#iPad Pro##iPhone","##")      =   [Mac,iPad#iPad Pro,iPhone]
     *    Strings.split("\t筷子 || 笔记 || 随|便||",true,true)    =   [筷子,笔记,随|便]
     * </pre>
     *
     * @param cse         被分割的字符串
     * @param separator   分隔符字符
     * @param ignoreEmpty 是否忽略空串
     * @param isTrim      是否去除分割字符串后每个元素两边的空白
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<String> splitToList(CharSequence cse, CharSequence separator, boolean ignoreEmpty, boolean isTrim) {
        if (cse == null) {
            return null;
        }
        final int length = cse.length();
        if (length == 0) {
            return Collections.emptyList();
        }
        String str = cse.toString(), sepCs = str(separator);

        final List<String> list = new ArrayList<>();
        int start = 0, index;
        if (isEmpty(separator)) {
            //按照空白分割
            for (index = 0; index < length; index++) {
                if (Character.isWhitespace(str.charAt(index))) {
                    addToList(list, str.substring(start, index), ignoreEmpty, isTrim);
                    start = index + 1;
                }
            }
            addToList(list, str.substring(start, length), ignoreEmpty, isTrim);
        } else if (separator.length() == 1) {
            return splitToList(str, separator.charAt(0), ignoreEmpty, isTrim);
        } else {
            index = 0;
            int sepLen = separator.length();
            while (index < length) {
                index = str.indexOf(sepCs, start);
                if (index > -1) {
                    addToList(list, str.substring(start, index), ignoreEmpty, isTrim);
                    start = index + sepLen;
                } else {
                    break;
                }
            }
            addToList(list, str.substring(start, length), ignoreEmpty, isTrim);
        }

        return list;
    }


    /**
     * 用英文逗号分割字符串
     *
     * @param str 被分割的字符串
     * @return 分割后的字符串集合列表
     */
    public static List<Integer> splitToIntList(CharSequence str) {
        return splitToIntList(str, Chars.COMMA);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<Integer> splitToIntList(CharSequence str, char separator) {
        if (str == null) {
            return null;
        }

        final List<String> strList = splitToList(str, separator, true, true);
        if (strList == null || strList.size() == 0) {
            return Collections.emptyList();
        }

        return str2IntList(strList);
    }

    /**
     * 分割字符串
     *
     * @param str       被分割的字符串
     * @param separator 分隔符字符
     * @return 分割后的字符串集合列表
     */
    @SuppressWarnings("Duplicates")
    public static List<Integer> splitToIntList(CharSequence str, CharSequence separator) {
        if (str == null) {
            return null;
        }
        final List<String> strList = splitToList(str, separator, true, true);
        if (strList == null || strList.size() == 0) {
            return Collections.emptyList();
        }
        return str2IntList(strList);
    }

    /**
     * 将字符串转为数字
     *
     * @param strList 字符串数组
     * @return 数字数组
     */
    private static List<Integer> str2IntList(List<String> strList) {
        return strList.stream().filter(Strings::isNotBlank).map(Strings::trim).map(Integer::valueOf).collect(Collectors.toList());
    }

    private static void addToList(List<String> list, String str, boolean ignoreEmpty, boolean isTrim) {
        if (isTrim) {
            str = trim(str);
        }
        if (!ignoreEmpty || !str.isEmpty()) {
            list.add(str);
        }
    }

    // endregion


    // region 其它方法

    /**
     * 统计指定内容中包含指定字符的次数
     *
     * @param cse     字符串
     * @param matchCh 匹配的字符
     * @return 出现的次数
     */
    public static int count(CharSequence cse, char matchCh) {
        int count = 0;
        if (isEmpty(cse)) {
            return count;
        }
        int length = cse.length();
        for (int i = 0; i < length; i++) {
            if (matchCh == cse.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取字符串的字节内容，编码为空采用系统默认编码
     *
     * @param cs       字符串
     * @param encoding 编码，可以为空
     * @return 字节码内容
     */
    public static byte[] bytes(CharSequence cs, Charset encoding) {
        if (cs == null) {
            return null;
        }
        return (encoding == null) ? cs.toString().getBytes() : cs.toString().getBytes(encoding);
    }

    /**
     * 将字符串结合对象转为字符串数组
     *
     * @param collection {@linkplain Collection} or {@code null}
     * @return 非{@code null}的字符串数组
     */
    public static String[] toStringArray(Collection<CharSequence> collection) {
        return (Collections.isEmpty(collection) ? Arrays.EMPTY_STRING_ARRAY : collection.stream().map(Strings::str).toArray(String[]::new));
    }

    // endregion


    // region 填充

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串<br>
     * 字符填充于字符串前
     *
     * @param str    被填充的字符串
     * @param ch     填充的字符
     * @param length 填充长度
     * @return 填充后的字符串
     */
    public static String fillHead(CharSequence str, char ch, int length) {
        return fill(str, ch, length, true);
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串<br>
     * 字符填充于字符串后
     *
     * @param str    被填充的字符串
     * @param ch     填充的字符
     * @param length 填充长度
     * @return 填充后的字符串
     */
    public static String fillTail(CharSequence str, char ch, int length) {
        return fill(str, ch, length, false);
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串
     *
     * @param str          被填充的字符串
     * @param ch           填充的字符
     * @param length       填充长度
     * @param appendToHead 是否填充在前
     * @return 填充后的字符串
     */
    public static String fill(CharSequence str, char ch, int length, boolean appendToHead) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        int times = length - strLen;
        if (times <= 0) {
            return str(str);
        }
        String repeatStr = repeat(ch, times);
        return appendToHead ? (repeatStr.concat(str(str))) : str(str).concat(repeatStr);
    }

    /**
     * 如果给定字符串不是以{@code suffix}结尾的，在尾部补充{@code suffix}
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     */
    public static String appendIfNot(CharSequence str, CharSequence suffix) {
        if (isAnyEmpty(str, suffix)) {
            return str(str);
        }
        String csr = str(str), sufCs = str(suffix);
        if (!csr.endsWith(sufCs)) {
            return csr.concat(sufCs);
        }
        return csr;
    }

    /**
     * 如果给定字符串不是以{@code suffix}结尾的，在尾部补充{@code suffix}
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     * @see #appendIfNot(CharSequence, CharSequence)
     */
    public static String addSuffixIfNot(CharSequence str, CharSequence suffix) {
        return appendIfNot(str, suffix);
    }

    // endregion


}
