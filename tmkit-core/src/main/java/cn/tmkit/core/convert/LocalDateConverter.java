package cn.tmkit.core.convert;

import cn.tmkit.core.date.LocalDateTimeUtil;
import cn.tmkit.core.exception.ConverterRuntimeException;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

import java.time.*;

/**
 * {@linkplain LocalDate} Converter
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class LocalDateConverter extends AbstractConverter<LocalDate> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected LocalDate handleInternal(@NotNull Object value) {
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime)value).toLocalDate();
        } else if (value instanceof ZonedDateTime) {
            return ((ZonedDateTime) value).toLocalDate();
        } else if (value instanceof OffsetDateTime) {
            return ((OffsetDateTime) value).toLocalDate();
        } else if (value instanceof LocalTime) {
            throw new ConverterRuntimeException("Can not Converter from [" + value.getClass() + "] to [" + getTargetClass() + "]");
        } else if (value instanceof String) {
            String str = execToStr(value);
            return LocalDateTimeUtil.parse(str).toLocalDate();
        }
        throw new ConverterRuntimeException("Can't cast " + value + " to java.time.LocalDate");
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<LocalDate> getTargetClass() {
        return LocalDate.class;
    }

    /**
     * 返回{@linkplain  LocalDateConverter}实例对象
     *
     * @return {@linkplain  LocalDateConverter}
     */
    public static LocalDateConverter getInstance() {
        return Singletons.get(LocalDateConverter.class);
    }

}
