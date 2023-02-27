package cn.tmkit.core.digest;

import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Hexes;
import cn.tmkit.core.lang.reflect.Singletons;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * SHA Utilities
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class SHAs {

    private static final MessageDigestCreator CREATOR = Singletons.get(MessageDigestCreator.class, MessageDigestAlgorithm.SHA1);

    //region ----------------------- SHA1 -----------------------

    /**
     * SHA1摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha1(final byte[] data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA1摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha1(final InputStream data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final String data) {
        return sha1Hex(data, null);
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data          待处理的数据
     * @param inputEncoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha1Hex(final String data, final Charset inputEncoding) {
        return sha1Hex(Objects.requireNonNull(data).getBytes(Charsets.getCharset(inputEncoding)));
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final byte[] data) {
        return Hexes.encodeToStr(sha1(data));
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final InputStream data) {
        return Hexes.encodeToStr(sha1(data));
    }

    //endregion

    //region ----------------------- SHA256 -----------------------

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final String data) {
        return sha256(Objects.requireNonNull(data).getBytes(Charsets.defaultCharset()));
    }

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final byte[] data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final InputStream data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final String data) {
        return Hexes.encodeToStr(sha256(data));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha256Hex(final String data, final Charset encoding) {
        return sha256Hex(Objects.requireNonNull(data).getBytes(Charsets.getCharset(encoding)));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     * x
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final byte[] data) {
        return Hexes.encodeToStr(sha256(data));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final InputStream data) {
        return Hexes.encodeToStr(sha256(data));
    }

    //endregion

    //region----------------------- SHA384 -----------------------

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     * @see #sha384(String, Charset)
     */
    public static byte[] sha384(final String data) {
        return sha384(data, null);
    }

    /**
     * SHA384摘要计算
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 计算后的数据
     */
    public static byte[] sha384(final String data, final Charset encoding) {
        return sha384(Objects.requireNonNull(data).getBytes(Charsets.getCharset(encoding)));
    }

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha384(final byte[] data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha384(final InputStream data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final String data) {
        return Hexes.encodeToStr(sha384(data));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha384Hex(final String data, final Charset encoding) {
        return Hexes.encodeToStr(sha384(data, encoding));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final byte[] data) {
        return Hexes.encodeToStr(sha384(data));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final InputStream data) {
        return Hexes.encodeToStr(sha384(data));
    }

    //endregion

    //region----------------------- SHA512 -----------------------

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     * @see #sha512(String, Charset)
     */
    public static byte[] sha512(final String data) {
        return sha512(data, null);
    }

    /**
     * SHA512摘要计算
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 计算后的数据
     */
    public static byte[] sha512(final String data, final Charset encoding) {
        return sha512(Objects.requireNonNull(data).getBytes(Charsets.getCharset(encoding)));
    }

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha512(final byte[] data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha512(final InputStream data) {
        return CREATOR.digest(Objects.requireNonNull(data));
    }

    /**
     * SHA512摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final String data) {
        return Hexes.encodeToStr(sha512(data));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha512Hex(final String data, final Charset encoding) {
        return Hexes.encodeToStr(sha512(data, encoding));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final byte[] data) {
        return Hexes.encodeToStr(sha512(data));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final InputStream data) {
        return Hexes.encodeToStr(sha512(data));
    }

    //endregion

}
