package cn.tmkit.core.convert;

import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Chars;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * 抽象转换器，提供通用的转换逻辑
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public abstract class AbstractConverter<R> implements Converter<R>, Serializable {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2023L;

    /**
     * {@inheritDoc}
     */
    @Override
    public R handle(Object value, R defaultValue) throws ConverterRuntimeException {
        Class<R> targetClass = getTargetClass();
        if (targetClass == null && defaultValue == null) {
            throw new ConverterRuntimeException("[type] and [defaultValue] are both null for Converter");
        }
        if (targetClass == null) {
            // 目标类型不确定时使用默认值的类型
            targetClass = (Class<R>) defaultValue.getClass();
        }
        if (value == null || targetClass == null) {
            return defaultValue;
        }
        if (null == defaultValue || targetClass.isInstance(defaultValue)) {
            if (targetClass.isInstance(value) && !Map.class.isAssignableFrom(targetClass)) {
                return targetClass.cast(value);
            }
            R result = handleInternal(value);
            return (result == null) ? defaultValue : result;
        } else {
            throw new ConverterRuntimeException("Default value [" + defaultValue + "](" + defaultValue.getClass() + ") is not the instance of [" + targetClass + "]");
        }
    }

    /**
     * 内部转换器，被 {@link AbstractConverter#handle(Object, Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link ConverterRuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    protected abstract R handleInternal(@NotNull Object value);

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    public abstract Class<R> getTargetClass();

    /**
     * 值转为String，用于内部转换中需要使用String中转的情况<br>
     * 转换规则为：
     *
     * <pre>
     * 1、字符串类型将被强转
     * 2、数组将被转换为逗号分隔的字符串
     * 3、其它类型将调用默认的toString()方法
     * </pre>
     *
     * @param value 值
     * @return String
     */
    protected String execToStr(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof CharSequence) {
            return value.toString();
        } else if (Arrays.isArray(value)) {
            return Arrays.toString(value);
        } else if (Chars.isChar(value)) {
            //对于ASCII字符使用缓存加速转换，减少空间创建
            return Chars.toString((char) value);
        }
        return value.toString();
    }

}
