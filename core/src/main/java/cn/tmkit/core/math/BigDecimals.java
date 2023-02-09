package cn.tmkit.core.math;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Objects;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * {@linkplain java.math.BigDecimal}的工具类
 * <p>Tanks for <a href="https://github.com/ReZeroLuoYi/BigDecimalUtil">BigDecimalUtil</a></p>
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-09
 */
public class BigDecimals {

    /**
     * 多个数相加，保留2位小数，超出部分四舍五入
     *
     * @param a       第一个数
     * @param b       第二个数
     * @param numbers 其它的数
     * @return 结果
     */
    public static BigDecimal add(@NotNull Object a, @NotNull Object b, Object... numbers) {
        FluentBigDecimal result = FluentBigDecimal.of(a.toString()).add(b.toString());
        if (Arrays.isNotEmpty(numbers)) {
            for (Object number : numbers) {
                if (Objects.isNotNull(number)) {
                    if (number instanceof FluentBigDecimal) {
                        result = result.add((FluentBigDecimal) number);
                    } else {
                        result = result.add(number.toString());
                    }
                }
            }
        }
        return result.getValue();
    }

    /**
     * 多个数相减去，保留2位小数，超出部分四舍五入
     *
     * @param a       第一个数
     * @param b       第二个数
     * @param numbers 其它的数
     * @return 结果
     */
    public static BigDecimal subtract(@NotNull Object a, @NotNull Object b, Object... numbers) {
        FluentBigDecimal result = FluentBigDecimal.of(a.toString()).subtract(b.toString());
        if (Arrays.isNotEmpty(numbers)) {
            for (Object number : numbers) {
                if (Objects.isNotNull(number)) {
                    if (number instanceof FluentBigDecimal) {
                        result = result.subtract((FluentBigDecimal) number);
                    } else {
                        result = result.subtract(number.toString());
                    }
                }
            }
        }
        return result.getValue();
    }

    /**
     * 多个数相减去，保留2位小数，超出部分四舍五入
     *
     * @param a       第一个数
     * @param b       第二个数
     * @param numbers 其它的数
     * @return 结果
     */
    public static BigDecimal multiply(@NotNull Object a, @NotNull Object b, Object... numbers) {
        FluentBigDecimal result = FluentBigDecimal.of(a.toString()).multiply(b.toString());
        if (Arrays.isNotEmpty(numbers)) {
            for (Object number : numbers) {
                if (Objects.isNotNull(number)) {
                    if (number instanceof FluentBigDecimal) {
                        result = result.multiply((FluentBigDecimal) number);
                    } else {
                        result = result.multiply(number.toString());
                    }
                }
            }
        }
        return result.getValue();
    }

    /**
     * 多个数相减去，保留2位小数，超出部分四舍五入
     *
     * @param a       第一个数
     * @param b       第二个数
     * @param numbers 其它的数
     * @return 结果
     */
    public static BigDecimal divide(@NotNull Object a, @NotNull Object b, Object... numbers) {
        FluentBigDecimal result = FluentBigDecimal.of(a.toString()).divide(b.toString());
        if (Arrays.isNotEmpty(numbers)) {
            for (Object number : numbers) {
                if (Objects.isNotNull(number)) {
                    if (number instanceof FluentBigDecimal) {
                        result = result.divide((FluentBigDecimal) number);
                    } else {
                        result = result.divide(number.toString());
                    }
                }
            }
        }
        return result.getValue();
    }

    /**
     * 判断两个数的大小，如果{@code a > b}则返回正整数，如果{@code a < b}则返回负整数，否则返回0
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return 比较结果
     */
    public static int compareTo(@NotNull Object a, @NotNull Object b) {
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

    /**
     * 判断两个数是否相等
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return 是否相等
     */
    public static boolean equals(@NotNull Object a, @NotNull Object b) {
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString())) == 0;
    }

}
