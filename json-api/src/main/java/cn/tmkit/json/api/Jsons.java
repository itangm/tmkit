package cn.tmkit.json.api;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.Objects;

import java.lang.reflect.Type;

/**
 * 提供默认的工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public class Jsons {

    /**
     * 将对象转为JSON字符串
     *
     * @param src 对象
     * @return JSON字符串或空字符串
     * @see #toJson(Object, Type)
     */
    @Nullable
    public static String toJson(@Nullable Object src) {
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
    @Nullable
    public static String toJson(@Nullable Object src, @Nullable Type typeOfSrc) {
        if (src == null) {
            return null;
        }
        return toJson(src, typeOfSrc, null);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src    对象
     * @param custom 指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(@Nullable Object src, @Nullable JsonHandler custom) {
        if (src == null) {
            return null;
        }
        return toJson(src, src.getClass(), custom);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 类型
     * @param custom    指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(@Nullable Object src, @Nullable Type typeOfSrc, @Nullable JsonHandler custom) {
        if (src == null) {
            return null;
        }
        return getJsonHandler(custom).serialize(src, typeOfSrc);
    }

    /**
     * Java对象转为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性
     * @return JSON字符串
     */
    @Nullable
    public static String toJson(Object src, String... ignorePropertyNames) {
        if (src == null) {
            return null;
        }
        return getJsonHandler(null).serialize(src, ignorePropertyNames);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(@Nullable String json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(@Nullable String json, Type typeOfT) {
        return fromJson(json, typeOfT, null);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param custom 字符串，可以为空
     * @param clazz  类型
     * @param <T>    泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Class<T> clazz, @Nullable JsonHandler custom) {
        return getJsonHandler(custom).deserialize(text, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param custom  字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Type typeOfT, @Nullable JsonHandler custom) {
        return getJsonHandler(custom).deserialize(text, typeOfT);
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
        return getJsonHandler(null).deserialize(text, Objects.requireNonNull(typeRef).getType());
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param custom  指定JSON引擎
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NotNull BaseTypeRef<T> typeRef, @Nullable JsonHandler custom) {
        return getJsonHandler(custom).deserialize(text, Objects.requireNonNull(typeRef).getType());
    }

    /**
     * 返回JSON引擎
     *
     * @param json 指定一个JSON引擎，可以为{@code null}
     * @return JSON引擎
     */
    private static JsonHandler getJsonHandler(JsonHandler json) {
        JsonHandler result = json != null ? json : JsonFactory.defaultJsonHandler();
        return Objects.requireNonNull(result, "JsonHandler Provider Cannot find!");
    }

}
