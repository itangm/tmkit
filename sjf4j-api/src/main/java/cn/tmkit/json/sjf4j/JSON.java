package cn.tmkit.json.sjf4j;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 提供默认的工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class JSON {

    /**
     * JSON处理器，真正处理JSON的引擎
     */
    private JsonHandler jsonHandler;

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 对象的某个类型
     * @return JSON字符串或空字符串
     */
    public String toJson(@NotNull Object src, @NotNull Type typeOfSrc) {
        return jsonHandler.serialize(src, typeOfSrc);
    }

    /**
     * Java对象转为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性
     * @return JSON字符串
     */
    public String toJson(Object src, String... ignorePropertyNames) {
        return jsonHandler.serialize(src, ignorePropertyNames);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public <T> T fromJson(String json, Type typeOfT) {
        return (json == null) ? null : jsonHandler.deserialize(json, typeOfT);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可为空
     * @param typeRef 类型
     * @param <T>     泛型
     * @return 对象
     */
    public <T> T fromJson(String json, @NotNull BaseTypeRef<T> typeRef) {
        return (json == null) ? null : jsonHandler.deserialize(json, typeRef.getType());
    }

    /**
     * 设置{@linkplain JsonHandler}
     *
     * @param jsonHandler jsonHandler
     * @return jsonHandler
     */
    public JSON setJsonHandler(JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
        return this;
    }

    /**
     * 返回{@linkplain JsonHandler}
     *
     * @return jsonHandler
     */
    public JsonHandler getJsonHandler() {
        return jsonHandler;
    }

    /**
     * 将JSON字符串转为集合
     *
     * @param json  字符串，可以为空
     * @param clazz 集合元素的类型
     * @param <T>   泛型限定
     * @return 集合对象
     */
    public <T> List<T> toList(String json, Class<T> clazz) {
        return json == null ? null : jsonHandler.deserializeList(json, clazz);
    }

    /**
     * 将JSO你字符串反序列化为{@linkplain Map}对象
     *
     * @param json   字符串，可以为空
     * @param kClass {@linkplain Map}的键类型
     * @param vClass {@linkplain Map}的值类型
     * @param <K>    键类型的泛型
     * @param <V>    值类型的泛型
     * @return {@linkplain Map}对象
     */
    public <V, K> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        return json == null ? null : jsonHandler.deserializeMap(json, kClass, vClass);
    }

}
