package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * BigDecimal Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class BigDecimalConverter extends AbstractConverter<BigDecimal> {

    private static final long serialVersionUID = 2023L;

    @Override
    protected BigDecimal handleInternal(@NotNull Object value) {
        if (value instanceof Number) {
            return Numbers.createBigDecimal((Number) value);
        } else if (value instanceof Boolean) {
            return BigDecimal.valueOf(((Boolean) value) ? 1 : 0);
        }
        return Numbers.createBigDecimal(execToStr(value));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<BigDecimal> getTargetClass() {
        return BigDecimal.class;
    }

    /**
     * 返回{@linkplain  BigDecimalConverter}实例对象
     *
     * @return {@linkplain  BigDecimalConverter}
     */
    public static BigDecimalConverter getInstance() {
        return Singletons.get(BigDecimalConverter.class);
    }

}
