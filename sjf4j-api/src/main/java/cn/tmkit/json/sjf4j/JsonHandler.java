package cn.tmkit.json.sjf4j;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * JSON Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public interface JsonHandler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    String serialize(@NotNull Object src, String... ignorePropertyNames) throws JsonRuntimeException;

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    String serialize(@NotNull Object src, @NotNull Type typeOfT) throws JsonRuntimeException;

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @param <T>     泛型类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    <T> T deserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException;

    /**
     * 将{@linkplain Reader}内容转为Java对象
     *
     * @param reader  内容
     * @param typeOfT Java类型
     * @param <T>     泛型类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化异常
     */
    <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException;

    /**
     * 将JSON字符串转为集合
     *
     * @param json  字符串，可以为空
     * @param clazz 集合元素的类型
     * @param <T>   泛型限定
     * @return 集合对象
     */
    <T> List<T> deserializeList(String json, Class<T> clazz);

    /**
     * 将JSON字符串反序列化为{@linkplain Map}对象
     *
     * @param json   字符串，可以为空
     * @param kClass {@linkplain Map}的键类型
     * @param vClass {@linkplain Map}的值类型
     * @param <K>    键类型的泛型
     * @param <V>    值类型的泛型
     * @return {@linkplain Map}对象
     */
    <K, V> Map<K, V> deserializeMap(String json, Class<K> kClass, Class<V> vClass);

}
