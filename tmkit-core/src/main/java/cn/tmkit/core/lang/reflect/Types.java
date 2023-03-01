package cn.tmkit.core.lang.reflect;

import cn.tmkit.core.lang.Arrays;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Java类的类型工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class Types {

    private static final Map<Class<?>, Object> EMPTIES;

    static {
        final Map<Class<?>, Object> empties = new LinkedHashMap<>();
        empties.put(boolean.class, false);
        empties.put(Boolean.class, false);
        empties.put(byte[].class, new byte[0]);
        empties.put(Collection.class, Collections.emptyList());
        empties.put(Iterator.class, Collections.emptyIterator());
        empties.put(List.class, Collections.emptyList());
        empties.put(Map.class, Collections.emptyMap());
        empties.put(Set.class, Collections.emptySet());
        empties.put(Optional.class, Optional.empty());
        empties.put(Stream.class, Stream.empty());
        EMPTIES = Collections.unmodifiableMap(empties);
    }

    /**
     * 返回类型的空值
     *
     * @param type 类型
     * @return 对应的空值
     */
    public static Object emptyValueOf(Type type) {
        return EMPTIES.getOrDefault(getRawType(type), null);
    }

    /**
     * 是否未知类型<br>
     * type为null或者{@link TypeVariable} 都视为未知类型
     *
     * @param type Type类型
     * @return 是否未知类型
     */
    public static boolean isUnknown(Type type) {
        return null == type || type instanceof TypeVariable;
    }

    /**
     * 获得Type对应的原始类
     *
     * @param theType {@link Type}
     * @return 原始类，如果无法获取原始类，返回{@code null}
     */
    public static Class<?> getRawType(Type theType) {
        if (theType != null) {
            // type is a normal class
            if (theType instanceof Class) {
                return (Class<?>) theType;
            } else if (theType instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) theType).getRawType();
                if (!(rawType instanceof Class<?>)) {
                    throw new IllegalArgumentException("Can not resolve type " + theType);
                }
                return (Class<?>) rawType;
            } else if (theType instanceof GenericArrayType) {
                Type genericComponentType = ((GenericArrayType) theType).getGenericComponentType();
                return Array.newInstance(getRawType(genericComponentType), 0).getClass();
            } else if (theType instanceof TypeVariable) {
                return (Class<?>) ((TypeVariable<?>) theType).getBounds()[0];
            } else if (theType instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType) theType).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getRawType(upperBounds[0]);
                }
            }
        }
        return null;
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param theType 被检查的类型，必须是已经确定泛型类型的类
     * @param index   泛型类型的索引号，即第几个泛型类型
     * @return {@link Type}
     */
    public static Type getGenericType(Type theType, int index) {
        Type[] genericTypes = getGenericTypes(theType);
        if (null != genericTypes && genericTypes.length > index) {
            return genericTypes[index];
        }
        return null;
    }

    /**
     * 获得指定类型中所有泛型参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到String
     *
     * @param theType 指定类型
     * @return 所有泛型参数类型
     */
    public static Type[] getGenericTypes(Type theType) {
        if (theType == null) {
            return null;
        }
        ParameterizedType parameterizedType = toParameterizedType(theType);
        return (parameterizedType == null) ? null : parameterizedType.getActualTypeArguments();
    }

    /**
     * 将{@link Type} 转换为{@link ParameterizedType}<br>
     * {@link ParameterizedType}用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到B{@link ParameterizedType}，从而获取到String
     *
     * @param theType {@link Type}
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(final Type theType) {
        ParameterizedType result = null;
        if (theType instanceof ParameterizedType) {
            result = (ParameterizedType) theType;
        } else if (theType instanceof Class) {
            final Class<?> clazz = (Class<?>) theType;
            Type genericSuperclass = clazz.getGenericSuperclass();
            if (genericSuperclass == null || Object.class.equals(genericSuperclass)) {
                // 如果类没有父类，而是实现一些定义好的泛型接口，则取接口的Type
                Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (Arrays.isNotEmpty(genericInterfaces)) {
                    // 默认取第一个实现接口的泛型Type
                    genericSuperclass = genericInterfaces[0];
                }
            }
            result = toParameterizedType(genericSuperclass);
        }
        return result;
    }

}
