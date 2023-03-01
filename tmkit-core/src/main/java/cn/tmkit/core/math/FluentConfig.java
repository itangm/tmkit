package cn.tmkit.core.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.MathContext;

import static java.math.RoundingMode.HALF_UP;

/**
 * 流式的{@linkplain FluentBigDecimal}配置
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-07
 */
@Getter
@AllArgsConstructor
@ToString
public class FluentConfig {

    /**
     * the mathContext of this BigDecimal
     */
    private final MathContext mathContext;

    /**
     * 标度处理
     */
    private final Scaler scaler;

    /**
     * 默认的配置
     */
    public static FluentConfig DEFAULT = new FluentConfig(MathContext.UNLIMITED, new NopScaler());

    /**
     * 默认的金额配置，2位有效小数，超出四舍五入。数字总个数位20
     */
    public static FluentConfig MONEY = monetary(20);

    /**
     * 自定义金额配置
     *
     * @param precision 精度，数字总个数
     * @return {@linkplain FluentConfig}
     */
    public static FluentConfig monetary(int precision) {
        return new FluentConfig(new MathContext(precision, HALF_UP), new MaxScaler(2));
    }

}
