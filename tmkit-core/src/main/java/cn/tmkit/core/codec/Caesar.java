package cn.tmkit.core.codec;

import cn.tmkit.core.lang.Chars;

/**
 * 凯撒密码的实现
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-10-26
 */
public class Caesar {

    private static final String TABLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 编码为凯撒字符串
     *
     * @param message 编码前的字符串
     * @param offset  偏移量
     * @return 编码后的字符串
     */
    public static String encode(String message, int offset) {
        StringBuilder sb = new StringBuilder(message.length());
        char ch;
        for (int i = 0, pos; i < message.length(); i++) {
            ch = message.charAt(i);
            if (Chars.isLetter(ch)) {
                pos = (TABLES.indexOf(ch) + offset) % TABLES.length();
                sb.append(TABLES.charAt(pos));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 解码凯撒字符串
     *
     * @param message 凯撒字符串
     * @param offset  偏移量
     * @return 解码后的字符串
     */
    public static String decode(String message, int offset) {
        StringBuilder sb = new StringBuilder(message.length());
        char ch;
        for (int i = 0, pos; i < message.length(); i++) {
            ch = message.charAt(i);
            if (Chars.isLetter(ch)) {
                pos = (TABLES.indexOf(ch) - offset) % TABLES.length();
                if (pos < 0) {
                    pos += TABLES.length();
                }
                sb.append(TABLES.charAt(pos));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

}
