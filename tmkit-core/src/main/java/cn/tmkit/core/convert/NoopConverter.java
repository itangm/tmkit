package cn.tmkit.core.convert;

import org.jetbrains.annotations.NotNull;

/**
 * 无实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class NoopConverter extends AbstractConverter<Object> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInternal(@NotNull Object input) {
        return input;
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<Object> getTargetClass() {
        return Object.class;
    }

}
