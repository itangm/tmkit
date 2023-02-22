package cn.tmkit.core.math;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 标度处理定义接口。标度可以理解为小数点有效位数（不完全相等）
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-08
 */
@FunctionalInterface
public interface Scaler extends Serializable {

    /**
     * 标度处理的方法
     *
     * @param value 待处理的值
     * @param mc    上下文
     * @return 处理后的值
     */
    @NotNull BigDecimal apply(@NotNull BigDecimal value, @NotNull MathContext mc);

}
