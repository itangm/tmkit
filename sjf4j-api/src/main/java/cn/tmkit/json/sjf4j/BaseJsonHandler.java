package cn.tmkit.json.sjf4j;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 基础的 JSON Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public abstract class BaseJsonHandler implements JsonHandler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(@NotNull Object src, String... ignorePropertyNames) throws JsonRuntimeException {
        return doSerialize(src, ignorePropertyNames);
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    public abstract String doSerialize(@NotNull Object src, String[] ignorePropertyNames) throws JsonRuntimeException;

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(@NotNull Object src, @NotNull Type typeOfT) throws JsonRuntimeException {
        return doSerialize(src, typeOfT);
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    public abstract String doSerialize(@NotNull Object src, Type typeOfT);

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        return doDeserialize(json, typeOfT);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    public abstract <T> T doDeserialize(@NotNull String json, @NotNull Type typeOfT);

    /**
     * 将{@linkplain Reader}内容转为Java对象
     *
     * @param reader  内容
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化异常
     */
    @Override
    public <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException {
        throw new UnsupportedOperationException();
    }

    /**
     * 将JSON字符串转为集合
     *
     * @param json  字符串，可以为空
     * @param clazz 集合元素的类型
     * @return 集合对象
     */
    @Override
    public <T> List<T> deserializeList(String json, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    /**
     * 将JSON字符串反序列化为{@linkplain Map}对象
     *
     * @param json   字符串，可以为空
     * @param kClass {@linkplain Map}的键类型
     * @param vClass {@linkplain Map}的值类型
     * @return {@linkplain Map}对象
     */
    @Override
    public <K, V> Map<K, V> deserializeMap(String json, Class<K> kClass, Class<V> vClass) {
        throw new UnsupportedOperationException();
    }

}
