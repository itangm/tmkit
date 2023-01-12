package cn.tmkit.core.support;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 简单的缓存，基于{@linkplain SoftReference}实现了自动垃圾回收、LRU cache
 * <p>该缓存是线程非安全的。</p>
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 * @see <a href="https://android.googlesource.com/platform/packages/apps/Mms/+/master/src/com/android/mms/util/SimpleCache.java">SimpleCache.java</a>
 */
public class SimpleCache<K, V> implements Serializable {

    /**
     * 真正缓存的池
     */
    private final SoftReferenceMap softReferenceMap;

    /**
     * 构造器
     *
     * @param maxCapacity 最大容量
     */
    public SimpleCache(int maxCapacity) {
        this(FixedLinkedHashMap.DEFAULT_INITIAL_CAPACITY, maxCapacity);
    }

    /**
     * 构造器
     *
     * @param initialCapacity 初始容量
     * @param maxCapacity     最大容量
     */
    public SimpleCache(int initialCapacity, int maxCapacity) {
        this(initialCapacity, maxCapacity, FixedLinkedHashMap.DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造器
     *
     * @param initialCapacity 初始容量
     * @param maxCapacity     最大容量
     * @param loadFactor      加载因子
     */
    public SimpleCache(int initialCapacity, int maxCapacity, float loadFactor) {
        this.softReferenceMap = new SoftReferenceMap(initialCapacity, maxCapacity, loadFactor);
    }

    /**
     * 从缓存池中获取值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        return unwrap(softReferenceMap.get(key));
    }

    /**
     * 将值放入到缓存中
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value) {
        softReferenceMap.put(key, new SoftReference<>(value));
        return value;
    }

    /**
     * 如果不存在设置键值对
     *
     * @param key   键
     * @param value 值
     * @return 值，如果存在则返回{@code null}
     */
    public V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            return put(key, value);
        }
        return null;
    }

    /**
     * 如果key对应的值不存在，则调用{@code supplier}的计算结果设置到缓存中
     *
     * @param key      键
     * @param supplier 值的计算函数，为空不处理
     * @return 值，可能为{@code null}
     */
    public V computeIfAbsent(K key, Supplier<V> supplier) {
        V v = get(key);
        if (v == null && supplier != null) {
            v = put(key, supplier.get());
        }
        return v;
    }

    /**
     * 如果key对应的值不存在，则调用{@code mappingFun}的计算结果设置到缓存中
     *
     * @param key        键
     * @param mappingFun 值的计算函数，为空不处理
     * @return 值，可能为{@code null}
     */
    public V computeIfAbsent(K key, Function<K, V> mappingFun) {
        V v = get(key);
        if (v == null && mappingFun != null) {
            v = put(key, mappingFun.apply(key));
        }
        return v;
    }

    /**
     * 返沪缓存大小
     *
     * @return 缓存小小
     */
    public int size() {
        return softReferenceMap.size();
    }

    /**
     * 清空缓存池
     */
    public void clear() {
        softReferenceMap.clear();
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 值
     */
    public V remove(K key) {
        return unwrap(softReferenceMap.remove(key));
    }

    /**
     * 解包
     *
     * @param ref 值的引用
     * @return 实际的值
     */
    private V unwrap(SoftReference<V> ref) {
        return ref != null ? ref.get() : null;
    }

    /**
     * 简单的LRU缓存实现
     */
    private class SoftReferenceMap extends FixedLinkedHashMap<K, SoftReference<V>> {

        public SoftReferenceMap(int initialCapacity, int maxCapacity, float loadFactor) {
            super(initialCapacity, maxCapacity, loadFactor);
        }

    }

}
