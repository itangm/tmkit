package cn.tmkit.core.convert;

import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

/**
 * {@linkplain Integer} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class IntegerConverter extends AbstractConverter<Integer> {

    /**
     * 返回{@linkplain  IntegerConverter}实例对象
     *
     * @return {@linkplain  IntegerConverter}
     */
    public static IntegerConverter getInstance() {
        return Singletons.get(IntegerConverter.class);
    }

    @Override
    protected Integer handleInternal(@NotNull Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1 : 0;
        } else if (input instanceof Number) {
            return Numbers.convert((Number) input, Integer.class);
        } else if (input instanceof String) {
            Number number = Numbers.createNumber(input.toString());
            if (number == null) {
                return null;
            }
            return Numbers.convert(number, Integer.class);
        }
        throw new ConverterRuntimeException("Can't cast " + input + " to java.lang.Integer");
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Integer> getTargetClass() {
        return Integer.class;
    }

    @Override
    public String toString() {
        return "IntegerConverter";
    }

}
