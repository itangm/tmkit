package cn.tmkit.core.lang;

import cn.tmkit.core.convert.Converts;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Map工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Maps {

    /**
     * Default initial capacity for {@linkplain HashMap}
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * Default load factor for {@link HashMap}/{@link LinkedHashMap} variants.
     *
     * @see #hashMap(int)
     * @see #linkedHashMap(int)
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // region 创建

    /**
     * 创建{@linkplain HashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> hashMap(int expectedSize) {
        return new HashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap(int expectedSize) {
        return new LinkedHashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (isNotEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    // endregion


    // region 是否为空判断

    /**
     * 判断map是否为空
     * <pre class="code">CollectionUtils.isEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断map是否为不为空
     * <pre class="code">CollectionUtils.isNotEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map不为{@code null}且不为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 如果{@code map}为{@code null}则返回一个空{@code Map}，否则返回本身
     *
     * @param map Map对象
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 非空的Map对象
     */
    @NotNull
    public static <K, V> Map<K, V> wrapper(Map<K, V> map) {
        return (map == null) ? Collections.emptyMap() : map;
    }

    // endregion


    // region join methods

    /**
     * <p>
     * 将Map对象转成字符串，其中Key与Value的连接使用{@code keySeparator}拼接,键值对之间采用采用{@code entrySeparator}拼接
     * </p>
     * <pre>
     *     Map&lt;String,String&gt; params = new HashMap&lt;&gt;();
     *     params.put("username","admin");
     *     params.put("password","123456");
     *     String str = CollectionUtils.toString(params,"=","|");
     *     //则得到的字符串结果为 username=admin|password=123456
     * </pre>
     *
     * @param data           数据
     * @param keySeparator   Key和Value的拼接字符串，默认值为空
     * @param entrySeparator 键值对的拼接字符串，默认值为空
     * @return 返回字符串
     */
    public static String join(final Map<String, ?> data, String keySeparator, String entrySeparator) {
        return join(data, keySeparator, entrySeparator, false, true);
    }

    /**
     * <p>
     * 将Map对象转成字符串，其中Key与Value的连接使用{@code keySeparator}拼接,键值对之间采用采用{@code entrySeparator}拼接.
     * </p>
     * <pre>
     *     Map&lt;String,String&gt; params = new HashMap&lt;&gt;();
     *     params.put("username","admin");
     *     params.put("password","123456");
     *     String str = CollectionUtils.toString(params,"=","|",true);
     *     //则得到的字符串结果为 password=123456|username=admin
     * </pre>
     *
     * @param data           Map数据
     * @param keySeparator   Key和Value的拼接字符串，默认值为空
     * @param entrySeparator 键值对的拼接字符串，默认值为空
     * @param keySortable    如果值为{@code true}则按自然排序排序,否则不处理
     * @param ignoreNull     如果键或值为{@code true}则忽略空值
     * @return 返回字符串
     */
    private static String join(final Map<String, ?> data, String keySeparator, String entrySeparator,
                               boolean keySortable, boolean ignoreNull) {
        if (data == null) {
            return null;
        }
        if (data.isEmpty()) {
            return Strings.EMPTY_STRING;
        }
        if (keySeparator == null) {
            keySeparator = Strings.EMPTY_STRING;
        }
        if (entrySeparator == null) {
            entrySeparator = Strings.EMPTY_STRING;
        }
        Set<String> keys = data.keySet();
        if (keySortable) {
            keys = new TreeSet<>(keys);
        }
        final String finalKeySeparator = keySeparator;
        return keys.stream().map(key -> {
            Object value = data.get(key);
            if (ignoreNull && (key == null || value == null)) {
                return null;
            }
            return key + finalKeySeparator + value;
        }).collect(Collectors.joining(entrySeparator));
    }

    /**
     * 将Map对象的数据转为通用签名字符串(目前通用签名规则为：将key和value用'='连接，每个键值对之间用'&nbsp;'连接，并且键列表按照自然排序)
     *
     * @param params Map对象数据
     * @return 返回字符串
     */
    public static String toSignStr(final Map<String, ?> params) {
        return join(params, Strings.EQUALS, Strings.AMP, true, true);
    }

    // endregion


    // region 排序

    /**
     * 对Map排序，Key有序的Map，使用默认Key排序方式（字母顺序）
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @return TreeMap
     * @see #sort(Map, Comparator)
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map) {
        return sort(map, null);
    }

    /**
     * 对Map排序，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map，为null返回null
     * @param comparator Key比较器
     * @return TreeMap，map为null返回null
     * @see #treeMap(Map, Comparator)
     */
    public static <K, V> TreeMap<K, V> sort(Map<K, V> map, Comparator<? super K> comparator) {
        if (map == null) {
            return null;
        }
        if (map instanceof TreeMap) {
            TreeMap<K, V> result = (TreeMap<K, V>) map;
            if (comparator == null || comparator.equals(result.comparator())) {
                return result;
            }
        }
        return treeMap(map, comparator);
    }

    // endregion


    // region general

    /**
     * 将{@code kvs}添加到{@code map}
     *
     * @param map 原Map对象
     * @param kvs 需要加入的Map对象
     * @param <K> 键类型
     * @param <V> 值类型
     */
    public static <K, V> void putAll(Map<K, V> map, Map<K, V> kvs) {
        if (isNotEmpty(map) && isNotEmpty(kvs)) {
            map.putAll(kvs);
        }
    }

    /**
     * 键值对反转
     *
     * @param map 原Map
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 反转后的Map
     */
    public static <K, V> Map<V, K> reverse(Map<K, V> map) {
        Map<V, K> resultMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resultMap.put(v, k));
        return resultMap;
    }

    // endregion


    // region 获取值

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key) {
        return getStr(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key, String defaultValue) {
        return get(map, key, String.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key) {
        return getInt(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key, Integer defaultValue) {
        return get(map, key, Integer.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        return getDouble(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key, Double defaultValue) {
        return get(map, key, Double.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key) {
        return getFloat(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key, Float defaultValue) {
        return get(map, key, Float.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key) {
        return getShort(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key, Short defaultValue) {
        return get(map, key, Short.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key) {
        return getBool(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key, Boolean defaultValue) {
        return get(map, key, Boolean.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key) {
        return getChar(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key, Character defaultValue) {
        return get(map, key, Character.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key) {
        return getByte(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key, Byte defaultValue) {
        return get(map, key, Byte.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return getLong(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key, Long defaultValue) {
        return get(map, key, Long.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map  map
     * @param key  键
     * @param type 值类型
     * @param <T>  目标值类型
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return get(map, key, type, null);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map          map
     * @param key          键
     * @param type         值类型
     * @param <T>          目标值类型
     * @param defaultValue 默认值
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type, T defaultValue) {
        return (null == map) ? null : Converts.convert(type, map.get(key), defaultValue);
    }

    // endregion

    // region
    // endregion

}
