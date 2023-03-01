package cn.tmkit.core.convert;

import cn.tmkit.core.exception.ConverterRuntimeException;

import java.util.function.BiFunction;

/**
 * 转换器接口，定义了数据转为指定类型的数据
 *
 * @param <R> 输出参数类型
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
@FunctionalInterface
public interface Converter<R> extends BiFunction<Object, R, R> {

    /**
     * 将数据转为指定类型的数据
     *
     * @param value        输入的数据值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConverterRuntimeException 转换异常
     */
    R handle(Object value, R defaultValue) throws ConverterRuntimeException;

    /**
     * {@inheritDoc}
     */
    @Override
    default R apply(Object o, R r) {
        return handle(o, r);
    }

    /**
     * 转换值为指定类型，可选是否不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @see #handle(Object, Object)
     */
    default R handleQuietly(Object value, R defaultValue) {
        try {
            return handle(value, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
