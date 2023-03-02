package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.ZoneIdConstant;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link ZonedDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

    private DateTimeFormatter formatter;

    public ZonedDateTimeDeserializer() {
        this(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public ZonedDateTimeDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public ZonedDateTimeDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
        if (this.formatter.getZone() == null) {
            this.formatter = this.formatter.withZone(ZoneIdConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public ZonedDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            try {
                if (primitive.isString()) {
                    String value = primitive.getAsString();
                    if (Strings.isEmpty(value)) {
                        return null;
                    }
                    // '2011-12-03T10:15:30+01:00[Asia/Shanghai]'
                    return ZonedDateTime.parse(value, this.formatter);
                } else if (primitive.isNumber()) {
                    // 认为是毫秒
                    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(primitive.getAsLong()), formatter.getZone());
                }
            } catch (RuntimeException e) {
                throw new JsonParseException("Unable to parse ZonedDateTime", e);
            }
        }
        return null;
    }

    public static final ZonedDateTimeDeserializer INSTANCE = new ZonedDateTimeDeserializer();

}
