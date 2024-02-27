package cn.tmkit.core.lang.reflect;

import cn.tmkit.core.convert.ConvertUtil;
import cn.tmkit.core.exception.ReflectiveOperationRuntimeException;
import cn.tmkit.core.filter.FieldFilter;
import cn.tmkit.core.lang.*;
import cn.tmkit.core.support.SimpleCache;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     * 是否为抽象方法
     *
     * @param method 方法
     * @return 是否为抽象类
     */
    public static boolean isAbstract(@NotNull Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(@NotNull Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
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
        Asserts.notNull(clazz);
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
        return (Constructor<T>[]) Asserts.notNull(clazz).getDeclaredConstructors();
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
        Asserts.notNull(method);
        setAccessible(method);
        try {
            return (T) method.invoke(isStatic(method) ? null : obj, params);
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new ReflectiveOperationRuntimeException("The Method [" + method + "] invoked occur exception obj = "
                    + obj + " , params = " + Arrays.toString(params), e);
        }
    }

    /**
     * 执行方法
     *
     * @param obj        对象，如果执行静态方法，此值为{@code null}
     * @param methodName 对象方法或静态方法的名称
     * @param params     方法参数，可以为空
     * @param <T>        返回的泛型限定类型
     * @return 结果
     */
    public static <T> T invoke(Object obj, String methodName, Object... params) {
        Asserts.notNull(obj);
        Asserts.notEmpty(methodName);
        Method method = getMethod(obj, methodName, params);
        if (null == method) {
            throw new ReflectiveOperationRuntimeException("No such method: [" + methodName + "] from [" + obj.getClass() + "]");
        }
        return invoke(obj, method, params);
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
        Asserts.notNull(clazz, "clazz == null");
        List<Field> declaredFields = new ArrayList<>();
        Class<?> searchClass = clazz;
        while (searchClass != null) {
            Collections.addAll(declaredFields, searchClass.getDeclaredFields());
            searchClass = searchClass.getSuperclass();
        }
        return declaredFields.isEmpty() ? Collections.emptyList() : declaredFields;
    }

    /**
     * 判断这个类是否存在这个字段名（含父类的）
     *
     * @param beanClass 被查找字段的类,不能为null
     * @param name      字段名
     * @return 是否包含字段
     * @throws SecurityException 安全异常
     */
    public static boolean hasField(Class<?> beanClass, String name) throws SecurityException {
        return null != getField(beanClass, name);
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

    /**
     * 查找方法名和参数数量、参数类型匹配的{@linkplain Method}
     *
     * @param obj        被查找的对象，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param params     参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getMethod(Object obj, String methodName, Object... params) throws SecurityException {
        if (null == obj || Strings.isBlank(methodName)) {
            return null;
        }
        return getMethod(obj.getClass(), methodName, ClassUtil.getClasses(params));
    }

    /**
     * 查找方法名和参数数量、参数类型匹配的{@linkplain Method}
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(clazz, false, methodName, paramTypes);
    }

    /**
     * 查找指定方法 如果找不到对应的方法则返回{@code null}<br>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。<br>
     * 如果查找的方法有多个同参数类型重载，查找第一个找到的方法
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param ignoreCase 是否忽略大小写
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, boolean ignoreCase, String methodName, Class<?>... paramTypes) throws SecurityException {
        if (null == clazz || StrUtil.isBlank(methodName)) {
            return null;
        }

        Method res = null;
        final Method[] methods = getMethods(clazz);
        if (ArrayUtil.isNotEmpty(methods)) {
            for (Method method : methods) {
                if (StrUtil.equals(methodName, method.getName(), ignoreCase)
                        && ClassUtil.isAllAssignable(method.getParameterTypes(), paramTypes)
                        && (res == null || res.getReturnType().isAssignableFrom(method.getReturnType()))) {
                    res = method;
                }
            }
        }
        return res;
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param clazz 类
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethods(@NotNull Class<?> clazz) throws SecurityException {
        return METHODS_CACHE.computeIfAbsent(clazz, () -> getDeclaredMethodsDirectly(clazz, true, false));
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存<br>
     * 接口获取方法和默认方法，获取的方法包括：
     * <ul>
     *     <li>本类中的所有方法（包括static方法）</li>
     *     <li>父类中的所有方法（包括static方法）</li>
     *     <li>Object中（包括static方法）</li>
     * </ul>
     *
     * @param clazz             类或接口
     * @param includeSupers     是否包括父类或接口的方法列表
     * @param includeFromObject 是否包括Object中的方法
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getDeclaredMethodsDirectly(@NotNull Class<?> clazz, boolean includeSupers,
                                                      boolean includeFromObject) throws SecurityException {
        if (clazz.isInterface()) {
            // 对于接口，直接调用Class.getMethods方法获取所有方法，因为接口都是public方法
            return includeSupers ? clazz.getMethods() : clazz.getDeclaredMethods();
        }

        final Map<String, Method> result = Maps.hashMap(Maps.DEFAULT_INITIAL_CAPACITY);
        Class<?> searchType = clazz;
        while (searchType != null) {
            if (!includeFromObject && Object.class == searchType) {
                break;
            }
            for (Method declaredMethod : searchType.getDeclaredMethods()) {
                result.put(generateKey(declaredMethod), declaredMethod);
            }
            findDefaultMethodsOnInterfaces(searchType).forEach(declaredMethod -> result.put(generateKey(declaredMethod), declaredMethod));

            searchType = (includeSupers && !searchType.isInterface()) ? searchType.getSuperclass() : null;
        }

        return result.values().toArray(EMPTY_METHOD_ARRAY);
    }

    /**
     * 生成方法的唯一键，格式为：返回类型#方法名:参数类型1,参数类型2[...]
     *
     * @param method 方法
     * @return 方法的唯一键
     */
    @SuppressWarnings("unchecked")
    private static String generateKey(Method method) {
        return Strings.format("{}#{}{}", () -> method.getReturnType().getName(),
                method::getName,
                () -> {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    return parameterTypes.length == 0 ? Strings.EMPTY_STRING :
                            Arrays.stream(parameterTypes).map(Class::getName)
                                    .collect(Collectors.joining(Strings.COMMA, Strings.COLON, Strings.EMPTY_STRING));
                });
    }

    private static List<Method> findDefaultMethodsOnInterfaces(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        List<Method> result = Collections.arrayList(interfaces.length);
        for (Class<?> ifc : interfaces) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!isAbstract(ifcMethod)) {
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

    private static final SimpleCache<Class<?>, Method[]> METHODS_CACHE = new SimpleCache<>(256);


    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

}
