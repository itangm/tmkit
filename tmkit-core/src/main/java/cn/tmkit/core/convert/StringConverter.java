package cn.tmkit.core.convert;

import org.jetbrains.annotations.NotNull;

/**
 * {@linkplain String}转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class StringConverter extends AbstractConverter<String> {

    /**
     * 内部转换器，被 {@link AbstractConverter#handleInternal(Object)} 调用，实现基本转换逻辑<br>
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
    protected String handleInternal(@NotNull Object value) {
        return execToStr(value);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<String> getTargetClass() {
        return String.class;
    }

    @Override
    public String toString() {
        return "StringConverter";
    }

}
