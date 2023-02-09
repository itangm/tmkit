package cn.tmkit.core.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 最大标度处理的实现
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-08
 */
@Getter
@AllArgsConstructor
public class MaxScaler implements Scaler {

    /**
     * 最大精度
     */
    private final int maxScale;

    /**
     * 精度处理的方法
     *
     * @param value 待处理的值
     * @param mc    上下文
     * @return 处理后的值
     */
    @Override
    public @NotNull BigDecimal apply(@NotNull BigDecimal value, @NotNull MathContext mc) {
        // https://www.cnblogs.com/noteless/p/9896139.html#
        // 求小数点左侧数字的长度
        int maxIntegerPrecision = mc.getPrecision() - getMaxScale();
        // 传入的值的数字长度
        int intPrecision = value.precision() - value.scale();
        if (intPrecision > maxIntegerPrecision) {
            throw new ArithmeticException(String.format("Cannot fit computed outcome %s into precision %s while still allowing for %s decimals.",
                    value.toPlainString(), mc.getPrecision(), getMaxScale()));
        }
        if (value.scale() <= getMaxScale()) {
            return value;
        }
        return value.setScale(maxScale, mc.getRoundingMode());
    }

}
