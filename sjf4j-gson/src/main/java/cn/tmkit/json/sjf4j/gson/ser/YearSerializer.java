package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link Year}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class YearSerializer implements JsonSerializer<Year> {

    private final DateTimeFormatter formatter;

    public YearSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy"));
    }

    public YearSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public YearSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(Year year, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(year.format(formatter));
    }

    public static final YearSerializer INSTANCE = new YearSerializer();

}
