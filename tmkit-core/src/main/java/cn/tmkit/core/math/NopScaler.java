package cn.tmkit.core.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 无任何标度处理的实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-08
 */
public class NopScaler implements Scaler {

    /**
     * 精度处理的方法
     *
     * @param value 待处理的值
     * @param mc    上下文
     * @return 处理后的值
     */
    @Override
    public @NotNull BigDecimal apply(@NotNull BigDecimal value, @NotNull MathContext mc) {
        return value;
    }

}
