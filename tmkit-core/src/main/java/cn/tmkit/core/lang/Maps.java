package cn.tmkit.core.lang;

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
     * @see #newHashMap(int)
     * @see #newLinkedHashMap(int)
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
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
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
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
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
    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
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
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
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
     * @see #newTreeMap(Map, Comparator)
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
        return newTreeMap(map, comparator);
    }

    // endregion


    // region general

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


    // region
    // endregion

}
