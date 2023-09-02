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
     * 值的转换器
     *
     * @param key   属性key
     * @param value 属性的值
     * @return 转换后的值
     */
    Object convert(String key, Object value);
}
