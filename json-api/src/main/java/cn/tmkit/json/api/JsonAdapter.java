package cn.tmkit.json.api;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.reflect.Classes;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * JSON Adapter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Slf4j
public class JsonAdapter {

    private JsonHandler handler;

    public String toJson(Object src) {
        return toJson(src, Classes.getClass(src));
    }

    public String toJson(@Nullable Object src, @Nullable Type typeOfSrc) {
        if (src == null) {
            return null;
        }
        return handler.serialize(src, typeOfSrc);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T fromJson(@Nullable String json, Class<T> clazz) {
        Object obj = fromJson(json, (Type) clazz);
        return (T) obj;
    }

    public <T> T fromJson(String json, Type typeOfT) {
        if (json == null) {
            return null;
        }
        return handler.deserialize(json, typeOfT);
    }

    public <T> T fromJson(Reader reader, Type typeOfT) {
        if (reader == null) {
            return null;
        }
        return handler.deserialize(reader, typeOfT);
    }

    public JsonAdapter setHandler(JsonHandler handler) {
        this.handler = handler;
        return this;
    }

}
