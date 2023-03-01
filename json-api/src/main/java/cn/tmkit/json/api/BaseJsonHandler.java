package cn.tmkit.json.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * 基础的 JSON Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-24
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
    public String serialize(@Nullable Object src, @Nullable String... ignorePropertyNames) throws JsonRuntimeException {
        if (Objects.isNull(src)) {
            return null;
        }
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
    public String serialize(@Nullable Object src, @Nullable Type typeOfT) throws JsonRuntimeException {
        if (Objects.isNull(src)) {
            return null;
        }
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
    public abstract String doSerialize(@NotNull Object src, @Nullable Type typeOfT);

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(@Nullable String json, @Nullable Type typeOfT) throws JsonRuntimeException {
        if (Strings.isBlank(json)) {
            return null;
        }
        Objects.requireNonNull(typeOfT, "The parameter [typeOfT] is null");
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
    public <T> T deserialize(@Nullable Reader reader, @Nullable Type typeOfT) throws JsonRuntimeException {
        throw new UnsupportedOperationException();
    }

}
