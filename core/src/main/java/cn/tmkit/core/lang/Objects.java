package cn.tmkit.core.lang;

import cn.tmkit.core.exception.ClassNotFoundRuntimeException;
import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.FastByteArrayOutputStream;

import java.io.*;
import java.nio.Buffer;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 对象工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Objects {

    // region null/nonNull

    /**
     * 如果对象为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean isNull(final Object obj) {
        return obj == null;
    }

    /**
     * 判断数组的任意一个元素是否为{@code null}
     *
     * @param array 数组
     * @return 如果数组任意一个元素为{@code null}则返回{@code true}，否则返回{@code false}
     */
    public static boolean isAnyNull(final Object... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (Object obj : array) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的元素都为{@code null}
     *
     * @param array 数组
     * @return 如果数组所有元素都为{@code null}则返回{@code true}，否则返回{@code false}
     */
    public static boolean isAllNull(final Object... array) {
        if (Arrays.isEmpty(array)) {
            return true;
        }
        for (Object obj : array) {
            if (!isNull(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组的元素都不为{@code null}
     *
     * @param array 数组
     * @return 是否都不为{@code null}
     */
    public static boolean isAllNotNull(Object... array) {
        if (Arrays.isEmpty(array)) {
            return false;
        }
        for (Object obj : array) {
            if (isNull(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean nonNull(final Object obj) {
        return obj != null;
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean isNotNull(final Object obj) {
        return nonNull(obj);
    }

    // endregion


    // region empty/notEmpty

    /**
     * <p>判断对象是否为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map} 是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return Strings.isEmpty((CharSequence) obj);
        } else if (Arrays.isArray(obj)) {
            return Arrays.isEmpty(obj);
        } else if (obj instanceof Collection<?>) {
            return Collections.isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return Maps.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Buffer) {
            return ((Buffer) obj).hasRemaining();
        }
        return false;
    }

    /**
     * <p>判断对象是否为不为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map}是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * <p>判断数组中任意一个元素为空或{@code null}</p>
     * 数组本身为空或{@code null}，数组的元素支持如下类型：
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map}是否为空</li>
     * </ul>
     *
     * @param objArray 数组
     * @return 是否任意为空
     */
    public static boolean isAnyEmpty(Object... objArray) {
        if (Arrays.isEmpty(objArray)) {
            return true;
        }
        for (Object obj : objArray) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    // endregion


    // region equals and hashCode

    /**
     * <p>
     * 比较两个对象的内容(equals),如果两个对象相等则返回{@code true},如果两个中有一个为{@code null}则返回{@code false}.
     * 如果两个对象都是{@code null}则返回{@code true}.如果传入的参数类型是数组,则比较的数组里的对象内容,而不是数组引用比较.
     * </p>
     * <pre class="code">
     * Objects.equals("hello","hello"); //--- true
     * Objects.equals("hello","hell"); //--- false;
     * Objects.equals(4,4); //--- true
     * Objects.equals(new String[]{"aaaa","bbb"},new String[]{"aaaa","bbb"}); //--- true
     * </pre>
     *
     * @param a 第一个比较对象
     * @param b 第二个比较对象
     * @return 判断两个对象内容是否相等
     * @see java.util.Arrays#equals(Object[], Object[])
     */
    public static boolean equals(final Object a, final Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.equals(b)) {
            return true;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {
            if (a instanceof Object[] && b instanceof Object[]) {
                return java.util.Arrays.equals((Object[]) a, (Object[]) b);
            }
            if (a instanceof boolean[] && b instanceof boolean[]) {
                return java.util.Arrays.equals((boolean[]) a, (boolean[]) b);
            }
            if (a instanceof byte[] && b instanceof byte[]) {
                return java.util.Arrays.equals((byte[]) a, (byte[]) b);
            }
            if (a instanceof char[] && b instanceof char[]) {
                return java.util.Arrays.equals((char[]) a, (char[]) b);
            }
            if (a instanceof double[] && b instanceof double[]) {
                return java.util.Arrays.equals((double[]) a, (double[]) b);
            }
            if (a instanceof float[] && b instanceof float[]) {
                return java.util.Arrays.equals((float[]) a, (float[]) b);
            }
            if (a instanceof int[] && b instanceof int[]) {
                return java.util.Arrays.equals((int[]) a, (int[]) b);
            }
            if (a instanceof long[] && b instanceof long[]) {
                return java.util.Arrays.equals((long[]) a, (long[]) b);
            }
            if (a instanceof short[] && b instanceof short[]) {
                return java.util.Arrays.equals((short[]) a, (short[]) b);
            }
        }
        return false;
    }

    /**
     * 对象的哈希码，如果对象为{@code null}则返回0
     *
     * @param obj 对象
     * @return 哈希码
     */
    public static int hashCode(final Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    /**
     * 多个元素的哈希值
     *
     * @param values 数组元素
     * @return 哈希值
     */
    public static int hashCode(Object... values) {
        return Arrays.hashCode(values);
    }

    // endregion


    // region compare

    /**
     * 判断两个对象的大小,注意空{@code null}比非空小
     *
     * @param a   对象a
     * @param b   对象b
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T extends Comparable<? super T>> int compare(T a, T b) {
        return compare(a, b, false);
    }

    /**
     * 判断两个对象的大小
     *
     * @param a           对象a
     * @param b           对象b
     * @param nullGreater 当被比对为{@code null}时是否排序在前面,{@code true}则表示{@code null}比任何非{@code null}大,{@code false}反之
     * @param <T>         对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b, final boolean nullGreater) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return nullGreater ? 1 : -1;
        } else if (b == null) {
            return nullGreater ? -1 : 1;
        }
        return a.compareTo(b);
    }

    /**
     * 判断两个对象大小
     *
     * @param a   对象a
     * @param b   对象b
     * @param c   比较器
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        if (a == b) {
            return 0;
        }
        if (c == null) {
            throw new NullPointerException();
        }
        return c.compare(a, b);
    }

    // endregion


    // region get null safety

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * Objects.getIfNull(null, null)      = null
     * Objects.getIfNull(null, "")        = ""
     * Objects.getIfNull(null, "zz")      = "zz"
     * Objects.getIfNull("abc", *)        = "abc"
     * Objects.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj          被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @param <T>          对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T getIfNull(final T obj, final T defaultValue) {
        return isNull(obj) ? defaultValue : obj;
    }

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * Objects.getIfNull(null, null)      报错
     * Objects.getIfNull(null, () -> {return null;})        = null
     * Objects.getIfNull(null, () -> {return "zz"})      = "zz"
     * Objects.getIfNull("abc", () -> {return "*";})        = "abc"
     * Objects.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj      被检查对象，可能为{@code null}
     * @param supplier 被检查对象为{@code null}返回的提供器的函数值，不可以为{@code null}
     * @param <T>      对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T getIfNull(final T obj, Supplier<T> supplier) {
        return isNull(obj) ? Asserts.notNull(supplier).get() : obj;
    }

    /**
     * 如果给定对象为空则返回默认值，否则返回当前对象
     *
     * @param obj          被检测的对象
     * @param defaultValue 默认值函数
     * @param <T>          对象的类型
     * @return 对象值
     */
    public static <T> T getIfEmpty(final T obj, final T defaultValue) {
        return isEmpty(obj) ? defaultValue : obj;
    }

    /**
     * 如果给定对象为空则返回默认值，否则返回当前对象
     *
     * @param obj      被检测的对象
     * @param supplier 默认值
     * @param <T>      对象的类型
     * @return 对象值
     */
    public static <T> T getIfEmpty(final T obj, final Supplier<T> supplier) {
        return isEmpty(obj) ? Asserts.notNull(supplier).get() : obj;
    }

    // endregion


    // region serialization

    /**
     * 序列化对象为字节码
     *
     * @param obj 待序列化的对象
     * @throws IoRuntimeException 当序列化失败抛出该异常
     */
    public static byte[] serialize(Serializable obj) throws IoRuntimeException {
        if (obj == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(obj);
            oos.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将字节码反序列化为对象
     *
     * @param data 字节码
     * @param <T>  对象类型
     * @return 反序列化的对象
     * @throws IoRuntimeException            反序列化失败时抛出该异常
     * @throws ClassNotFoundRuntimeException 对象类型找不到时抛出该异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data) throws IoRuntimeException, ClassNotFoundRuntimeException {
        if (Arrays.isEmpty(data)) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

    // endregion

    /**
     * 对象转字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toString(Object obj) {
        return Arrays.toString(obj);
    }

}
