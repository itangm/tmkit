package cn.tmkit.core.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 集合工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Collections {

    // region 判断集合是否为空

    /**
     * 判断集合是否为空
     * <pre class="code">CollectionUtil.isEmpty(list);</pre>
     *
     * @param c 集合
     * @return 如果集合为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Collection<?> c) {
        return (c == null || c.isEmpty());
    }

    /**
     * 判断集合不为空
     *
     * @param c 集合
     * @return 不为空则返回{@code true}，否则返回{@code  false}
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    // endregion


    // region 空集合

    /**
     * 空集合，不可变
     *
     * @param <E> 集合元素类型
     * @return 不可变的空集合
     * @see java.util.Collections#emptyList()
     */
    public static <E> List<E> emptyList() {
        return java.util.Collections.emptyList();
    }

    /**
     * 空集合，可变的，所谓可变的就是可以往集合中增删
     *
     * @param <E> 集合元素类型
     * @return 可变的空集合
     */
    public static <E> List<E> mutableList() {
        return new ArrayList<>(0);
    }

    /**
     * 空的Set，不可变
     *
     * @param <E> 元素类型
     * @return 不可变的空Set
     * @see java.util.Collections#emptySet()
     */
    public static <E> Set<E> emptySet() {
        return java.util.Collections.emptySet();
    }

    /**
     * 空的Set，可变的，所谓可变的就是可以往Set中增删
     *
     * @param <E> 元素类型
     * @return 可变的空Set
     */
    public static <E> Set<E> mutableSet() {
        return new HashSet<>(0);
    }

    // endregion

    // region 创建集合

    /**
     * 新建一个ArrayList
     *
     * @param <E>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... values) {
        if (values == null || values.length == 0) {
            return new ArrayList<>();
        }
        ArrayList<E> result = new ArrayList<>(values.length);
        addAll(result, values);
        return result;
    }

    /**
     * 新建一个空List
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     */
    public static <E> List<E> list(boolean isLinked) {
        return list(isLinked, (E) null);
    }

    /**
     * 新建一个List
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     */
    @SafeVarargs
    public static <E> List<E> list(boolean isLinked, E... values) {
        return isLinked ? newLinkedList(values) : newArrayList(values);
    }

    /**
     * 创建一个{@linkplain LinkedList}
     *
     * @param values 初始的元素列表
     * @param <E>    集合中的元素
     * @return {@link LinkedList}
     */
    @SafeVarargs
    public static <E> LinkedList<E> newLinkedList(E... values) {
        LinkedList<E> list = new LinkedList<>();
        if (values != null && values.length != 0) {
            addAll(list, values);
        }
        return list;
    }

    // endregion


    // region 排序

    /**
     * 集合排序，根据集合元素实现的排序规则进行排序
     *
     * @param list 集合
     * @param <E>  集合中的元素
     * @see #sort(List, Comparator)
     */
    public static <E extends Comparable<? super E>> void sort(List<E> list) {
        sort(list, null);
    }

    /**
     * 按照自定义排序规则对集合排序
     *
     * @param list       集合
     * @param comparator 自定义排序规则
     * @param <E>        集合中的元素
     * @see java.util.Collections#sort(List, Comparator)
     */
    @SuppressWarnings("Java8ListSort")
    public static <E> void sort(List<E> list, @Nullable Comparator<? super E> comparator) {
        if (isNotEmpty(list)) {
            java.util.Collections.sort(list, comparator);
        }
    }

    /**
     * 集合洗牌，打乱
     *
     * @param list 集合
     * @param <E>  集合中的元素
     * @see java.util.Collections#shuffle(List)
     */
    public static <E> void shuffle(List<E> list) {
        if (isNotEmpty(list)) {
            java.util.Collections.shuffle(list);
        }
    }

    /**
     * 指定随机源将集合打乱，洗牌
     *
     * @param list 集合
     * @param rand 随机源
     * @param <E>  集合中的元素
     * @see java.util.Collections#shuffle(List, Random)
     */
    public static <E> void shuffle(List<E> list, @NotNull Random rand) {
        if (isNotEmpty(list)) {
            java.util.Collections.shuffle(list, rand);
        }
    }

    // endregion


    // region JOIN相关方法

    /**
     * 将集合数据转为字符串，每个元素之间采用英文逗号 <span style="color: red;">,</span> 拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param c 集合数据
     * @return 字符串
     */
    public static String join(final Collection<String> c) {
        return join(c, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param c       集合数据
     * @param separator 分隔符
     * @return 字符串
     */
    public static String join(final Collection<String> c, String separator) {
        return join(c, separator, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用,拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * @param src        集合数据
     * @param ignoreNull 值为{@code null}忽略
     * @return 字符串
     */
    public static String join(final Collection<String> src, final boolean ignoreNull) {
        return join(src, Strings.COMMA, ignoreNull);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 忽略null
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull) {
        return join(src, separator, ignoreNull, false);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * <p>Note:如果Key要先排序则集合元素中不能存在{@code null}，因为{@linkplain TreeSet}数据结构规定</p>
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 值为{@code null}忽略
     * @param sortable   值为{@code true}则正序排序，否则默认
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull, final boolean sortable) {
        if (src == null) {
            return null;
        }
        if (src.isEmpty()) {
            return Strings.EMPTY_STRING;
        }
        if (separator == null) {
            separator = Strings.EMPTY_STRING;
        }

        Collection<String> resultColl = src;
        if (sortable) {
            resultColl = new TreeSet<>(src);
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String str : resultColl) {
            if (ignoreNull && str == null) {
                continue;
            }
            if (isFirst) {
                sb.append(str);
                isFirst = false;
            } else {
                sb.append(separator).append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 将所有指定的元素添加到集合中
     *
     * @param coll     集合
     * @param elements 待添加的元素
     * @param <E>      元的类型
     * @return 如果集合有变化则返回{@code true}
     */
    @SafeVarargs
    public static <E> boolean addAll(Collection<? super E> coll, E... elements) {
        if (coll == null || (elements == null || elements.length == 0)) {
            return false;
        }
        return java.util.Collections.addAll(coll, elements);
    }

    /**
     * 将集合和数组合并到新的集合中
     * 如果源集合是{@code List}接口则返回{@code ArrayList}；如果源集合是{@code Set}则返回{@code HashSet}
     *
     * @param coll     源集合
     * @param elements 数组
     * @param <E>      元素类型
     * @return 新集合
     */
    @SafeVarargs
    public static <E> Collection<? extends E> merge(Collection<? extends E> coll, E... elements) {
        if (isEmpty(coll) && (elements == null || elements.length == 0)) {
            return (coll == null) ? emptyList() : coll;
        }
        if (isNotEmpty(coll) && (elements == null || elements.length == 0)) {
            return coll;
        }
        if (isEmpty(coll) && !(elements == null || elements.length == 0)) {
            ArrayList<E> arrayList = new ArrayList<E>();
            addAll(arrayList, elements);
            return arrayList;
        }
        Collection<E> results;
        if (coll instanceof List) {
            results = new ArrayList<>(coll.size() + elements.length);
        } else if (coll instanceof Set) {
            results = new HashSet<>(coll.size() + elements.length);
        } else {
            throw new UnsupportedOperationException("Unsupported collection type :" + coll.getClass());
        }
        results.addAll(coll);
        addAll(results, elements);
        return results;
    }

    // endregion


}
