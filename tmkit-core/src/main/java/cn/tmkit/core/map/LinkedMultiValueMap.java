package cn.tmkit.core.map;

import cn.tmkit.core.lang.CollectionUtil;
import cn.tmkit.core.support.CloneSupport;

import java.io.Serializable;
import java.util.*;

/**
 * 基于{@linkplain LinkedHashMap}的实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class LinkedMultiValueMap<K, V> extends CloneSupport<LinkedMultiValueMap<K, V>> implements MultiValueMap<K, V>, Serializable {

    private final Map<K, List<V>> storeMap;

    public LinkedMultiValueMap() {
        this.storeMap = new LinkedHashMap<>();
    }

    public LinkedMultiValueMap(int initialCapacity) {
        this.storeMap = new LinkedHashMap<>(initialCapacity);
    }

    public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
        this.storeMap = new LinkedHashMap<>(otherMap);
    }

    // region MultiValueMap

    /**
     * 根据{@code key}返回第一个值
     *
     * @param key 键
     * @return 第一个值，可能为{@code null}
     */
    @Override
    public V getFirst(K key) {
        List<V> values = this.storeMap.get(key);
        return CollectionUtil.isNotEmpty(values) ? values.get(0) : null;
    }

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void add(K key, V value) {
        List<V> values = this.storeMap.computeIfAbsent(key, k -> new LinkedList<>());
        values.add(value);
    }

    /**
     * 添加键值对
     *
     * @param key    键
     * @param values 所有值列表
     */
    @Override
    public void addAll(K key, List<V> values) {
        List<V> currentValues = this.storeMap.computeIfAbsent(key, k -> new LinkedList<>());
        currentValues.addAll(values);
    }

    /**
     * 设置键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(K key, V value) {
        List<V> values = new LinkedList<>();
        values.add(value);
        this.storeMap.put(key, values);
    }

    /**
     * 转为单个值的键值对，取值的第一个
     *
     * @return 单个值Map
     */
    @Override
    public Map<K, V> toSingleValueMap() {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.storeMap.size());
        this.storeMap.forEach((key, values) -> {
            if (CollectionUtil.isNotEmpty(values)) {
                singleValueMap.put(key, values.get(0));
            }
        });
        return singleValueMap;
    }

    // endregion

    // region Map

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.storeMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return this.storeMap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return this.storeMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return this.storeMap.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> get(Object key) {
        return this.storeMap.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> put(K key, List<V> value) {
        return this.storeMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> remove(Object key) {
        return this.storeMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> map) {
        this.storeMap.putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.storeMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return this.storeMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<List<V>> values() {
        return this.storeMap.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return this.storeMap.entrySet();
    }

    // endregion

}
