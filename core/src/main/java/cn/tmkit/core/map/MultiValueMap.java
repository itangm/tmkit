package cn.tmkit.core.map;

import java.util.List;
import java.util.Map;

/**
 * 值可以存储多个的Map接口
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public interface MultiValueMap<K, V> extends Map<K, List<V>> {

    /**
     * 根据{@code key}返回第一个值
     *
     * @param key 键
     * @return 第一个值，可能为{@code null}
     */
    V getFirst(K key);

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    void add(K key, V value);

    /**
     * 添加键值对
     *
     * @param key    键
     * @param values 所有值列表
     */
    void addAll(K key, List<V> values);

    /**
     * 添加值
     *
     * @param values 待添加的值
     */
    default void addAll(MultiValueMap<K, V> values) {
        values.forEach(this::addAll);
    }

    /**
     * 设置键值对
     *
     * @param key   键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 设置键值对
     *
     * @param map 键值对
     */
    default void setAll(Map<K, V> map) {
        map.forEach(this::set);
    }

    /**
     * 转为单个值的键值对，取值的第一个
     *
     * @return 单个值Map
     */
    Map<K, V> toSingleValueMap();

}
