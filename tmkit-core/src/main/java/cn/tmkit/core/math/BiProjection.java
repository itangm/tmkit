package cn.tmkit.core.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 函数式接口，接收三个参数，用于{@linkplain java.math.BigDecimal}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-08
 */

public interface BiProjection<Arg> {

    /**
     * 处理方法
     * @param value 第一个值
     * @param argument 第二个值
     * @param mc 上下文
     * @return 结果
     */
    @NotNull BigDecimal project(@NotNull BigDecimal value, @NotNull Arg argument, @NotNull MathContext mc);

}
