package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

/**
 * {@linkplain Short}转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class ShortConverter extends AbstractConverter<Short> {

    public static ShortConverter getInstance() {
        return Singletons.get(ShortConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Short handleInternal(@NotNull Object input) {
        Integer output = IntegerConverter.getInstance().handleInternal(input);
        return Numbers.convert(output, Short.class);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Short> getTargetClass() {
        return Short.class;
    }

    @Override
    public String toString() {
        return "ShortConverter";
    }

}
