package cn.tmkit.core.convert;

import cn.tmkit.core.date.LocalDateTimeUtil;
import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

import java.time.*;

/**
 * {@linkplain LocalDateTime} Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class LocalDateTimeConverter extends AbstractConverter<LocalDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected LocalDateTime handleInternal(@NotNull Object value) {
        if (value instanceof LocalDate) {
            return ((LocalDate) value).atStartOfDay();
        } else if (value instanceof ZonedDateTime) {
            return ((ZonedDateTime) value).toLocalDateTime();
        } else if (value instanceof OffsetDateTime) {
            return ((OffsetDateTime) value).toLocalDateTime();
        } else if (value instanceof LocalTime) {
            return LocalDateTime.of(LocalDate.now(), (LocalTime) value);
        } else if (value instanceof String) {
            String str = execToStr(value);
            return LocalDateTimeUtil.parse(str);
        }
        throw new ConverterRuntimeException("Can't cast " + value + " to java.time.LocalDateTime");
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<LocalDateTime> getTargetClass() {
        return LocalDateTime.class;
    }

    /**
     * 返回{@linkplain  LocalDateTimeConverter}实例对象
     *
     * @return {@linkplain  LocalDateTimeConverter}
     */
    public static LocalDateTimeConverter getInstance() {
        return Singletons.get(LocalDateTimeConverter.class);
    }

}
