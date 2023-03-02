package cn.tmkit.json.sjf4j.jackson.deser;

import cn.tmkit.core.date.DefaultCustomFormatter;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class OffsetDateTimeDeserializer extends InstantDeserializer<OffsetDateTime> {

    public OffsetDateTimeDeserializer() {
        this(DefaultCustomFormatter.UTC_MS_WITH_ZONE_OFFSET.getFormatter());
    }

    public OffsetDateTimeDeserializer(DateTimeFormatter formatter) {
        super(InstantDeserializer.OFFSET_DATE_TIME, formatter);
    }

}
