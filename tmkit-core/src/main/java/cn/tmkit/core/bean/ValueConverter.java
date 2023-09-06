package cn.tmkit.core.bean;

/**
 * 值转换器，在属性复制时是否需要进行值的自定义转换
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-09-02
 */
public interface ValueConverter {

    /**
     * 是否匹配属性名或键名
     *
     * @param key 键名或属性名
     * @return 匹配执行转换
     */
    default boolean matches(String key) {
        return false;
    }

    /**
     * 值的转换器
     *
     * @param value       属性的值
     * @param targetClass 目标类型
     * @return 转换后的值
     */
    default Object convert(Object value, Class<?> targetClass) {
        return value;
    }

}
