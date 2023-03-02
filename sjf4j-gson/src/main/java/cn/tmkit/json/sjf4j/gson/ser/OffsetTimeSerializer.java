package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class OffsetTimeSerializer implements JsonSerializer<OffsetTime> {

    private final DateTimeFormatter formatter;

    public OffsetTimeSerializer() {
        this(DefaultCustomFormatter.NORMAL_TIME);
    }

    public OffsetTimeSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public OffsetTimeSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(OffsetTime offsetTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(offsetTime.format(formatter));
    }

    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();

}
