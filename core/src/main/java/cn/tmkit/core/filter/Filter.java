package cn.tmkit.core.filter;

import java.util.function.Predicate;

/**
 * Filter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
@FunctionalInterface
public interface Filter<T> extends Predicate<T> {

    /**
     * 是否接受该对象
     *
     * @param ele 当前对象
     * @return 接受则返回{@code true},否则返回{@code false}
     */
    boolean accept(T ele);

    /**
     * {@inheritDoc}
     */
    default boolean test(T ele) {
        return accept(ele);
    }

}
