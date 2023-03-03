package cn.tmkit.core.bean;

import cn.tmkit.core.exception.BeanException;
import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.*;
import cn.tmkit.core.lang.reflect.Reflects;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Java Bean工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class Beans {

    /**
     * 判断是不是普通的JavaBean，判断方法为：
     * <pre>
     *     1. 判断属性是不是有getter/setter方法
     *     2. 判断字段是不是public修饰符
     * </pre>
     *
     * @param clazz 待检测的类
     * @return {@code true} or {@code false}
     */
    public static boolean isBeanType(Class<?> clazz) {
        if (!Classes.isNormalClass(clazz)) {
            return false;
        }

        // 判断你是不是有public修饰符
        boolean result = Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(field -> Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()));
        if (result) {
            return true;
        }

        Method[] methods = clazz.getDeclaredMethods();
        // 是不是有setter
        result = Arrays.stream(methods).anyMatch(method -> method.getName().startsWith("set"));
        if (result) {
            // 必须同时还要有getter/is
            return Arrays.stream(methods).anyMatch(method ->
                    method.getName().startsWith("get") || method.getName().startsWith("is"));
        }

        return false;
    }

    /**
     * 获得Bean字段描述集合,获得的结果会缓存在{@link BeanIntrospectCache}中
     *
     * @param beanClass Bean类
     * @return 字段描述数组
     * @throws BeanException 获取属性异常
     */
    public static List<PropertyDescriptor> getPropertyDescriptorList(Class<?> beanClass) throws BeanException {
        Asserts.notNull(beanClass, "beanClass == null");
        return BeanIntrospectCache.getInstance().getPropertyDescriptors(beanClass);
    }

    /**
     * 获得字段名和字段描述Map,获得的结果会缓存在{@link BeanIntrospectCache}中
     *
     * @param beanClass Bean类
     * @return 字段名和字段描述Map
     * @throws BeanException 获取属性异常
     */
    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> beanClass) throws BeanException {
        List<PropertyDescriptor> list = getPropertyDescriptorList(beanClass);
        final Map<String, PropertyDescriptor> map = new HashMap<>(list.size());
        for (PropertyDescriptor propertyDescriptor : list) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }

    /**
     * 根据属性名称获取对应属性对象
     *
     * @param beanClass    类型
     * @param propertyName 属性名
     * @return {@linkplain PropertyDescriptor}
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
        if (Strings.isBlank(propertyName)) {
            return null;
        }
        return getPropertyDescriptorMap(beanClass).get(propertyName);
    }

    /**
     * 获取属性值,通过{@linkplain PropertyDescriptor}获取，必须要求要求实现{@code getter}方法
     *
     * @param bean         bean实例
     * @param propertyName 属性名
     * @param <T>          属性值类型
     * @return 属性值, 如果属性找不到或未实现{@code getter}方法返回{@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object bean, String propertyName) {
        Asserts.notNull(bean, "bean == null");
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            return null;
        }
        try {
            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                return null;
            }
            return (T) readMethod.invoke(bean);
        } catch (ReflectiveOperationException e) {
            throw new BeanException(e);
        }
    }

    /**
     * 对象转Map,不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false);
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, boolean ignoreNullValue) {
        Map<String, Object> targetMap = new LinkedHashMap<>();
        beanToMap(bean, targetMap, ignoreNullValue);
        return targetMap;
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param targetMap       目标的Map
     * @param ignoreNullValue 是否忽略值为空的字段
     */
    public static void beanToMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullValue) {
        if (bean == null || targetMap == null) {
            return;
        }
        Method readMethod;
        Object value;
        for (PropertyDescriptor propertyDescriptor : getPropertyDescriptorList(bean.getClass())) {
            readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                try {
                    value = readMethod.invoke(bean);
                    if (ignoreNullValue && value != null) {
                        targetMap.put(propertyDescriptor.getName(), value);
                    } else {
                        targetMap.put(propertyDescriptor.getName(), value);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new BeanException(e);
                }
            }
        }

    }

    /**
     * Java Bean对象转Map
     *
     * @param bean       bean对象
     * @param targetMap  目标的Map
     * @param copyOption 复制配置
     */
    private static void beanToMap(Object bean, Map<String, Object> targetMap, CopyOption copyOption) {
        if (bean == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        final Map<String, PropertyDescriptor> propertyDescriptorMap = getPropertyDescriptorMap(bean.getClass());
        propertyDescriptorMap.forEach((key, pd) -> {
            Object value = Reflects.invoke(bean, pd.getReadMethod());
            copyToMap(key, value, targetMap, finalCo);
        });
    }

    /**
     * Map Copy To Map
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 拷贝的配置
     */
    public static void mapToMap(Map<String, Object> source, Map<String, Object> target, CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        source.forEach((key, value) -> {
            copyToMap(key, value, target, finalCo);
        });
    }

    /**
     * Map对象转为Java Bean对象
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 拷贝配置
     */
    public static void mapToBean(Map<String, Object> source, Object target, CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        Map<String, PropertyDescriptor> propertyDescriptorMap = getPropertyDescriptorMap(target.getClass());
        source.forEach((key, value) -> {
            if (finalCo.getIgnoreProperties().contains(key)) {
                return;
            }
            PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(key);
            if (propertyDescriptor == null) {
                return;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod == null) {
                return;
            }
            if (value == null) {
                if (!finalCo.isIgnoreNullValue()) {
                    Reflects.invoke(target, writeMethod, new Object[]{null});
                }
            } else {
                if (value instanceof String) {
                    String str = (String) value;
                    if (Strings.hasLength(str) || !finalCo.isIgnoreEmptyString()) {
                        Reflects.invoke(target, writeMethod, value);
                    }
                } else {
                    Reflects.invoke(target, writeMethod, value);
                }
            }
        });
    }

    /**
     * 将Javabean对象转为Map,其中值的类型为{@code String}
     *
     * @param bean 对象
     * @return Map对象
     */
    public static Map<String, String> toMapAsValueString(Object bean) {
        return toMapAsValueString(bean, true);
    }

    /**
     * 将Javabean对象转为Map,其中值的类型为{@code String}
     *
     * @param bean       对象
     * @param ignoreNull 是否忽略空值
     * @return Map对象
     */
    public static Map<String, String> toMapAsValueString(Object bean, boolean ignoreNull) {
        Map<String, Object> propertiesMap = beanToMap(bean);
        Map<String, String> resultMap = new HashMap<>(propertiesMap.size());

        Object val;
        for (String propertyName : propertiesMap.keySet()) {
            val = propertiesMap.get(propertyName);
            if (val == null) {
                if (!ignoreNull) {
                    resultMap.put(propertyName, null);
                }
            } else {
                if (val instanceof Number) {
                    Number number = (Number) val;
                    resultMap.put(propertyName, number.toString());
                } else if (val instanceof String) {
                    resultMap.put(propertyName, (String) val);
                } else if (val instanceof Date) {
                    resultMap.put(propertyName, String.valueOf(((Date) val).getTime()));
                } else {
                    resultMap.put(propertyName, val.toString());
                }
            }
        }
        return resultMap;
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, (String) null);
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source           原始对象
     * @param target           目标对象
     * @param ignoreProperties 过滤的属性名
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        copyProperties(source, target, new CopyOption(ignoreProperties));
    }

    /**
     * 对象复制，如果值为{@code null}时如果设置{@code ignoreNullValue}为{@code true}则不复制
     *
     * @param source          源对象
     * @param target          目标对象
     * @param ignoreNullValue 是否忽略null值
     */
    public static void copyProperties(Object source, Object target, boolean ignoreNullValue) {
        copyProperties(source, target, new CopyOption(ignoreNullValue));
    }

    /**
     * 对象复制
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 复制配置
     */
    @SuppressWarnings("unchecked")
    public static void copyProperties(Object source, Object target, CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        if (source instanceof Map) {
            if (target instanceof Map) {
                mapToMap((Map<String, Object>) source, (Map<String, Object>) target, copyOption);
            } else {
                mapToBean((Map<String, Object>) source, target, copyOption);
            }
        } else {
            if (target instanceof Map) {
                beanToMap(source, (Map<String, Object>) target, copyOption);
            } else {
                final CopyOption finalCo = copyOption;
                final Map<String, PropertyDescriptor> targetPdMap = getPropertyDescriptorMap(target.getClass());
                getPropertyDescriptorMap(source.getClass()).forEach((key, sourcePd) -> {
                    if (finalCo.getIgnoreProperties().contains(key)) {
                        return;
                    }
                    PropertyDescriptor targetPd = targetPdMap.get(key);
                    if (targetPd == null || targetPd.getWriteMethod() == null) {
                        return;
                    }
                    Object value = Reflects.invoke(source, sourcePd.getReadMethod());
                    if (value == null) {
                        if (!finalCo.isIgnoreNullValue()) {
                            Reflects.invoke(target, targetPd.getWriteMethod(), new Object[]{null});
                        }
                    } else {
                        if (value instanceof String) {
                            String str = (String) value;
                            if (Strings.hasLength(str) || !finalCo.isIgnoreEmptyString()) {
                                Reflects.invoke(target, targetPd.getWriteMethod(), value);
                            }
                        } else {
                            Reflects.invoke(target, targetPd.getWriteMethod(), value);
                        }
                    }
                });
            }
        }
    }

    /**
     * 拷贝集合中的对象
     *
     * @param src         源集合
     * @param targetClass 模板bean类型
     * @param <E>泛型标记
     * @return 目标集合
     */
    @Nullable
    public static <E> List<E> copyProperties(@Nullable Collection<?> src, Class<E> targetClass) {
        return copyProperties(src, targetClass, Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 拷贝集合中的对象
     *
     * @param src         源集合
     * @param targetClass 模板bean类型
     * @param <E>泛型标记
     * @return 目标集合
     */
    @SuppressWarnings("all")
    @Nullable
    public static <E> List<E> copyProperties(@Nullable Collection<?> src, Class<E> targetClass,
                                             @Nullable String... ignoreProperties) {
        if (Objects.isAnyNull(src, targetClass)) {
            return null;
        }
        List<E> list = Collections.arrayList();
        for (Object obj : src) {
            list.add(copyProperties(obj, targetClass, ignoreProperties));
        }
        return list;
    }

    /**
     * 拷贝对象
     *
     * @param src              源bean对象
     * @param targetClass      目标bean类型
     * @param ignoreProperties 忽略的属性
     * @param <E>              泛型标记
     * @return 目标bean对象
     */
    @Nullable
    public static <E> E copyProperties(@Nullable Object src, Class<E> targetClass,
                                       @Nullable String... ignoreProperties) {
        if (Objects.isAnyNull(src, targetClass)) {
            return null;
        }
        E target = Reflects.newInstance(targetClass);
        Beans.copyProperties(src, target, ignoreProperties);
        return target;
    }

    /**
     * 将属性和其值复制到目标对象中
     *
     * @param key        属性名
     * @param value      属性对应的值
     * @param targetMap  目标Map
     * @param copyOption 复制选项
     */
    private static void copyToMap(String key, Object value, Map<String, Object> targetMap, CopyOption copyOption) {
        if (copyOption.getIgnoreProperties().contains(key)) {
            return;
        }
        if (value == null) {
            if (!copyOption.isIgnoreNullValue()) {
                targetMap.put(key, null);
            }
        } else {
            if (value instanceof String) {
                String str = (String) value;
                if (Strings.hasLength(str) || !copyOption.isIgnoreEmptyString()) {
                    targetMap.put(key, value);
                }
            } else {
                targetMap.put(key, value);
            }
        }
    }

}
