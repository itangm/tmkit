package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link Instant}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class InstantSerializer implements JsonSerializer<Instant> {

    private final DateTimeFormatter formatter;

    public InstantSerializer() {
        this(DateTimeFormatter.ISO_INSTANT);
    }

    public InstantSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public InstantSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(instant));
    }

    public static final InstantSerializer INSTANCE = new InstantSerializer();

}
