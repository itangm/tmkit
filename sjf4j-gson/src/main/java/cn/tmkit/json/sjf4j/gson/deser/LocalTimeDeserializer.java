package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.json.sjf4j.gson.ser.LocalTimeSerializer;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeDeserializer() {
        this(DefaultCustomFormatter.NORMAL_TIME);
    }

    public LocalTimeDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public LocalTimeDeserializer(@NotNull CustomFormatter formatter) {
        this(formatter.getFormatter());
    }

    @Override
    public LocalTime deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return LocalTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();

}
