package cn.tmkit.core.mutable;

import java.io.Serializable;

/**
 * 可变{@code byte}类型
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-13
 */
public class MutableByte extends Number implements Mutable<Byte>, Comparable<MutableByte>, Serializable {

    private static final long serialVersionUID = 2023L;

    /**
     * byte value
     */
    private byte value;

    /**
     * 构造，默认值0
     */
    public MutableByte() {
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableByte(final byte value) {
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableByte(final Number value) {
        this(value.byteValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte get() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(final Byte value) {
        this.value = value;
    }

    /**
     * 重写equals
     *
     * @return Equals to another object
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableByte) {
            return value == ((MutableByte) obj).value;
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
        return value;
    }

    /**
     * 比较
     *
     * @param other 其它 MutableBool 对象
     * @return x==y返回0，x&lt;y返回-1，x&gt;y返回1
     */
    @Override
    public int compareTo(final MutableByte other) {
        return Byte.compare(this.value, other.value);
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
    public byte byteValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return value;
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
    public void set(final byte value) {
        this.value = value;
    }

}
