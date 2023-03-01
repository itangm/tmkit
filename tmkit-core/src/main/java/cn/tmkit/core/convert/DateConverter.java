package cn.tmkit.core.convert;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTime Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class DateConverter extends AbstractConverter< Date> {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2022L;

    /**
     * 目标类型
     */
    protected Class<? extends Date> targetClass;

    /**
     * 日期格式化
     */
    private String format;

    public DateConverter() {
        this.targetClass = Date.class;
    }

    public DateConverter(String format) {
        this();
        this.format = format;
    }

    public DateConverter(Class<? extends Date> targetClass) {
        this.targetClass = targetClass;
    }

    public DateConverter(Class<? extends Date> targetClass, String format) {
        this(targetClass);
        this.format = format;
    }

    public void setTargetClass(Class<? extends Date> targetClass) {
        this.targetClass = targetClass;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 内部转换器，被 {@link AbstractConverter#handleInternal(Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link RuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    @Override
    protected Date handleInternal(@NotNull Object value) {
        Long mills = null;
        if (value instanceof Calendar) {
            mills = ((Calendar) value).getTimeInMillis();
        } else if (value instanceof Number) {
            mills = ((Number) value).longValue();
        } else if (value instanceof TemporalAccessor) {
            //TODO 待实现
        } else {
//            String str = execToStr(value);
//            StringUtil.isEmpty(this.format)?
//                    Date
        }

        if (mills == null) {
            return null;
        }
        if (targetClass == Date.class) {
            return new Date(mills);
        } else if (targetClass == java.sql.Date.class) {
            return new java.sql.Date(mills);
        } else if (targetClass == java.sql.Time.class) {
            return new java.sql.Time(mills);
        } else if (targetClass == java.sql.Timestamp.class) {
            return new java.sql.Timestamp(mills);
        }
        throw new UnsupportedOperationException("Unsupported date type "+ targetClass.getName());
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<Date> getTargetClass() {
        return (Class<Date>) targetClass;
    }

}
