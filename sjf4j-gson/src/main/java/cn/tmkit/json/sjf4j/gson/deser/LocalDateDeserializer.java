package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateDeserializer() {
        this(DefaultCustomFormatter.NORMAL_DATE);
    }

    public LocalDateDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public LocalDateDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return LocalDate.parse(value, formatter);
            } else if (primitive.isNumber()) {
                return LocalDate.ofEpochDay(primitive.getAsLong());
            }
        }
        return null;
    }

    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

}
