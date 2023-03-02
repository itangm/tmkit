package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
        this(DefaultCustomFormatter.NORMAL_DATETIME_FULL);
    }

    public LocalDateTimeSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public LocalDateTimeSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.format(formatter));
    }

    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

}
