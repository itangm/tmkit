package cn.tmkit.core.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 固定标度处理的实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-08
 */
@Getter
@AllArgsConstructor
public class FixedScaler implements Scaler {

    /**
     * 固定精度
     */
    private final int scale;

    /**
     * 精度处理的方法
     *
     * @param value 待处理的值
     * @param mc    上下文
     * @return 处理后的值
     */
    @Override
    public @NotNull BigDecimal apply(@NotNull BigDecimal value, @NotNull MathContext mc) {
        return value.setScale(scale, mc.getRoundingMode());
    }

}
