package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

/**
 * {@linkplain Float} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class FloatConverter extends AbstractConverter<Float> {

    private static final long serialVersionUID = 2023L;

    public static FloatConverter getInstance() {
        return Singletons.get(FloatConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Float handleInternal(@NotNull Object input) {
        Double output = DoubleConverter.getInstance().handleInternal(input);
        return Numbers.convert(output, Float.class);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Float> getTargetClass() {
        return Float.class;
    }

    @Override
    public String toString() {
        return "FloatConverter";
    }

}
