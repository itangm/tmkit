package cn.tmkit.core.lang;

import cn.tmkit.core.convert.Converts;
import cn.tmkit.core.map.LinkedMultiValueMap;
import cn.tmkit.core.map.MultiValueMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
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

    private static int ensureSize(int expectedSize) {
        return (int) (expectedSize / DEFAULT_LOAD_FACTOR);
    }

    // region 创建空Map

    /**
     * 创建一个空的不可变的Map（Map的各种方法基本不能用）
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 空的Map对象
     */
    public static <K, V> Map<K, V> emptyMap() {
        return java.util.Collections.emptyMap();
    }

    // endregion

    // region 创建HashMap

    /**
     * 创建{@linkplain HashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> hashMap() {
        return hashMap(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 创建{@linkplain HashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return hashMap();
    }

    /**
     * 创建{@linkplain HashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> hashMap(int expectedSize) {
        return new HashMap<>(ensureSize(expectedSize), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 创建{@linkplain HashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return hashMap(expectedSize);
    }

    /**
     * 创建{@linkplain HashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @param m   map数据
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> hashMap(Map<? extends K, ? extends V> m) {
        if (isEmpty(m)) {
            return hashMap();
        }
        HashMap<K, V> r = hashMap(m.size() << 1);
        r.putAll(m);
        return r;
    }

    /**
     * 创建{@linkplain HashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @param m   map数据
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> m) {
        return hashMap(m);
    }

    // endregion

    // region 创建LinkedHashMap

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap() {
        return linkedHashMap(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap(int expectedSize) {
        return new LinkedHashMap<>(ensureSize(expectedSize), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap(Map<? extends K, ? extends V> m) {
        if (isEmpty(m)) {
            return linkedHashMap();
        }
        LinkedHashMap<K, V> r = linkedHashMap(m.size() << 1);
        r.putAll(m);
        return r;
    }

    // endregion

    // region 创建TreeMap

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        键的类型
     * @param <V>        值的类型
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
     * @param <K>        键的类型
     * @param <V>        值的类型
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    // endregion

    // region 创建IdentityHashMap

    /**
     * 新建{@linkplain IdentityHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return {@link IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> identityHashMap(int expectedSize) {
        return new IdentityHashMap<>(ensureSize(expectedSize));
    }

    /**
     * 新建{@linkplain IdentityHashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return {@link IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> identityHashMap() {
        return identityHashMap(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 新建{@linkplain IdentityHashMap}
     *
     * @param m   原Map数据
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return {@link IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> identityHashMap(Map<? extends K, ? extends V> m) {
        if (isEmpty(m)) {
            return identityHashMap();
        }
        IdentityHashMap<K, V> r = identityHashMap(m.size() << 1);
        r.putAll(m);
        return r;
    }

    // endregion

    // region 创建MultiValueMap

    /**
     * 创建{@linkplain MultiValueMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 值可以为多个的Map对象
     */
    public static <K, V> MultiValueMap<K, V> multiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    /**
     * 创建{@linkplain MultiValueMap}
     *
     * @param expectedSize 期望的容量
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return 值可以为多个的Map对象
     */
    public static <K, V> MultiValueMap<K, V> multiValueMap(int expectedSize) {
        return new LinkedMultiValueMap<>(ensureSize(expectedSize));
    }

    // endregion

    // region 创建HashMap，并初始值

    /**
     * 创建一个单一的Map
     *
     * @param key   键的值
     * @param value 值的值
     * @param <K>   键的类型
     * @param <V>   值的类型
     * @return 单个键值对的Map
     * @see java.util.Collections#singletonMap(Object, Object)
     */
    public static <K, V> Map<K, V> singletonMap(K key, V value) {
        return java.util.Collections.singletonMap(key, value);
    }

    /**
     * 创建一个Map
     *
     * @param key   键的值
     * @param value 值的值
     * @param <K>   键的类型
     * @param <V>   值的类型
     * @return Map对象
     */
    public static <K, V> Map<K, V> of(K key, V value) {
        Map<K, V> map = MapUtil.newHashMap();
        map.put(key, value);
        return map;
    }

    /**
     * 创建一个Map
     *
     * @param key1   键的值
     * @param value1 值的值
     * @param key2   键的值
     * @param value2 值的值
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return Map对象
     */
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2) {
        Map<K, V> map = MapUtil.newHashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * 创建一个Map
     *
     * @param key1   键的值
     * @param value1 值的值
     * @param key2   键的值
     * @param value2 值的值
     * @param key3   键的值
     * @param value3 值的值
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return Map对象
     */
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3) {
        Map<K, V> map = MapUtil.newHashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }

    /**
     * 创建一个Map
     *
     * @param key1   键的值
     * @param value1 值的值
     * @param key2   键的值
     * @param value2 值的值
     * @param key3   键的值
     * @param value3 值的值
     * @param key4   键的值
     * @param value4 值的值
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return Map对象
     */
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
        Map<K, V> map = MapUtil.newHashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        return map;
    }

    /**
     * 创建一个Map
     *
     * @param key1   键的值
     * @param value1 值的值
     * @param key2   键的值
     * @param value2 值的值
     * @param key3   键的值
     * @param value3 值的值
     * @param key4   键的值
     * @param value4 值的值
     * @param key5   键的值
     * @param value5 值的值
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return Map对象
     */
    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4,
                                      K key5, V value5) {
        Map<K, V> map = MapUtil.newHashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        map.put(key5, value5);
        return map;
    }

    // endregion

    // region 创建HashMap，并初始值

    /**
     * 创建{@linkplain ConcurrentHashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return ConcurrentHashMap实例
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap() {
        return newConcurrentHashMap();
    }

    /**
     * 创建{@linkplain ConcurrentHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return ConcurrentHashMap实例
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap(int expectedSize) {
        return newConcurrentHashMap(expectedSize);
    }

    /**
     * 创建{@linkplain ConcurrentHashMap}
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return ConcurrentHashMap实例
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 创建{@linkplain ConcurrentHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键的类型
     * @param <V>          值的类型
     * @return ConcurrentHashMap实例
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int expectedSize) {
        return new ConcurrentHashMap<>(ensureSize(expectedSize));
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
        return (map == null) ? emptyMap() : map;
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
    public static String join(final Map<String, ?> data, String keySeparator, String entrySeparator,
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

    /**
     * 根据给定的键和映射函数，如果映射中不包含该键，将其添加到映射中，并返回该键对应的值。
     * 如果映射中已经包含了该键，则返回该键原来的值。
     *
     * @param map 要操作的映射
     * @param key 要查找或添加的键
     * @param mf  用于添加键到映射中的函数
     * @param <K> 映射中键的类型
     * @param <V> 映射中值的类型
     * @return 键对应的值
     */
    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> mf) {
        if (Objects.isAnyNull(map, key, mf)) {
            return null;
        }
        V value = map.get(key);
        if (value != null) {
            return value;
        }
        return map.computeIfAbsent(key, mf);
    }

    /**
     * 根据给定的键和映射函数，如果映射中不包含该键，将其添加到映射中，并返回该键对应的值。
     * 如果映射中已经包含了该键，则返回该键原来的值。
     *
     * @param map      要操作的映射
     * @param key      要查找或添加的键
     * @param supplier 用于添加键到映射中的函数
     * @param <K>      映射中键的类型
     * @param <V>      映射中值的类型
     * @return 键对应的值
     */
    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Supplier<V> supplier) {
        return computeIfAbsent(map, key, k -> supplier.get());
    }

    /**
     * 遍历集合元素，并执行动作{@code action}
     *
     * @param map    集合列表
     * @param action 执行动作
     * @param <K>    键的类型
     * @param <V>    值的类型
     */
    public static <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
        if (isEmpty(map) || Objects.isNull(action)) {
            return;
        }
        map.forEach(action);
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


    // region 转换

    /**
     * 集合对象根据元素属性分组，属性相同的元素组成一个子集合
     *
     * @param collection 集合对象
     * @param classifier 分类器
     * @param <K>        键的类型
     * @param <T>        值的类型
     * @return Map对象
     */
    public static <K, T> Map<K, List<T>> groupingBy(Collection<T> collection, Function<? super T, ? extends K> classifier) {
        if (Collections.isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 集合对象转为Map对象
     *
     * @param collection 集合对象
     * @param keyMapper  键的转换器，非空
     * @param <K>        键的类型
     * @param <T>        集合的元素类型
     * @return Map对象
     */
    public static <K, T> Map<K, T> toMap(Collection<T> collection, @NotNull Function<? super T, ? extends K> keyMapper) {
        return toMap(collection, keyMapper, Function.identity());
    }

    /**
     * 集合对象转为Map对象
     *
     * @param collection  集合对象
     * @param keyMapper   键的转换器，非空
     * @param valueMapper 值的转换器
     * @param <K>         键的类型
     * @param <V>         值的类型
     * @param <T>         集合的元素类型
     * @return Map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, @NotNull Function<? super T, ? extends K> keyMapper,
                                            @NotNull Function<? super T, ? extends V> valueMapper) {
        return toMap(collection, keyMapper, valueMapper, pickLast());
    }

    /**
     * 集合对象转为Map对象
     *
     * @param collection    集合对象
     * @param keyMapper     键的转换器，非空
     * @param valueMapper   值的转换器
     * @param mergeFunction 合并的函数，如果键相同的时候
     * @param <K>           键的类型
     * @param <V>           值的类型
     * @param <T>           集合的元素类型
     * @return Map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, @NotNull Function<? super T, ? extends K> keyMapper,
                                            @NotNull Function<? super T, ? extends V> valueMapper,
                                            @NotNull BinaryOperator<V> mergeFunction) {
        if (Collections.isEmpty(collection)) {
            return emptyMap();
        }
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * 转换Map中值
     *
     * @param originalMap    源Map对象
     * @param valueConverter 值的转换器
     * @param <K>            键的类型
     * @param <V>            值的类型
     * @param <R>            值的新类型
     * @return 转换后的Map对象
     */
    public static <K, V, R> Map<K, R> convertValue(Map<K, V> originalMap, @NotNull BiFunction<K, V, R> valueConverter) {
        return convertValue(originalMap, valueConverter, pickLast());
    }

    /**
     * 转换Map中值
     *
     * @param originalMap    源Map对象
     * @param valueConverter 值的转换器
     * @param mergeFunction  值的合并操作
     * @param <K>            键的类型
     * @param <V>            值的类型
     * @param <R>            值的新类型
     * @return 转换后的Map对象
     */
    public static <K, V, R> Map<K, R> convertValue(Map<K, V> originalMap, @NotNull BiFunction<K, V, R> valueConverter,
                                                   @Nullable BinaryOperator<R> mergeFunction) {
        if (isEmpty(originalMap)) {
            return emptyMap();
        }
        BinaryOperator<R> bo = (mergeFunction == null) ? pickLast() : mergeFunction;
        return originalMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                entry -> valueConverter.apply(entry.getKey(), entry.getValue()), bo));
    }

    /**
     * 转换Map中值
     *
     * @param originalMap 源Map对象
     * @param converter   值的转换器
     * @param <K>         键的类型
     * @param <V>         值的类型
     * @param <R>         值的新类型
     * @return 转换后的Map对象
     */
    public static <K, V, R> Map<K, R> convertValue(Map<K, V> originalMap, @NotNull Function<V, R> converter) {
        return convertValue(originalMap, converter, pickLast());
    }

    /**
     * 转换Map中值
     *
     * @param originalMap   源Map对象
     * @param converter     值的转换器
     * @param mergeFunction 值的合并操作
     * @param <K>           键的类型
     * @param <V>           值的类型
     * @param <R>           值的新类型
     * @return 转换后的Map对象
     */
    public static <K, V, R> Map<K, R> convertValue(Map<K, V> originalMap, @NotNull Function<V, R> converter, @Nullable BinaryOperator<R> mergeFunction) {
        if (isEmpty(originalMap)) {
            return emptyMap();
        }
        BinaryOperator<R> bo = (mergeFunction == null) ? pickLast() : mergeFunction;
        return originalMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> converter.apply(entry.getValue()), bo));
    }

    /**
     * 内置的选择最后一个元素
     *
     * @param <T> 元素类型
     * @return 最后一个元素
     */
    public static <T> BinaryOperator<T> pickLast() {
        return (t, t2) -> t2;
    }

    // endregion

}
