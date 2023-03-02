package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link YearMonth}s.
 *
 * @author Miles.Tang
 */
public class YearMonthDeserializer implements JsonDeserializer<YearMonth> {

    private final DateTimeFormatter formatter;

    public YearMonthDeserializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public YearMonthDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public YearMonthDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public YearMonth deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return YearMonth.parse(value, formatter);
            }
        }
        return null;
    }

    public static final YearMonthDeserializer INSTANCE = new YearMonthDeserializer();

}
