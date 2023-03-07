package cn.tmkit.json.sjf4j.jackson.ser;

import cn.tmkit.core.date.CustomFormatter;
import org.jetbrains.annotations.NotNull;

/**
 * Serializer for Java 8 OffsetDateTime
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
public class OffsetDateTimeSerializer extends com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer {

    public OffsetDateTimeSerializer(@NotNull CustomFormatter customFormatter) {
        super(INSTANCE, null, customFormatter.getFormatter());
    }

}
