package cn.tmkit.core.lang;

import cn.tmkit.core.function.Action;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author miles.tang
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
        return arrayList(0);
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
    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> of(E... values) {
        return arrayList(values);
    }

    /**
     * 新建一个指定容量大小的{@linkplain ArrayList}
     *
     * @param capacity 容量
     * @param <E>      集合元素类型
     * @return ArrayList对象
     */
    public static <E> ArrayList<E> of(int capacity) {
        return new ArrayList<>(capacity);
    }

    /**
     * 新建一个指定容量大小的{@linkplain ArrayList}
     *
     * @param capacity 容量
     * @param <E>      集合元素类型
     * @return ArrayList对象
     */
    public static <E> ArrayList<E> arrayList(int capacity) {
        return new ArrayList<>(capacity);
    }

    /**
     * 新建一个指定容量大小的{@linkplain ArrayList}
     *
     * @param capacity 容量
     * @param <E>      集合元素类型
     * @return ArrayList对象
     */
    public static <E> ArrayList<E> newArrayList(int capacity) {
        return arrayList(capacity);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <E>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <E> ArrayList<E> arrayList(E... values) {
        if (Arrays.isEmpty(values)) {
            return new ArrayList<>();
        }
        ArrayList<E> result = new ArrayList<>(values.length);
        addAll(result, values);
        return result;
    }

    /**
     * 新建一个ArrayList
     *
     * @param <E>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... values) {
        return arrayList(values);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <E>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    public static <E> ArrayList<E> arrayList(Collection<E> values) {
        if (isEmpty(values)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(values);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <E>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    public static <E> ArrayList<E> newArrayList(Collection<E> values) {
        return arrayList(values);
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
        return isLinked ? linkedList(values) : arrayList(values);
    }

    /**
     * 创建一个{@linkplain LinkedList}
     *
     * @param values 初始的元素列表
     * @param <E>    集合中的元素
     * @return {@link LinkedList}
     */
    @SafeVarargs
    public static <E> LinkedList<E> linkedList(E... values) {
        LinkedList<E> list = new LinkedList<>();
        if (values != null && values.length != 0) {
            addAll(list, values);
        }
        return list;
    }

    /**
     * 创建一个只有一个元素的集合
     *
     * @param element 元素的值
     * @param <E>     元素类型
     * @return 集合
     * @see java.util.Collections#singletonList(Object)
     */
    public static <E> List<E> singletonList(E element) {
        return java.util.Collections.singletonList(element);
    }

    /**
     * 新建一个指定容量大小的{@linkplain HashSet}
     *
     * @param capacity 指定初始容量
     * @param <E>      元素类型
     * @return HashSet对象
     */
    public static <E> HashSet<E> hashSet(int capacity) {
        return new HashSet<>((int) (capacity / Maps.DEFAULT_LOAD_FACTOR));
    }

    /**
     * 新建{@linkplain HashSet}
     *
     * @param values 初始元素列表
     * @param <E>    元素类型
     * @return HashSet对象
     */
    @SafeVarargs
    public static <E> HashSet<E> hashSet(E... values) {
        int capacity = Arrays.isEmpty(values) ? Maps.DEFAULT_INITIAL_CAPACITY : values.length;
        HashSet<E> hashSet = hashSet(capacity);
        Arrays.forEach(values, hashSet::add);
        return hashSet;
    }

    /**
     * 新建{@linkplain HashSet}
     *
     * @param values 初始元素列表
     * @param <E>    元素类型
     * @return HashSet对象
     */
    public static <E> HashSet<E> hashSet(Collection<E> values) {
        int capacity = isEmpty(values) ? Maps.DEFAULT_INITIAL_CAPACITY : values.size();
        HashSet<E> hashSet = hashSet(capacity);
        hashSet.addAll(values);
        return hashSet;
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
    public static <E> void sort(List<E> list, Comparator<? super E> comparator) {
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
    public static <T> String join(final Collection<T> c) {
        return join(c, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param c         集合数据
     * @param separator 分隔符
     * @return 字符串
     */
    public static <T> String join(final Collection<T> c, String separator) {
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
    public static <T> String join(final Collection<T> src, final boolean ignoreNull) {
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
    public static <T> String join(final Collection<T> src, String separator, final boolean ignoreNull) {
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
    public static <T> String join(final Collection<T> src, String separator, final boolean ignoreNull, final boolean sortable) {
        if (src == null) {
            return null;
        }
        if (src.isEmpty()) {
            return Strings.EMPTY_STRING;
        }
        if (separator == null) {
            separator = Strings.EMPTY_STRING;
        }

        Collection<T> resultColl = src;
        if (sortable) {
            resultColl = new TreeSet<>(src);
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Object obj : resultColl) {
            if (ignoreNull && obj == null) {
                continue;
            }
            if (isFirst) {
                sb.append(obj);
                isFirst = false;
            } else {
                sb.append(separator).append(obj);
            }
        }
        return sb.toString();
    }

    // endregion


    // region 添加

    /**
     * 将所有指定的元素添加到集合中
     *
     * @param coll     集合
     * @param elements 待添加的元素
     * @param <E>      元的类型
     */
    @SafeVarargs
    public static <E> void addAll(Collection<E> coll, E... elements) {
        if (coll == null || Arrays.isEmpty(elements)) {
            return;
        }
        java.util.Collections.addAll(coll, elements);
    }

    /**
     * 将所有指定的元素添加到集合中
     *
     * @param coll     集合
     * @param elements 待添加的元素
     * @param <E>      元的类型
     */
    public static <E> void addAll(Collection<E> coll, Collection<E> elements) {
        if (coll == null || isEmpty(elements)) {
            return;
        }
        coll.addAll(elements);
    }

    // endregion


    // region general methods

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
        if (isEmpty(coll) && Arrays.isEmpty(elements)) {
            return (coll == null) ? emptyList() : coll;
        }
        if (isNotEmpty(coll) && Arrays.isEmpty(elements)) {
            return coll;
        }
        if (isEmpty(coll) && Arrays.isNotEmpty(elements)) {
            ArrayList<E> arrayList = new ArrayList<>();
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

    /**
     * 遍历集合元素，并执行动作{@code action}
     *
     * @param coll   集合列表
     * @param action 执行动作
     * @param <E>    集合的元素类型
     */
    public static <E> void forEach(Collection<E> coll, Consumer<E> action) {
        if (isEmpty(coll) || Objects.isNull(action)) {
            return;
        }
        coll.forEach(action);
    }

    /**
     * 非空操作
     *
     * @param c        集合
     * @param consumer 非空的操作
     * @param <E>      元素类型
     */
    public static <E> void isNotEmpty(Collection<E> c, Consumer<Collection<E>> consumer) {
        if (isEmpty(c) || Objects.isNull(consumer)) {
            return;
        }
        consumer.accept(c);
    }

    /**
     * 非空操作
     *
     * @param c      集合
     * @param action 非空的操作
     * @param <E>    元素类型
     */
    public static <E> void isNotEmpty(Collection<E> c, Action action) {
        if (isEmpty(c) || Objects.isNull(action)) {
            return;
        }
        action.execute();
    }

    // endregion


    // region get methods

    /**
     * 获取集合中指定下标的元素值，下标可以为负数，例如-1表示最后一个元素
     *
     * @param <E>        元素类型
     * @param collection 集合
     * @param index      下标，支持负数
     * @return 元素值
     */
    public static <E> E get(Collection<E> collection, final int index) {
        if (isEmpty(collection)) {
            return null;
        }
        int size = collection.size();
        // 负数的处理
        int pos = (index < 0) ? (index + size) : index;
        // 下标越界
        if (pos < 0 || pos >= size) {
            return null;
        }
        if (collection instanceof List) {
            final List<E> list = ((List<E>) collection);
            return list.get(index);
        }
        return get(collection.iterator(), pos);
    }

    /**
     * 遍历{@linkplain  Iterator}，获取指定{@code index}位置的元素
     *
     * @param iterator 迭代器
     * @param index    元素的位置，不能为负数
     * @param <E>      元素类型
     * @return 指定位置的元素值或{@code null}
     */
    public static <E> E get(Iterator<E> iterator, int index) {
        Asserts.isTrue(index >= 0, "The index must be greater than or equal to 0.");
        if (iterator == null) {
            return null;
        }
        while (iterator.hasNext()) {
            index--;
            if (index == -1) {
                return iterator.next();
            }
            iterator.next();
        }
        return null;
    }

    // endregion


    // region 集合转换

    /**
     * 集合对象转为List集合
     *
     * @param collection 集合对象
     * @param <E>        元素类型
     * @return List集合
     */
    @NotNull
    public static <E> List<E> toList(Collection<E> collection) {
        if (collection == null) {
            return emptyList();
        }
        if (collection instanceof List) {
            return (List<E>) collection;
        }
        return newArrayList(collection);
    }

    /**
     * 集合对象转为Set集合
     *
     * @param collection 集合对象
     * @param <E>        元素类型
     * @return Set集合
     */
    @NotNull
    public static <E> Set<E> toSet(Collection<E> collection) {
        if (collection == null) {
            return emptySet();
        }
        if (collection instanceof Set) {
            return (Set<E>) collection;
        }
        return new HashSet<>(collection);
    }

    /**
     * 集合对象使用转换器转为List集合
     *
     * @param collection 集合悐
     * @param mapper     转换器，非空
     * @param <E>        原集合的元素类型
     * @param <R>        目标集合的元素的类型
     * @return List集合
     */
    @NotNull
    public static <E, R> List<R> toList(Collection<E> collection, @NotNull Function<E, R> mapper) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合对象使用转换器转为Set集合
     *
     * @param collection 集合悐
     * @param mapper     转换器，非空
     * @param <E>        原集合的元素类型
     * @param <R>        目标集合的元素的类型
     * @return Set集合
     */
    public static <E, R> Set<R> toSet(Collection<E> collection, @NotNull Function<E, R> mapper) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    // endregion


    // region 最大值、最小值

    /**
     * 根据集合元素中某一个属性的值最大，返回这个元素，如果匹配不到则返回{@code null}
     *
     * @param collection   集合对象
     * @param keyExtractor 元素的属性映射器
     * @param <E>          元素类型
     * @param <K>          属性类型
     * @return 最大的元素
     */
    public static <E, K extends Comparable<K>> E maxElement(Collection<E> collection, Function<? super E, ? extends K> keyExtractor) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().max(Comparator.comparing(keyExtractor)).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> Integer maxInt(Collection<E> collection, ToIntFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalInt optionalInt = collection.stream().mapToInt(mapper).max();
        if (optionalInt.isPresent()) {
            return optionalInt.getAsInt();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> Long maxLong(Collection<E> collection, ToLongFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalLong optionalLong = collection.stream().mapToLong(mapper).max();
        if (optionalLong.isPresent()) {
            return optionalLong.getAsLong();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> Double maxLong(Collection<E> collection, ToDoubleFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalDouble optionalDouble = collection.stream().mapToDouble(mapper).max();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> BigDecimal maxBigDecimal(Collection<E> collection, Function<E, BigDecimal> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).max(BigDecimal::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> LocalDate maxLocalDate(Collection<E> collection, Function<E, LocalDate> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).max(LocalDate::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> LocalTime maxLocalTime(Collection<E> collection, Function<E, LocalTime> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).max(LocalTime::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最大值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最大值或{@code null}
     */
    public static <E> LocalDateTime maxLocalDateTime(Collection<E> collection, Function<E, LocalDateTime> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).max(LocalDateTime::compareTo).orElse(null);
    }

    /**
     * 根据集合元素中某一个属性的值最小，返回这个元素，如果匹配不到则返回{@code null}
     *
     * @param collection   集合对象
     * @param keyExtractor 元素的属性映射器
     * @param <E>          元素类型
     * @param <K>          属性类型
     * @return 最小的元素
     */
    public static <E, K extends Comparable<K>> E minElement(Collection<E> collection, Function<? super E, ? extends K> keyExtractor) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().min(Comparator.comparing(keyExtractor)).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> Integer minInt(Collection<E> collection, ToIntFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalInt optionalInt = collection.stream().mapToInt(mapper).min();
        if (optionalInt.isPresent()) {
            return optionalInt.getAsInt();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> Long minLong(Collection<E> collection, ToLongFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalLong optionalLong = collection.stream().mapToLong(mapper).min();
        if (optionalLong.isPresent()) {
            return optionalLong.getAsLong();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> Double minLong(Collection<E> collection, ToDoubleFunction<E> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        OptionalDouble optionalDouble = collection.stream().mapToDouble(mapper).min();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        return null;
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> BigDecimal minBigDecimal(Collection<E> collection, Function<E, BigDecimal> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).min(BigDecimal::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> LocalDate minLocalDate(Collection<E> collection, Function<E, LocalDate> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).min(LocalDate::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> LocalTime minLocalTime(Collection<E> collection, Function<E, LocalTime> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).min(LocalTime::compareTo).orElse(null);
    }

    /**
     * 获取集合元素中某个属性的最小值
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 最小值或{@code null}
     */
    public static <E> LocalDateTime minLocalDateTime(Collection<E> collection, Function<E, LocalDateTime> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).min(LocalDateTime::compareTo).orElse(null);
    }

    // endregion


    // region 求和

    /**
     * 求和
     *
     * @param collection 集合元素
     * @return 求和的值
     */
    public static int sumInt(Collection<Integer> collection) {
        return sumInt(collection, Integer::intValue);
    }

    /**
     * 获取集合元素中某个属性的值求和
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 求和
     */
    public static <E> int sumInt(Collection<E> collection, ToIntFunction<E> mapper) {
        if (isEmpty(collection)) {
            return 0;
        }
        return collection.stream().mapToInt(mapper).sum();
    }

    /**
     * 求和
     *
     * @param collection 集合元素
     * @return 求和的值
     */
    public static long sumLong(Collection<Long> collection) {
        return sumLong(collection, Long::longValue);
    }

    /**
     * 获取集合元素中某个属性的值求和
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 求和
     */
    public static <E> long sumLong(Collection<E> collection, ToLongFunction<E> mapper) {
        if (isEmpty(collection)) {
            return 0;
        }
        return collection.stream().mapToLong(mapper).sum();
    }

    /**
     * 求和
     *
     * @param collection 集合元素
     * @return 求和的值
     */
    public static double sumDouble(Collection<Double> collection) {
        return sumDouble(collection, Double::doubleValue);
    }

    /**
     * 获取集合元素中某个属性的值求和
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 求和
     */
    public static <E> double sumDouble(Collection<E> collection, ToDoubleFunction<E> mapper) {
        if (isEmpty(collection)) {
            return 0;
        }
        return collection.stream().mapToDouble(mapper).sum();
    }

    /**
     * 获取集合元素中某个属性的值求和
     *
     * @param collection 集合对象
     * @param mapper     元素的属性映射器
     * @param <E>        元素的类型
     * @return 求和
     */
    public static <E> BigDecimal sumBigDecimal(Collection<E> collection, Function<E, BigDecimal> mapper) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().map(mapper).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // endregion


    // region 集合分片

    /**
     * 集合分片，根据入参{@code size}指定分片大小。
     * <p>简单来讲就是将一个大集合分为N个小集合，每个小集合的元素个数为{@code size}（最后一个集合的元素个数可能小于{@code size}）</p>
     * <p>适用于批量插入，防止因为集合过大造成内存溢出等场景</p>
     *
     * @param coll 源集合
     * @param size 小集合的元素数量
     * @param <T>  元素类型
     * @return 分片列表
     */
    public static <T> List<List<T>> partition(Collection<T> coll, int size) {
        if (isEmpty(coll)) {
            return emptyList();
        }
        return new Partition<>(coll, size);
    }

    /**
     * 集合分片，根据入参{@code size}指定分片大小。
     * <p>简单来讲就是将一个大集合分为N个小集合，每个小集合的元素个数为{@code size}（最后一个集合的元素个数可能小于{@code size}）</p>
     * <p>适用于批量插入，防止因为集合过大造成内存溢出等场景</p>
     *
     * @param coll 源集合
     * @param size 小集合的元素数量
     * @param <T>  元素类型
     * @return 分片列表
     */
    public static <T> List<List<T>> split(Collection<T> coll, int size) {
        return partition(coll, size);
    }

    private static class Partition<T> extends AbstractList<List<T>> {

        private final List<T> list;
        private final int size;

        private Partition(Collection<T> coll, int size) {
            this.list = newArrayList(coll);
            this.size = Math.min(size, list.size());
        }

        private Partition(List<T> coll, int size) {
            this.list = newArrayList(coll);
            this.size = Math.min(size, list.size());
        }

        /**
         * {@inheritDoc}
         *
         * @param index
         * @throws IndexOutOfBoundsException {@inheritDoc}
         */
        @Override
        public List<T> get(int index) {
            int start = index * size;
            int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        @Override
        public int size() {
            return (int) Math.ceil((double) list.size() / (double) size);
        }

    }
    // endregion

}
