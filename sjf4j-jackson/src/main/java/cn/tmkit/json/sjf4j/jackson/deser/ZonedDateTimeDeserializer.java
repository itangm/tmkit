package cn.tmkit.json.sjf4j.jackson.deser;

import cn.tmkit.core.date.DefaultCustomFormatter;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class ZonedDateTimeDeserializer extends InstantDeserializer<ZonedDateTime> {

    public ZonedDateTimeDeserializer() {
        this(DefaultCustomFormatter.UTC_MS_WITH_ZONE_OFFSET.getFormatter());
    }

    public ZonedDateTimeDeserializer(DateTimeFormatter formatter) {
        super(InstantDeserializer.ZONED_DATE_TIME, formatter);
    }

}
