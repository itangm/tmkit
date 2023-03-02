package cn.tmkit.json.sjf4j.gson.ser;

import cn.tmkit.core.date.CustomFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link YearMonth}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class YearMonthSerializer implements JsonSerializer<YearMonth> {

    private final DateTimeFormatter formatter;

    public YearMonthSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public YearMonthSerializer(@NotNull CustomFormatter customFormatter) {
        this(customFormatter.getFormatter());
    }

    public YearMonthSerializer(@NotNull DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(YearMonth yearMonth, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(yearMonth.format(formatter));
    }

    public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

}
