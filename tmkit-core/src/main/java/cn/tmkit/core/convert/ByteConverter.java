package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

/**
 * 字节转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class ByteConverter extends AbstractConverter<Byte> {

    private static final long serialVersionUID = 2022L;

    /**
     * 返回{@linkplain  ByteConverter}实例对象
     *
     * @return {@linkplain  ByteConverter}
     */
    public static ByteConverter getInstance() {
        return Singletons.get(ByteConverter.class);
    }

    @Override
    protected Byte handleInternal(@NotNull Object input) {
        Integer output = IntegerConverter.getInstance().handleInternal(input);
        if (output == null) {
            return null;
        }
        return Numbers.convert(output, Byte.class);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Byte> getTargetClass() {
        return Byte.class;
    }

    @Override
    public String toString() {
        return "ByteConverter";
    }

}
