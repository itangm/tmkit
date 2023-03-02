package cn.tmkit.json.sjf4j.gson.deser;

import cn.tmkit.core.date.CustomFormatter;
import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.date.ZoneIdConstant;
import cn.tmkit.core.lang.Strings;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link OffsetDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class OffsetDateTimeDeserializer implements JsonDeserializer<OffsetDateTime> {

    private DateTimeFormatter formatter;

    public OffsetDateTimeDeserializer() {
        this(DefaultCustomFormatter.UTC_MS_WITH_ZONE_OFFSET);
    }

    public OffsetDateTimeDeserializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public OffsetDateTimeDeserializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
        if (this.formatter.getZone() == null) {
            this.formatter = this.formatter.withZone(ZoneIdConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public OffsetDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (Strings.isEmpty(value)) {
                    return null;
                }
                return OffsetDateTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final OffsetDateTimeDeserializer INSTANCE = new OffsetDateTimeDeserializer();

}
