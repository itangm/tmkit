package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.date.ZoneIdConstant;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link ZonedDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

    private final DateTimeFormatter formatter;

    public ZonedDateTimeSerializer() {
        this(DefaultCustomFormatter.NORMAL_DATETIME_FULL);
    }

    public ZonedDateTimeSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public ZonedDateTimeSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
        if (this.formatter.getZone() == null) {
            this.formatter.withZone(ZoneIdConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (zonedDateTime == null) {
            return null;
        }
        return new JsonPrimitive(zonedDateTime.format(formatter));
    }

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

}
