package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import cn.tmkit.core.math.FluentBigDecimal;
import org.jetbrains.annotations.NotNull;

/**
 * {@linkplain FluentBigDecimal} Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class FluentBigDecimalConverter extends AbstractConverter<FluentBigDecimal> {

    /**
     * 内部转换器，被 {@link AbstractConverter#handle(Object, Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link RuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    @Override
    protected FluentBigDecimal handleInternal(@NotNull Object value) {
        if (value instanceof Number) {
            return FluentBigDecimal.of(Numbers.createBigDecimal((Number) value));
        } else if (value instanceof Boolean) {
            return FluentBigDecimal.of(((Boolean) value) ? 1 : 0);
        }
        return FluentBigDecimal.of(Numbers.createBigDecimal(execToStr(value)));
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<FluentBigDecimal> getTargetClass() {
        return FluentBigDecimal.class;
    }

    /**
     * 返回{@linkplain  FluentBigDecimalConverter}实例对象
     *
     * @return {@linkplain  FluentBigDecimalConverter}
     */
    public static FluentBigDecimalConverter getInstance() {
        return Singletons.get(FluentBigDecimalConverter.class);
    }

}
