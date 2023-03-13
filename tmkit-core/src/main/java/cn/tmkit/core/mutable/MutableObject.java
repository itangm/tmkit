package cn.tmkit.core.mutable;

import java.io.Serializable;

/**
 * 可变{@code Object}
 *
 * @param <T> 可变的类型
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public class MutableObject<T> implements Mutable<T>, Serializable {

    private static final long serialVersionUID = 2023L;

    /**
     * 构建{@code MutableObject}
     *
     * @param value 被包装的值
     * @param <T>   值类型
     * @return {@linkplain MutableObject}
     */
    public static <T> MutableObject<T> of(T value) {
        return new MutableObject<>(value);
    }

    private T value;

    /**
     * 构造，空值
     */
    public MutableObject() {

    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableObject(final T value) {
        this.value = value;
    }

    // -----------------------------------------------------------------------
    @Override
    public T get() {
        return this.value;
    }

    @Override
    public void set(final T value) {
        this.value = value;
    }

    // -----------------------------------------------------------------------
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() == obj.getClass()) {
            final MutableObject<?> that = (MutableObject<?>) obj;
            return this.value.equals(that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

    // -----------------------------------------------------------------------
    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }

}
