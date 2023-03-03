package cn.tmkit.json.sjf4j.util;

import cn.tmkit.json.sjf4j.BaseTypeRef;
import cn.tmkit.json.sjf4j.JSON;
import cn.tmkit.json.sjf4j.JsonFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * JSON工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class JSONs {

    private static final JSON DEFAULT_JSON;

    static {
        DEFAULT_JSON = new JSON();
        DEFAULT_JSON.setJsonHandler(JsonFactory.defaultJsonHandler());
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src 对象
     * @return JSON字符串或空字符串
     * @see #toJson(Object, Type)
     */
    public static String toJson(Object src) {
        if (src == null) {
            return null;
        }
        return toJson(src, src.getClass());
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 对象的某个类型
     * @return JSON字符串或空字符串
     */
    public static String toJson(Object src, Type typeOfSrc) {
        if (src == null) {
            return null;
        }
        return DEFAULT_JSON.toJson(src, (typeOfSrc == null) ? src.getClass() : typeOfSrc);
    }

    /**
     * Java对象转为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性
     * @return JSON字符串
     */
    public static String toJson(Object src, String... ignorePropertyNames) {
        if (src == null) {
            return null;
        }
        return DEFAULT_JSON.toJson(src, ignorePropertyNames);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return DEFAULT_JSON.fromJson(json, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return DEFAULT_JSON.fromJson(json, typeOfT);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NotNull BaseTypeRef<T> typeRef) {
        return DEFAULT_JSON.fromJson(text, Objects.requireNonNull(typeRef).getType());
    }

}
