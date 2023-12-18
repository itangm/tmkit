package cn.tmkit.core.io;

import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Strings;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * NIO工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-12-18
 */
public class NioUtil {

    /**
     * 读取剩余部分bytes
     *
     * @param buffer ByteBuffer
     * @return bytes
     */
    public static byte[] readBytes(ByteBuffer buffer) {
        return readBytes(buffer, buffer.remaining());
    }

    /**
     * 读取剩余部分并转为UTF-8编码字符串
     *
     * @param buffer ByteBuffer
     * @return 字符串
     */
    public static String readUtf8Str(ByteBuffer buffer) {
        return readStr(buffer, Charsets.UTF_8);
    }

    /**
     * 读取剩余部分并转为字符串
     *
     * @param buffer   ByteBuffer
     * @param encoding 编码
     * @return 字符串
     */
    private static String readStr(ByteBuffer buffer, Charset encoding) {
        return Strings.str(readBytes(buffer), encoding);
    }

    /**
     * 读取指定长度的byte，如果长度不足，则读取剩余部分
     *
     * @param buffer    ByteBuffer
     * @param maxLength 最大长度
     * @return bytes
     */
    public static byte[] readBytes(ByteBuffer buffer, int maxLength) {
        final int remaining = buffer.remaining();
        if (maxLength > remaining) {
            maxLength = remaining;
        }
        byte[] data = new byte[maxLength];
        buffer.get(data);
        return data;
    }

}
