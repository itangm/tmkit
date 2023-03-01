package cn.tmkit.core.convert;

import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Hexes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 转换工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class Converts {

    /**
     * 将源对象{@code value}转为字符串，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        将被转换的值
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String toStr(Object value, String defaultValue) {
        return convert(String.class, value, defaultValue);
    }

    /**
     * 将源对象{@code value}转为字符串，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 将被转换的值
     * @return 字符串
     */
    public static String toStr(Object value) {
        return toStr(value, null);
    }

    /**
     * 转换为字符，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Character toChar(Object value, Character defaultValue) {
        return convert(Character.class, value, defaultValue);
    }

    /**
     * 转换为字符，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    /**
     * 转换为byte，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Byte toByte(Object value, Byte defaultValue) {
        return convert(Byte.class, value, defaultValue);
    }

    /**
     * 转换为byte，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Byte toByte(Object value) {
        return toByte(value, null);
    }

    /**
     * 转换为Short，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Short toShort(Object value, Short defaultValue) {
        return convert(Short.class, value, defaultValue);
    }

    /**
     * 转换为Short，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Short toShort(Object value) {
        return toShort(value, null);
    }

    /**
     * 转换为int，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Integer toInt(Object value, Integer defaultValue) {
        return convert(Integer.class, value, defaultValue);
    }

    /**
     * 转换为int，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer toInt(Object value) {
        return toInt(value, null);
    }

    /**
     * 转换为long，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Long toLong(Object value, Long defaultValue) {
        return convert(Long.class, value, defaultValue);
    }

    /**
     * 转换为long，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long toLong(Object value) {
        return toLong(value, null);
    }

    /**
     * 转换为double，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Double toDouble(Object value, Double defaultValue) {
        return convert(Double.class, value, defaultValue);
    }

    /**
     * 转换为double，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object value) {
        return toDouble(value, null);
    }

    /**
     * 转换为float，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Float toFloat(Object value, Float defaultValue) {
        return convert(Float.class, value, defaultValue);
    }

    /**
     * 转换为float，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object value) {
        return toFloat(value, null);
    }

    /**
     * 转换为boolean，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * String支持的值为：true、false、yes、ok、no，1,0<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Boolean toBool(Object value, Boolean defaultValue) {
        return convert(Boolean.class, value, defaultValue);
    }

    /**
     * 转换为boolean，如果转换失败返回{@code null}<br>
     * String支持的值为：true、false、yes、ok、no，1,0<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object value) {
        return toBool(value, null);
    }

    /**
     * 转换为BigInteger，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
        return convert(BigInteger.class, value, defaultValue);
    }

    /**
     * 转换为BigInteger，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value) {
        return toBigInteger(value, null);
    }

    /**
     * 转换为BigDecimal，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return convert(BigDecimal.class, value, defaultValue);
    }

    /**
     * 转换为BigDecimal，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, null);
    }

    /**
     * 转换为Date，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Date toDate(Object value, Date defaultValue) {
        return convert(Date.class, value, defaultValue);
    }

    /**
     * 转换为Date，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Date toDate(Object value) {
        return toDate(value, null);
    }

    /**
     * LocalDateTime，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalDateTime toLocalDateTime(Object value, LocalDateTime defaultValue) {
        return convert(LocalDateTime.class, value, defaultValue);
    }

    /**
     * LocalDateTime，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalDateTime toLocalDateTime(Object value) {
        return toLocalDateTime(value, null);
    }

    /**
     * LocalDate，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalDate toLocalDate(Object value, LocalDate defaultValue) {
        return convert(LocalDate.class, value, defaultValue);
    }

    /**
     * LocalDate，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalDate toLocalDate(Object value) {
        return toLocalDate(value, null);
    }

    /**
     * LocalTime，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalTime toLocalTime(Object value, LocalTime defaultValue) {
        return convert(LocalTime.class, value, defaultValue);
    }

    /**
     * LocalTime，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalTime toLocalTime(Object value) {
        return toLocalTime(value, null);
    }

    /**
     * Instant，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Instant toInstant(Object value, Instant defaultValue) {
        return convert(Instant.class, value, defaultValue);
    }

    /**
     * Instant，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Instant toInstant(Object value) {
        return toInstant(value, null);
    }

    /**
     * 将源值转为指定类型的值<br>
     * 当{@code quietly}为{@code true}时，如果转换时发生异常返回{@code defaultValue}<br>
     * 当转换结果为{@code null}时，返回默认值{@code defaultValue}
     *
     * @param <T>          目标泛型
     * @param clazz        目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @param quietly      是否静默转换，{@code true}不抛异常
     * @return 转换后的值
     */
    protected static <T> T doConvert(Class<T> clazz, Object value, T defaultValue, boolean quietly) {
        final ConverterRegistry registry = ConverterRegistry.getInstance();
        try {
            return registry.convert(value, clazz, defaultValue);
        } catch (ConverterRuntimeException e) {
            if (quietly) {
                return defaultValue;
            }
            throw e;
        }
    }


    /**
     * 字符串转换成十六进制字符串，结果为小写
     *
     * @param str 待转换的ASCII字符串
     * @return 十六进制字符串
     * @see #toHexStr(String)
     */
    public static String toHexStr(String str) {
        return toHexStr(str, null);
    }

    /**
     * 字符串转换成十六进制字符串，结果为小写
     *
     * @param str     待转换的ASCII字符串
     * @param charset 编码
     * @return 十六进制字符串
     * @see #toHexStr(byte[])
     */
    public static String toHexStr(String str, Charset charset) {
        return toHexStr(str.getBytes(Charsets.getCharset(charset, Charsets.UTF_8)));
    }

    /**
     * byte数组转十六进制串
     *
     * @param bytes 被转换的byte数组
     * @return 转换后的值
     * @see Hexes#encodeToStr(byte[])
     */
    public static String toHexStr(byte[] bytes) {
        return Hexes.encodeToStr(bytes);
    }

    /**
     * Hex字符串转换为Byte值
     *
     * @param src 字节数组
     * @return byte[]
     * @see Hexes#decode(String)
     */
    public static byte[] hexToBytes(String src) {
        return Hexes.decode(src);
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr  Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @param charset 编码 {@link Charset}
     * @return 对应的字符串
     * @see Hexes#decodeToStr(String, Charset)
     */
    public static String hexToStr(String hexStr, Charset charset) {
        return Hexes.decodeToStr(hexStr, charset);
    }

    /**
     * 转换指定类型的值，不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param <T>          目标泛型
     * @param clazz        目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConverterRuntimeException 异常
     */
    public static <T> T convert(Class<T> clazz, Object value, T defaultValue) throws ConverterRuntimeException {
        return doConvert(clazz, value, defaultValue, true);
    }

}
