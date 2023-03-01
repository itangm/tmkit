package cn.tmkit.core.convert;

import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * {@linkplain Long} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class LongConverter extends AbstractConverter<Long> {

    public static LongConverter getInstance() {
        return Singletons.get(LongConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long handleInternal(@NotNull Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1L : 0L;
        } else if (input instanceof Number) {
            return Numbers.convert((Number) input, Long.class);
        } else if (input instanceof String) {
            return Numbers.convert(Numbers.createNumber(input.toString()), Long.class);
        } else if (input instanceof Date) {
            return ((Date) input).getTime();
        }
        throw new ConverterRuntimeException("Can't cast " + input + "to java.lang.Long");
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Long> getTargetClass() {
        return Long.class;
    }

    @Override
    public String toString() {
        return "LongConverter";
    }

}
