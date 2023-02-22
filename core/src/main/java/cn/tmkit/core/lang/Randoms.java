package cn.tmkit.core.lang;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Randoms {

    /**
     * 用于随机选的数字
     */
    public static final String BASE_NUMBERS = "0123456789";

    /**
     * 用于随机选的字符
     */
    public static final String BASE_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 用于随机选的字符和数字
     */
    public static final String BASE_CHAR_NUMBERS = BASE_CHARS + BASE_NUMBERS;

    /**
     * 获取随机数生成器对象<br>
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * 获得随机Boolean值
     *
     * @return true or false
     */
    public static boolean nextBoolean() {
        return getRandom().nextBoolean();
    }

    /**
     * 获得随机数[0, 2^32)
     *
     * @return 随机数
     */
    public static int nextInt() {
        return getRandom().nextInt();
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public static int nextInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static int nextInt(int limit) {
        return getRandom().nextInt(limit);
    }

    /**
     * 获得随机数
     *
     * @return 随机数
     */
    public static long nextLong() {
        return getRandom().nextLong();
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static long nextLong(long limit) {
        return getRandom().nextLong(limit);
    }

    /**
     * 获得指定范围内的随机数[min, max)
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public static long nextLong(long min, long max) {
        return getRandom().nextLong(min, max);
    }

    /**
     * 获得随机数[0, 1)
     *
     * @return 随机数
     */
    public static double nextDouble() {
        return getRandom().nextDouble();
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static double nextDouble(double limit) {
        return getRandom().nextDouble(limit);
    }

    /**
     * 获得指定范围内的随机数[min, max)
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public static double nextDouble(double min, double max) {
        return getRandom().nextDouble(min, max);
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>  元素类型
     * @param list 列表
     * @return 随机元素
     */
    public static <T> T element(List<T> list) {
        return element(list, list.size());
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>  元素类型
     * @param list 列表
     * @param max  限制列表的前N项
     * @return 随机元素
     */
    public static <T> T element(List<T> list, int max) {
        return list.get(nextInt(max));
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @return 随机元素
     */
    public static <T> T element(T[] array) {
        return element(array, array.length);
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @param max   限制列表的前N项
     * @return 随机元素
     */
    public static <T> T element(T[] array, int max) {
        return array[nextInt(max)];
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符）
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String str(int length) {
        return str(BASE_CHAR_NUMBERS, length);
    }

    /**
     * 获得一个随机的字符串
     *
     * @param baseString 随机字符选取的样本
     * @param length     字符串的长度
     * @return 随机字符串
     */
    public static String str(String baseString, int length) {
        if (Strings.isEmpty(baseString)) {
            return Strings.EMPTY_STRING;
        }
        final StringBuilder sb = new StringBuilder(length);

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获得一个随机的数字字符串，比如6位验证码
     *
     * @param length 字符串的长度
     * @return 随机的数字字符串
     */
    public static String numberStr(int length) {
        return str(BASE_NUMBERS, length);
    }

    /**
     * 随机获得一个字符串，过滤可读性比较困难的字符（0 o O l 1 I i)
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String strReadable(int length) {
        String excludeStr = "oO0l1Ii";
        return strExcludeStr(length, excludeStr);
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符） 并排除指定字符串
     *
     * @param length     字符串的长度
     * @param excludeStr 要排除的字符串
     * @return 随机字符串
     */
    public static String strExcludeStr(int length, String excludeStr) {
        if (Strings.isEmpty(excludeStr)) {
            return str(length);
        }
        String baseStr = Strings.removeAll(BASE_CHAR_NUMBERS, excludeStr.toCharArray());
        return str(baseStr, length);
    }

}
