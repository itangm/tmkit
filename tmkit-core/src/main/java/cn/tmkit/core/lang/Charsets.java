package cn.tmkit.core.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Charsets {

    /**
     * ISO-8859-1编码名
     */
    public static final String ISO_8859_1_VALUE = "ISO-8859-1";

    /**
     * UTF-8编码名
     */
    public static final String UTF_8_VALUE = "UTF-8";

    /**
     * GBK编码名
     */
    public static final String GBK_VALUE = "GBK";

    /**
     * ISO-8859-1编码对象
     */
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

    /**
     * UTF-8编码对象
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * GBK编码对象
     */
    public static final Charset GBK = Charset.forName(GBK_VALUE);

    /**
     * 平台默认编码
     */
    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static @NotNull Charset defaultCharset() {
        return DEFAULT_CHARSET;
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static @NotNull String defaultCharsetName() {
        return DEFAULT_CHARSET.name();
    }

    /**
     * 转换为{@linkplain Charset}对象
     *
     * @param charsetName 字符编码，如果为空则返回UTF-8编码
     * @return {@link Charset}
     */
    public static @NotNull Charset forName(String charsetName) {
        return forName(charsetName, Charsets.UTF_8);
    }

    /**
     * 转换为{@linkplain Charset}对象
     *
     * @param charsetName 字符编码，如果为空则返回默认编码
     * @return {@link Charset}
     */
    public static Charset forName(String charsetName, Charset defaultEncoding) {
        return StringUtil.isBlank(charsetName) ? defaultEncoding : Charset.forName(charsetName);
    }

    /**
     * 返回字符编码，如果为{@code} null}则返回平台默认编码
     *
     * @param encoding 编码
     * @return 编码
     */
    public static Charset getCharset(@Nullable Charset encoding) {
        return getCharset(encoding, defaultCharset());
    }

    /**
     * 返回字符编码，如果为{@code} null}则返回指定默认编码
     *
     * @param encoding        编码
     * @param defaultEncoding 当{@code encoding}为空时
     * @return 编码
     */
    public static Charset getCharset(@Nullable Charset encoding, Charset defaultEncoding) {
        return encoding == null ? defaultEncoding : encoding;
    }

    /**
     * 解析编码，如果解析失败返回默认编码
     *
     * @param encoding        编码字符串
     * @param defaultEncoding 默认编码
     * @return 解析后的编码
     */
    public static Charset parseQuietly(final String encoding, Charset defaultEncoding) {
        if (encoding != null) {
            try {
                return Charset.forName(encoding);
            } catch (UnsupportedCharsetException e) {
                // ignore
            }
        }
        return defaultEncoding;
    }

}
