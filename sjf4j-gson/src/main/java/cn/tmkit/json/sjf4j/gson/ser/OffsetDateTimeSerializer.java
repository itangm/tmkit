package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 OffsetDateTime
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class OffsetDateTimeSerializer implements JsonSerializer<OffsetDateTime> {

    private final DateTimeFormatter formatter;

    public OffsetDateTimeSerializer() {
        this(DefaultCustomFormatter.UTC_MS_WITH_ZONE_OFFSET);
    }

    public OffsetDateTimeSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public OffsetDateTimeSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(offsetDateTime.format(formatter));
    }

    public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();

}
