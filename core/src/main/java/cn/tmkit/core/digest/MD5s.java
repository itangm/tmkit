package cn.tmkit.core.digest;

import cn.tmkit.core.lang.Asserts;
import cn.tmkit.core.lang.Base64s;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.HexUtil;
import cn.tmkit.core.lang.reflect.Singletons;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * MD5工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class MD5s {

    private static final MessageDigestCreator CREATOR = Singletons.get(MessageDigestCreator.class, MessageDigestAlgorithm.MD5);

    /**
     * md5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final byte[] data) {
        Asserts.notEmpty(data);
        return CREATOR.digest(data);
    }

    /**
     * 将输入流的数据进行MD5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final InputStream data) {
        Asserts.notNull(data);
        return CREATOR.digest(data);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的小写字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final String data) {
        return digestHex(data, null);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的大写字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestUpperHex(final String data) {
        return digestUpperHex(data, null);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的小写字符串返回
     *
     * @param data          待计算的数据
     * @param inputEncoding 编码
     * @return 16进制的字符串
     */
    public static String digestHex(final String data, final Charset inputEncoding) {
        Asserts.notNull(data);
        byte[] input = data.getBytes(Charsets.getCharset(inputEncoding));
        return HexUtil.encodeToStr(digest(input));
    }

    /**
     * 计算字符串的md5值，结果转为16进制的大写字符串返回
     *
     * @param data          待计算的数据
     * @param inputEncoding 编码
     * @return 16进制的字符串
     */
    public static String digestUpperHex(final String data, final Charset inputEncoding) {
        Asserts.notNull(data);
        byte[] input = data.getBytes(Charsets.getCharset(inputEncoding));
        return HexUtil.encodeToStr(digest(input), false);
    }

    /**
     * 计算的流的md5值，结果转为16进制的字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final InputStream data) {
        return HexUtil.encodeToStr(digest(data));
    }

    /**
     * 计算字符串(默认UTF8)的MD5值，将结果转为BASE64输出
     *
     * @param data 待计算的数据
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data) {
        return digestBase64(data, null);
    }

    /**
     * 计算字符串的MD5值，将结果转为BASE64输出
     *
     * @param data           待计算的数据
     * @param outputEncoding 字符串编码
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data, final Charset outputEncoding) {
        Asserts.notNull(data);
        byte[] input = data.getBytes(Charsets.getCharset(outputEncoding));
        return Base64s.encode(digest(input));
    }

}
