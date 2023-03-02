package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.date.ZoneIdConstant;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeDeserializer() {
        this(DefaultCustomFormatter.NORMAL_DATETIME_FULL);
    }

    public LocalDateTimeDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public LocalDateTimeDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDateTime deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return LocalDateTime.parse(value, formatter);
            } else if (primitive.isNumber()) {
                return LocalDateTime.ofEpochSecond(primitive.getAsLong(), 0, ZoneIdConstant.BEIJING_ZONE_OFFSET);
            }
        }
        return null;
    }

    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

}
