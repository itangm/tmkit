package cn.tmkit.core.mutable;

import cn.tmkit.core.lang.Objects;

import java.io.Serializable;

/**
 * 可变{@linkplain Boolean}类型
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public class MutableBoolean implements Comparable<MutableBoolean>, Mutable<Boolean>, Serializable {

    private static final long serialVersionUID = 2023L;

    private Boolean value;

    /**
     * 构造，默认值{@code false}
     */
    public MutableBoolean() {
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableBoolean(final boolean value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean get() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(final Boolean value) {
        this.value = value;
    }

    /**
     * 重写equals
     *
     * @return Equals to another object
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableBoolean) {
            return Objects.equals(value, ((MutableBoolean) obj).value);
        }
        return false;
    }

    /**
     * 重写hashCode
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }

    /**
     * 比较
     *
     * @param other 其它 MutableBool 对象
     * @return x==y返回0，x&lt;y返回-1，x&gt;y返回1
     */
    @Override
    public int compareTo(final MutableBoolean other) {
        return Boolean.compare(this.value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void set(final boolean value) {
        this.value = value;
    }

    /**
     * 设置值为{@code false}
     */
    public void setFalse() {
        this.value = false;
    }

    /**
     * 设置值为{@code true}
     */
    public void setTrue() {
        this.value = true;
    }


}
