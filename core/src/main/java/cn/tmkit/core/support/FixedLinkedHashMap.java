package cn.tmkit.core.support;

import java.util.LinkedHashMap;

/**
 * 固定大小的{@linkplain LinkedHashMap}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    /**
     * The default initial capacity - MUST be a power of two.
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * The load factor used when none specified in constructor.
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private final int maxCapacity;

    /**
     * 构造器，初始容量为{@linkplain #DEFAULT_INITIAL_CAPACITY}，默认的加载因子{@linkplain #DEFAULT_LOAD_FACTOR}
     *
     * @param maxCapacity 最大容量
     */
    public FixedLinkedHashMap(int maxCapacity) {
        this(DEFAULT_INITIAL_CAPACITY, maxCapacity);
    }

    /**
     * 构造器，默认的加载因子{@linkplain #DEFAULT_LOAD_FACTOR}
     *
     * @param initialCapacity 初始容量
     * @param maxCapacity     最大容量
     */
    public FixedLinkedHashMap(int initialCapacity, int maxCapacity) {
        this(initialCapacity, maxCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造器
     *
     * @param initialCapacity 初始容量
     * @param maxCapacity     最大容量
     * @param loadFactor      加载因子
     */
    public FixedLinkedHashMap(int initialCapacity, int maxCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
        this.maxCapacity = Math.max(initialCapacity, maxCapacity);
    }

    /**
     * 最大容量
     *
     * @return 最大容量
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        // 当链表元素大于最大容量时，移除最老（最久未被使用）的元素
        return size() > this.maxCapacity;
    }

}
