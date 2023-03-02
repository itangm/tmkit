package cn.tmkit.json.sjf4j;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

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
    @Nullable
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

}
