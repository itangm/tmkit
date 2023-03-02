package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link MonthDay}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class MonthDayDeserializer implements JsonDeserializer<MonthDay> {

    private final DateTimeFormatter formatter;

    public MonthDayDeserializer() {
        this(DateTimeFormatter.ofPattern("MM-dd"));
    }

    public MonthDayDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public MonthDayDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public MonthDay deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return MonthDay.parse(value, formatter);
            }

        }
        return null;
    }

    public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer();

}
