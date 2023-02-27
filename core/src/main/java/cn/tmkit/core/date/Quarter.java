package cn.tmkit.core.date;

import cn.tmkit.core.exception.DateRuntimeException;

/**
 * 季度枚举类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public enum Quarter {

    /**
     * 一季度
     */
    Q1(1, "一季度"),

    /**
     * 二季度
     */
    Q2(2, "二季度"),

    /**
     * 三季度
     */
    Q3(3, "三季度"),

    /**
     * 四季度
     */
    Q4(4, "四季度"),

    ;

    /**
     * 值
     */
    private final int value;

    /**
     * 中文名
     */
    private final String cn;

    Quarter(int value, String cn) {
        this.value = value;
        this.cn = cn;
    }

    /**
     * 枚举的值
     *
     * @return 枚举的值
     */
    private int getValue() {
        return value;
    }

    /**
     * 中文名
     *
     * @return 中文名
     */
    public String toCn() {
        return cn;
    }

    /**
     * 解析季度
     *
     * @param quarter 季度值
     * @return {@linkplain Quarter}
     */
    public static Quarter of(int quarter) {
        if (quarter < 1 || quarter > 4) {
            throw new DateRuntimeException("Invalid value for quarter:" + quarter);
        }
        return ENUMS[quarter - 1];
    }

    private static final Quarter[] ENUMS = Quarter.values();

}
