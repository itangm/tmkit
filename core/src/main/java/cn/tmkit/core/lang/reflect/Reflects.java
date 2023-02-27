package cn.tmkit.core.lang.reflect;

import cn.tmkit.core.convert.ConvertUtil;
import cn.tmkit.core.exception.ReflectiveOperationRuntimeException;
import cn.tmkit.core.filter.FieldFilter;
import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Classes;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.support.SimpleCache;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Java反射工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class Reflects {

    /**
     * 缓存构造器
     */
    private static final SimpleCache<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new SimpleCache<>(1024);

    /**
     * 缓存字段
     */
    private static final SimpleCache<Class<?>, List<Field>> FIELDS_CACHE = new SimpleCache<>(1024);

    /**
     * 是否为抽象类
     *
     * @param tClass 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(Class<?> tClass) {
        return Modifier.isAbstract(tClass.getModifiers());
    }

    /**
     * 判断类是否是接口
     *
     * @param ownerClass 对象class
     * @return 如果是接口则返回{@code true},否则返回@{@code false}
     */
    public static boolean isInterface(Class<?> ownerClass) {
        return ownerClass != null && Modifier.isInterface(ownerClass.getModifiers());
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否是静态方法
     */
    public static boolean isStatic(Method method) {
        return method != null && Modifier.isStatic(method.getModifiers());
    }

    /**
     * 是否为静态字段
     *
     * @param field 字段
     * @return 是否是静态字段
     */
    public static boolean isStatic(Field field) {
        return field != null && Modifier.isStatic(field.getModifiers());
    }

    /**
     * 实例化对象
     *
     * @param clazz  类
     * @param params 构造器的参数列表
     * @param <T>    泛型限定的类型
     * @return 对象
     * @throws ReflectiveOperationRuntimeException 反射异常包装类
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) {
        if (Arrays.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            try {
                return constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new ReflectiveOperationRuntimeException("The Class[" + clazz + "] newing instance occur exception,"
                        + "param =" + Arrays.toString(params), e);
            }
        }
        final Class<?>[] paramTypes = Classes.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (constructor == null) {
            throw new ReflectiveOperationRuntimeException("No constructor found : " + java.util.Arrays.toString(paramTypes));
        }
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectiveOperationRuntimeException("The Class[" + clazz + "] newing instance occur exception,"
                    + "param =" + Arrays.toString(params), e);
        }
    }

    /**
     * 获得类的指定参数类型的构造器，私有构造器也会返回
     *
     * @param clazz      类
     * @param paramTypes 参数类型，可以为空
     * @param <T>        泛型限定类型
     * @return 构造方法，如果未找到则返回{@code null}
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes) {
        if (clazz == null) {
            return null;
        }
        final Constructor<T>[] constructors = getConstructorsFromCache(clazz);
        return java.util.Arrays.stream(constructors)
                .filter(element -> Classes.isAllAssignable(element.getParameterTypes(), paramTypes))
                .peek(Reflects::setAccessible)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取一个类的所有构造器列表，优先从缓存中获取
     *
     * @param clazz 类
     * @return 构造器列表
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructorsFromCache(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.computeIfAbsent(clazz, () -> getConstructors(clazz));
    }

    /**
     * 获取一个类的所有构造器列表
     *
     * @param clazz 类
     * @return 构造器列表
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> clazz) {
        return (Constructor<T>[]) Objects.requireNonNull(clazz).getDeclaredConstructors();
    }

    /**
     * 设置方法为可访问（私有方法可以被外部调用）
     *
     * @param <T> AccessibleObject的子类，比如Class、Method、Field等
     * @param ao  可设置访问权限的对象，比如Class、Method、Field等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T ao) {
        if (null != ao && !ao.isAccessible()) {
            ao.setAccessible(true);
        }
        return ao;
    }

    /**
     * 执行方法
     *
     * @param obj    对象，如果执行静态方法，此值为{@code null}
     * @param method 对象方法或静态方法
     * @param params 方法参数，可以为空
     * @param <T>    返回的泛型限定类型
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, Method method, Object... params) {
        setAccessible(method);
        try {
            return (T) method.invoke(isStatic(method) ? null : obj, params);
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new ReflectiveOperationRuntimeException("The Method [" + method + "] invoked occur exception obj = "
                    + obj + " , params = " + Arrays.toString(params), e);
        }
    }

    /**
     * 获取字段值
     *
     * @param obj   对象，静态字段则为{@code null}
     * @param field 字段
     * @return 字段的值
     */
    public static Object getFieldValue(Object obj, Field field) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Class<?>) {
            // 静态字段获取时对象为null
            obj = null;
        }
        setAccessible(field);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new ReflectiveOperationRuntimeException("The Field [" + field + "]" +
                    " Getting value occur exception, obj = " + obj, e);
        }
    }

    /**
     * 获取属性值
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || Strings.isBlank(fieldName)) {
            return null;
        }
        return getFieldValue(obj, getField(obj instanceof Class<?> ? (Class<?>) obj : obj.getClass(), fieldName));
    }

    /**
     * 获取Class定义的属性列表
     *
     * @param clazz 目标类型
     * @return 返回属性列表
     */
    public static List<Field> getFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        return FIELDS_CACHE.computeIfAbsent(clazz, Reflects::getFieldsDirectly);
    }

    /**
     * 根据过滤器过滤属性,最后返回类解析的属性列表
     *
     * @param targetClass 目标类型
     * @param fieldFilter 满足条件的过滤器
     * @return 过滤后的属性列表
     */
    public static List<Field> getFields(Class<?> targetClass, FieldFilter fieldFilter) {
        List<Field> fieldList = getFields(targetClass);
        if (Collections.isEmpty(fieldList) || fieldFilter == null) {
            return fieldList;
        }
        return fieldList.stream().filter(fieldFilter::accept).collect(Collectors.toList());
    }

    /**
     * 直接通过反射获取一个类的所有字段
     *
     * @param clazz 待处理的类
     * @return 字段列表
     */
    public static List<Field> getFieldsDirectly(Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz == null");
        List<Field> declaredFields = new ArrayList<>();
        Class<?> searchClass = clazz;
        while (searchClass != null) {
            Collections.addAll(declaredFields, searchClass.getDeclaredFields());
            searchClass = searchClass.getSuperclass();
        }
        return declaredFields.isEmpty() ? Collections.emptyList() : declaredFields;
    }

    /**
     * 根据{@code fieldName}在{@code targetClass}查找，支持父类的属性查找。
     *
     * @param clazz     被查找的类
     * @param fieldName 需要查找的字段
     * @return {@linkplain Field} or {@code null}
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        return findField(clazz, fieldName, null);
    }

    /**
     * 根据{@code fieldName}在{@code targetClass}查找，支持父类的属性查找。
     *
     * @param clazz     the class to introspect.
     * @param fieldName the name of the field.
     * @return the Field object, or {@code null} if not found.
     */
    public static Field findField(Class<?> clazz, String fieldName) {
        return findField(clazz, fieldName, null);
    }

    /**
     * 根据{@code fieldName}或{@code fieldType}在{@code targetClass}查找，支持父类的属性查找。
     *
     * @param clazz     the class to introspect.
     * @param fieldName the name of the field.
     * @param fieldType the type of the field.
     * @return the Field object, or {@code null} if not found.
     */
    public static Field findField(Class<?> clazz, String fieldName, Class<?> fieldType) {
        if (clazz == null || (Strings.isBlank(fieldName) && fieldType == null)) {
            return null;
        }
        return getFields(clazz).stream()
                .filter(element -> element.getName().equals(fieldName))
                .filter(element -> fieldType == null || fieldType.equals(element.getType()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 设置属性值
     *
     * @param obj   对象，如果是静态属性可以为空
     * @param field 字段
     * @param value 新的值
     * @throws ReflectiveOperationRuntimeException 反射异常
     */
    public static void setFieldValue(Object obj, Field field, Object value) throws ReflectiveOperationRuntimeException {
        if (field == null) {
            return;
        }
        Class<?> fieldType = field.getType();
        if (value != null) {
            if (!Classes.isAssignable(value.getClass(), fieldType)) {
                Object tv = ConvertUtil.convert(fieldType, value, null);
                if (tv != null) {
                    value = tv;
                }
            }
        } else {
            value = Classes.getDefaultValue(fieldType);
        }
        try {
            (setAccessible(field)).set(obj, value);
        } catch (IllegalAccessException e) {
            throw new ReflectiveOperationRuntimeException("The Field[" + field + "] setting value occur exception," +
                    "obj = " + obj + " , value = " + value, e);
        }
    }

    /**
     * 设置属性值
     *
     * @param obj       对象，如果是静态属性可以为空
     * @param filedName 字段名
     * @param value     新的值
     * @throws ReflectiveOperationRuntimeException 反射异常
     */
    public static void setFieldValue(Object obj, String filedName, Object value) throws ReflectiveOperationRuntimeException {
        if (obj == null || Strings.isBlank(filedName)) {
            return;
        }
        Field field = getField((obj instanceof Class<?>) ? (Class<?>) obj : obj.getClass(), filedName);
        setFieldValue(obj, field, value);
    }

}
