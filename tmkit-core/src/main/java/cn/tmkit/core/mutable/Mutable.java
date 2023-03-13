package cn.tmkit.core.mutable;

/**
 * 提供值可变的接口，比如常见的使用场景再Lambda、匿名内部类等等
 *
 * @param <T> 值的类型
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public interface Mutable<T> {

    /**
     * 获得原始值
     * @return 原始值
     */
    T get();

    /**
     * 设置值
     * @param value 值
     */
    void set(T value);

}
