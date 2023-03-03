package cn.tmkit.core.lang;

import cn.tmkit.core.exception.ClassNotFoundRuntimeException;
import cn.tmkit.core.lang.reflect.ReflectUtil;
import cn.tmkit.core.lang.reflect.Reflects;
import cn.tmkit.core.lang.reflect.TypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Java类的工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class Classes {

    /**
     * Map with primitive wrapper type as key and corresponding primitive
     * type as value, for example: Integer.class -> int.class.
     */
    static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    /**
     * Map with primitive type as key and corresponding wrapper
     * type as value, for example: int.class -> Integer.class.
     */
    static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 空数组(boolean类型)
     */
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    /**
     * @return 获得Java ClassPath路径，不包括 jre
     */
    public static String[] getJavaClassPaths() {
        return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    }

    /**
     * {@code null}安全的获取对象类型
     *
     * @param <T> 对象类型
     * @param obj 对象，如果为{@code null} 返回{@code null}
     * @return 对象类型，提供对象如果为{@code null} 返回{@code null}
     */
    public static <T> Class<T> getClass(T obj) {
        //noinspection unchecked
        return (obj == null) ? null : (Class<T>) obj.getClass();
    }

    /**
     * 获取对象数组的类型数组
     *
     * @param values 对象数组
     * @return 其对应的类型数组
     */
    public static Class<?>[] getClasses(Object... values) {
        if (Arrays.isEmpty(values)) {
            return EMPTY_CLASS_ARRAY;
        }
        return Arrays.stream(values)
                .map(value -> Objects.isNull(value) ? Objects.class : values.getClass())
                .toArray(Class<?>[]::new);
    }

    /**
     * 获取指定类型的默认值
     * <p>
     * 默认值规则
     * 原始数值类型返回0
     * 原始boolean类型返回false
     * 其它均返回为null
     * </p>
     *
     * @param clazz 类
     * @return 类的默认值
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz == null) {
            return null;
        } else if (clazz.isPrimitive()) {
            if (clazz == boolean.class) {
                return false;
            } else if (clazz == byte.class) {
                return (byte) 0;
            } else if (clazz == char.class) {
                return (char) 0;
            } else if (clazz == short.class) {
                return (short) 0;
            } else if (clazz == int.class) {
                return 0;
            } else if (clazz == long.class) {
                return 0L;
            } else if (clazz == float.class) {
                return 0F;
            } else if (clazz == double.class) {
                return 0D;
            }
        }
        return null;
    }

    /**
     * 获取类型的默认值
     *
     * @param parameterTypes 值类型
     * @return 对饮的默认值
     */
    public static Object[] getDefaultValues(Class<?>[] parameterTypes) {
        if (Arrays.isEmpty(parameterTypes)) {
            return Arrays.EMPTY_OBJECT_ARRAY;
        }
        return Arrays.stream(parameterTypes).map(Classes::getDefaultValue).toArray(Object[]::new);
    }

    /**
     * 判断sourceType是否是targetType父类或接口，其类的本身
     *
     * @param sourceType 被检查的类型
     * @param targetType 被检查的类型
     * @return 如果是则返回{@code true}，否则返回{@code false}
     * @see Class#isAssignableFrom(Class)
     */
    public static boolean isAssignable(Class<?> sourceType, Class<?> targetType) {
        if (Arrays.isAnyNull(sourceType, targetType)) {
            return false;
        }
        if (sourceType.isAssignableFrom(targetType)) {
            return true;
        }
        if (sourceType.isPrimitive()) {
            Class<?> resolvedPrimitive = PRIMITIVE_WRAPPER_TYPE_MAP.get(targetType);
            return sourceType == resolvedPrimitive;
        } else {
            Class<?> resolvedWrapper = PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(targetType);
            return resolvedWrapper != null && sourceType.isAssignableFrom(resolvedWrapper);
        }
    }

    /**
     * 判断sourceTypes是否全部为targetTypes的类本身、父类或接口
     *
     * @param sourceTypes 待匹配的类型
     * @param targetTypes 待检测的类型
     * @return 如果均满足则返回{@code true},否则返回{@code false}
     */
    public static boolean isAllAssignable(Class<?>[] sourceTypes, Class<?>[] targetTypes) {
        if (Arrays.isEmpty(sourceTypes) && Arrays.isEmpty(targetTypes)) {
            return true;
        }
        if (null == sourceTypes || null == targetTypes) {
            return false;
        }
        if (sourceTypes.length != targetTypes.length) {
            return false;
        }
        int length = sourceTypes.length;
        for (int i = 0; i < length; i++) {
            if (!isAssignable(sourceTypes[i], targetTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断类是否是内部类
     *
     * @param clazz 类型
     * @return 是否内部类
     */
    public static boolean isInnerClass(Class<?> clazz) {
        return clazz != null && clazz.getEnclosingClass() != null;
    }

    /**
     * 是否为标准的类<br>
     * 这个类必须：
     *
     * <pre>
     * 1. 非接口
     * 2. 非抽象类
     * 3. 非Enum枚举
     * 4. 非数组
     * 5. 非注解
     * 6. 非原始类型
     * 7. 非Java综合类(合成类)
     * </pre>
     *
     * @param tClass 类
     * @return 是否为标准类
     */
    public static boolean isNormalClass(Class<?> clazz) {
        return clazz != null && (!ReflectUtil.isInterface(clazz) && !Reflects.isAbstract(clazz) && !clazz.isEnum() &&
                !clazz.isArray() && !clazz.isAnnotation() && !clazz.isPrimitive() && !clazz.isSynthetic());
    }
    /**
     * 获得给定类的第一个泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @return {@link Class}
     */
    public static Class<?> getGenericType(Class<?> clazz) {
        return getGenericType(clazz, 0);
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，即第几个泛型类型
     * @return {@link Class}
     */
    private static Class<?> getGenericType(Class<?> clazz, int index) {
        Type genericType = TypeUtil.getGenericType(clazz, index);
        return TypeUtil.getRawType(genericType);
    }

    /**
     * 加载类并初始化
     *
     * @param className 类名
     * @param <T>       泛型限定的类型
     * @return 类
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 加载类并初始化
     *
     * @param className  类名
     * @param initialize 是否初始化
     * @param <T>        泛型限定的类型
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, boolean initialize) {
        try {
            return (Class<T>) Class.forName(className, initialize, ClassLoaders.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

    /**
     * 执行方法
     *
     * @param obj    对象，如果执行静态方法，此值为{@code null}
     * @param method 对象方法或静态方法
     * @param params 方法参数，可以为空
     * @param <T>    返回的泛型限定类型
     * @return 结果
     * @see Reflects#invoke(Object, Method, Object...)
     */
    public static <T> T invoke(Object obj, Method method, Object... params) {
        return Reflects.invoke(obj, method, params);
    }

    /**
     * 获取Class定义的属性列表
     *
     * @param clazz 目标类型
     * @return 返回属性列表
     */
    public static List<Field> getFields(Class<?> clazz) {
        return Reflects.getFields(clazz);
    }

}
