package cn.tmkit.core.mutable;

import java.io.Serializable;

/**
 * 可变{@code long}类型
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public class MutableLong extends Number implements Mutable<Long>, Comparable<MutableLong>, Serializable {

    private static final long serialVersionUID = 2023L;

    /**
     * byte value
     */
    private long value;

    /**
     * 构造，默认值0
     */
    public MutableLong() {
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableLong(final long value) {
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableLong(final Number value) {
        this(value.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long get() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(final Long value) {
        this.value = value;
    }

    /**
     * 重写equals
     *
     * @return Equals to another object
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableLong) {
            return value == ((MutableLong) obj).value;
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
        return Long.hashCode(value);
    }

    /**
     * 比较
     *
     * @param other 其它 MutableBool 对象
     * @return x==y返回0，x&lt;y返回-1，x&gt;y返回1
     */
    @Override
    public int compareTo(final MutableLong other) {
        return Long.compare(this.value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float floatValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void set(final long value) {
        this.value = value;
    }

}
