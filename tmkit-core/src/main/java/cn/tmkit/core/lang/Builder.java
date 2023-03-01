package cn.tmkit.core.lang;

import java.util.function.Supplier;

/**
 * Builder Interface
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
@FunctionalInterface
public interface Builder<T> extends Supplier<T> {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();

    /**
     * {@inheritDoc}
     */
    default T get() {
        return build();
    }

}
