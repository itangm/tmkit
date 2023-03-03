package cn.tmkit.core.math;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * 流式的{@linkplain BigDecimal}，默认精度(7位)，默认舍弃模式为{@linkplain RoundingMode#HALF_UP}
 * <p>Thanks for <a href="https://github.com/HonoluluHenk/fluent-bigdecimals">fluent-bigdecimals</a></p>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-07
 */
@Getter
public class FluentBigDecimal extends Number implements Serializable, Comparable<FluentBigDecimal> {

    /**
     * store value
     */
    private final @NotNull BigDecimal value;

    /**
     * The scale of this BigDecimal,
     */
    private final @NotNull FluentConfig fluentConfig;

    FluentBigDecimal(@NotNull BigDecimal value) {
        this(value, FluentConfig.DEFAULT);
    }

    FluentBigDecimal(@NotNull BigDecimal value, @NotNull FluentConfig fluentConfig) {
        this.value = value;
        this.fluentConfig = fluentConfig;
    }

    // region Create Instance

    /**
     * 根据字符串内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符串内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull Number input) {
        return of(input, null);
    }

    /**
     * 根据字符串内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符串内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull Number input, FluentConfig config) {
        return of(input.toString(), config);
    }

    /**
     * 根据字符串内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符串内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull String input) {
        return of(input, null);
    }

    /**
     * 根据字符串内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符串内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull String input, FluentConfig config) {
        return of(new BigDecimal(input), config);
    }

    /**
     * 根据{@linkplain BigDecimal}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input BigDecimal
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull BigDecimal input) {
        return of(input, null);
    }

    /**
     * 根据{@linkplain BigDecimal}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input BigDecimal
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull BigDecimal input, FluentConfig config) {
        return (config == null) ? new FluentBigDecimal(input) : new FluentBigDecimal(input, config);
    }

    /**
     * 根据字符数组内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符数组内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(char @NotNull [] input) {
        return of(input, null);
    }

    /**
     * 根据字符数组内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 字符数组内容
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(char @NotNull [] input, FluentConfig config) {
        return of(new BigDecimal(input), config);
    }

    /**
     * 根据字符数组内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input  字符数组内容
     * @param offset 数组中的位置
     * @param len    包含几个字符
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(char @NotNull [] input, int offset, int len) {
        return of(input, offset, len, null);
    }

    /**
     * 根据字符数组内容构造一个{@linkplain FluentBigDecimal}
     *
     * @param input  字符数组内容
     * @param offset 数组中的位置
     * @param len    包含几个字符
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(char @NotNull [] input, int offset, int len, FluentConfig config) {
        return of(new BigDecimal(input, offset, len), config);
    }

    /**
     * 根据{@linkplain BigInteger}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input BigInteger
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull BigInteger input) {
        return of(input, null);
    }

    /**
     * 根据{@linkplain BigInteger}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input BigInteger
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal of(@NotNull BigInteger input, FluentConfig config) {
        return of(new BigDecimal(input), config);
    }

    /**
     * 根据整数构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 整数
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(int input) {
        return valueOf(input, null);
    }

    /**
     * 根据整数构造一个{@linkplain FluentBigDecimal}
     *
     * @param input 整数
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(int input, FluentConfig config) {
        return of(BigDecimal.valueOf(input), config);
    }

    /**
     * 根据{@code long}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input long
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(long input) {
        return valueOf(input, null);
    }

    /**
     * 根据{@code long}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input long
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(long input, FluentConfig config) {
        return of(BigDecimal.valueOf(input), config);
    }

    /**
     * 根据{@code double}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input double
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(double input) {
        return valueOf(input, null);
    }

    /**
     * 根据{@code double}构造一个{@linkplain FluentBigDecimal}
     *
     * @param input double
     * @return {@linkplain FluentBigDecimal}
     */
    public static FluentBigDecimal valueOf(double input, FluentConfig config) {
        return of(BigDecimal.valueOf(input), config);
    }

    // endregion

    // region Addition

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Short value) {
        return value == null ? this : add(value.longValue());
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (short value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addShorts(Collection<Short> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Integer value) {
        return value == null ? this : add(value.intValue());
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(int... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Integer... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addIntegers(Collection<Integer> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Float value) {
        return value == null ? this : add(value.doubleValue());
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (float value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addFloats(Collection<Float> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(long value) {
        return add(BigDecimal.valueOf(value));
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Long value) {
        return value == null ? this : add(new BigDecimal(value));
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (long value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addLongs(Collection<Long> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(double value) {
        return add(Double.toString(value));
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Double value) {
        return value != null ? add(new BigDecimal(value)) : this;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (double value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addDoubles(Collection<Double> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Number value) {
        return value != null ? add(value.toString()) : this;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Number... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Number value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addNumbers(Collection<Number> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Number value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加一个数
     *
     * @param val 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(String val) {
        //非空
        if (Strings.hasText(val)) {
            return add(new BigDecimal(Strings.trim(val)));
        }
        return this;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(String... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addStrings(Collection<String> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param value 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(BigDecimal value) {
        if (value != null) {
            return apply(BigDecimal::add, value);
        }
        return this;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(BigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal addBigDecimals(Collection<BigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param other 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(FluentBigDecimal other) {
        if (other != null) {
            return add(other.getValue());
        }
        return this;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(FluentBigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (FluentBigDecimal value : values) {
            result = result.add(value);
        }
        return result;
    }

    /**
     * 加法
     *
     * @param values 被加数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal add(Collection<FluentBigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (FluentBigDecimal value : values) {
            result = result.add(value);
        }
        return result;
    }

    // endregion


    // region Subtraction

    /**
     * 减法
     *
     * @param value 数值
     * @return 当前对象
     */
    public FluentBigDecimal subtract(Short value) {
        return value == null ? this : subtract(value.longValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (short value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractShorts(Collection<Short> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (short value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Integer value) {
        return value == null ? this : subtract(value.longValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(int... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Integer... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractIntegers(Collection<Integer> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(long value) {
        return subtract(new BigDecimal(value));
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Long value) {
        return value == null ? this : subtract(value.longValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (long value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractLongs(Collection<Long> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Float value) {
        return value == null ? this : subtract(value.doubleValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (float value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractFloats(Collection<Float> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(double value) {
        return subtract(Double.toString(value));
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Double value) {
        return value == null ? this : subtract(value.doubleValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (double value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractDoubles(Collection<Double> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(String value) {
        if (Strings.hasText(value)) {
            return subtract(new BigDecimal(Strings.trim(value)));
        }
        return this;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(String... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractStrings(Collection<String> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param value 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(BigDecimal value) {
        if (value != null) {
            return apply(BigDecimal::subtract, value);
        }
        return this;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(BigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtractBigDecimals(Collection<BigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param other 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(FluentBigDecimal other) {
        return (other == null) ? this : subtract(other.getValue());
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(FluentBigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (FluentBigDecimal value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    /**
     * 减法
     *
     * @param values 被减数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal subtract(Collection<FluentBigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (FluentBigDecimal value : values) {
            result = result.subtract(value);
        }
        return result;
    }

    //endregion


    //region Multiply

    /**
     * 乘法
     *
     * @param val 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Short val) {
        return val == null ? this : multiply(val.longValue());
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (short value : values) {
            result = result.multiply(value);
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyShorts(Collection<Short> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param val 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Integer val) {
        return val == null ? this : multiply(val.longValue());
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(int... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.multiply(value);
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Integer... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyIntegers(Collection<Integer> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘一个数
     *
     * @param value 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Float value) {
        return (value == null) ? this : multiply(value.floatValue());
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (float value : values) {
            result = result.multiply(value);
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyFloats(Collection<Float> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(long val) {
        return multiply(BigDecimal.valueOf(val));
    }

    /**
     * 乘一个数
     *
     * @param value 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Long value) {
        return (value == null) ? this : multiply(value.longValue());
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (long value : values) {
            result = result.multiply(value);
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyLongs(Collection<Long> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘一个数
     *
     * @param value 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(double value) {
        return multiply(BigDecimal.valueOf(value));
    }

    /**
     * 乘一个数
     *
     * @param value 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Double value) {
        return value == null ? this : multiply(value.doubleValue());
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (double value : values) {
            result = result.multiply(value);
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyDoubles(Collection<Double> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘一个数
     *
     * @param val 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(String val) {
        if (Strings.hasText(val)) {
            val = Strings.trim(val);
            return multiply(new BigDecimal(val));
        }
        return this;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(String... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            if (Strings.hasText(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyStrings(Collection<String> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            if (Strings.hasText(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param value 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(BigDecimal value) {
        return (value == null) ? this : apply(BigDecimal::multiply, value);
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(BigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param values 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiplyBigDecimals(Collection<BigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            if (Objects.isNotNull(value)) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 乘法
     *
     * @param other 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(FluentBigDecimal other) {
        return (other == null) ? this : this.multiply(other.getValue());
    }

    /**
     * 乘法
     *
     * @param others 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(FluentBigDecimal... others) {
        if (Arrays.isEmpty(others)) {
            return this;
        }
        return Arrays.stream(others).filter(Objects::nonNull).reduce(this, FluentBigDecimal::multiply);
    }

    /**
     * 乘法
     *
     * @param others 被乘数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal multiply(Collection<FluentBigDecimal> others) {
        if (Collections.isEmpty(others)) {
            return this;
        }
        return others.stream().filter(Objects::nonNull).reduce(this, FluentBigDecimal::multiply);
    }

    //endregion


    //region Division

    /**
     * 除法
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Short val) {
        return val == null ? this : divide(val.longValue());
    }

    /**
     * 除法
     *
     * @param values 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (short value : values) {
            result = result.divide(value);
        }
        return result;
    }

    /**
     * 除法
     *
     * @param values 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Short... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除法
     *
     * @param values 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideShorts(Collection<Short> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Short value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Integer val) {
        return (val == null) ? this : divide(val.longValue());
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(int... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (int value : values) {
            result = result.divide(value);
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Integer... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideIntegers(Collection<Integer> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Integer value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Float val) {
        return (val == null) ? this : divide(val.doubleValue());
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (float value : values) {
            result = result.divide(value);
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Float... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideFloats(Collection<Float> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Float value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(long val) {
        return divide(BigDecimal.valueOf(val));
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Long val) {
        return val == null ? this : divide(val.longValue());
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (long value : values) {
            result = result.divide(value);
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Long... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideLongs(Collection<Long> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Long value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(double val) {
        return divide(BigDecimal.valueOf(val));
    }

    /**
     * 除以一个数
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Double val) {
        return val == null ? this : divide(val.doubleValue());
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (double value : values) {
            result = result.divide(value);
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Double... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideDoubles(Collection<Double> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (Double value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 连续除以一个数
     *
     * @param val 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(String val) {
        if (Strings.hasText(val)) {
            val = Strings.trim(val);
            return divide(new BigDecimal(val));
        }
        return this;
    }

    /**
     * 连续除以一个数
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(String... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            if (Strings.hasText(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除法
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideStrings(Collection<String> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (String value : values) {
            if (Strings.hasText(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除法
     *
     * @param val 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(BigDecimal val) {
        return (val == null) ? this : apply(BigDecimal::divide, val);
    }

    /**
     * 除法
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(BigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
        // return Arrays.stream(values).filter(Objects::nonNull).map(FluentBigDecimal::of).reduce(this, FluentBigDecimal::divide);
    }

    /**
     * 除法
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divideBigDecimals(Collection<BigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        FluentBigDecimal result = this;
        for (BigDecimal value : values) {
            if (Objects.isNotNull(value)) {
                result = result.divide(value);
            }
        }
        return result;
    }

    /**
     * 除法
     *
     * @param other 被除数
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(FluentBigDecimal other) {
        if (other == null) {
            return this;
        }
        return this.divide(other.getValue());
    }


    /**
     * 除法
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(FluentBigDecimal... values) {
        if (Arrays.isEmpty(values)) {
            return this;
        }
        return Arrays.stream(values).filter(Objects::nonNull).reduce(this, FluentBigDecimal::divide);
    }

    /**
     * 除法
     *
     * @param values 被除数列表
     * @return {@linkplain FluentBigDecimal}
     */
    public FluentBigDecimal divide(Collection<FluentBigDecimal> values) {
        if (Collections.isEmpty(values)) {
            return this;
        }
        return values.stream().filter(Objects::nonNull).reduce(this, FluentBigDecimal::divide);
    }

    //endregion

    /**
     * 统一处理的方法
     *
     * @param projection 处理函数
     * @param argument   第二个参数
     * @return 新的结果
     */
    private FluentBigDecimal apply(BiProjection<BigDecimal> projection, BigDecimal argument) {
        if (argument == null) {
            return this;
        }

        FluentConfig fluentConfig = getFluentConfig();
        MathContext mathContext = fluentConfig.getMathContext();
        BigDecimal outcome = projection.project(this.value, argument, mathContext);
        Scaler scaler = fluentConfig.getScaler();
        BigDecimal scaleResult = scaler.apply(outcome, mathContext);
        return new FluentBigDecimal(scaleResult, fluentConfig);
    }

    /**
     * {@inheritDoc}
     *
     * @see BigDecimal#compareTo(BigDecimal)
     */
    @Override
    public int compareTo(@NotNull FluentBigDecimal o) {
        return getValue().compareTo(o.getValue());
    }

    /**
     * 与另外一个{@linkplain BigDecimal}比较
     *
     * @param other 另外一个{@linkplain BigDecimal}
     * @return 比较结果
     * @see BigDecimal#compareTo(BigDecimal)
     */
    public int compareTo(@NonNull BigDecimal other) {
        return getValue().compareTo(other);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return getValue().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long longValue() {
        return getValue().longValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float floatValue() {
        return getValue().floatValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return getValue().doubleValue();
    }

    /**
     * 比较是否相等
     *
     * @param other 另外一个{@linkplain BigDecimal}
     * @see #compareTo(BigDecimal)
     */
    public boolean compare(BigDecimal other) {
        return compareTo(other) == 0;
    }

    /**
     * Convenience: shortcut for: foo.compareTo(BigDecimal.ZERO) == 0.
     */
    public boolean isZero() {
        return compare(BigDecimal.ZERO);
    }

    /**
     * See {@link BigDecimal#longValueExact()}.
     */
    public long longValueExact() {
        return getValue().longValueExact();
    }

    /**
     * See {@link BigDecimal#intValueExact()}.
     */
    public int intValueExact() {
        return getValue().intValueExact();
    }

    /**
     * See {@link BigDecimal#shortValueExact()}.
     */
    public short shortValueExact() {
        return getValue().shortValueExact();
    }

    /**
     * See {@link BigDecimal#byteValueExact()}.
     */
    public byte byteValueExact() {
        return getValue().byteValueExact();
    }

    @Override
    public @NonNull String toString() {
        return String.format("%s[%s,%s]", getClass().getSimpleName(), value.toPlainString(), getFluentConfig());
    }

    /**
     * See {@link BigDecimal#toPlainString()}.
     */
    public @NonNull String toPlainString() {
        return getValue().toPlainString();
    }

    /**
     * See {@link BigDecimal#toEngineeringString()}.
     */
    public @NonNull String toEngineeringString() {
        return getValue().toEngineeringString();
    }

    /**
     * See {@link BigDecimal#toBigInteger()}.
     */
    public BigInteger toBigInteger() {
        return getValue().toBigInteger();
    }

    /**
     * See {@link BigDecimal#toBigIntegerExact()}.
     */
    public BigInteger toBigIntegerExact() {
        return getValue().toBigIntegerExact();
    }

}
