package cn.tmkit.core.date;

import cn.tmkit.core.exception.DateRuntimeException;

import java.time.DayOfWeek;

/**
 * 星期的枚举类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public enum Week {

    /**
     * 周一
     */
    MONDAY("Monday", "Mon", "一", 2, 1),

    /**
     * 周二
     */
    TUESDAY("Tue", "Tuesday", "二", 3, 2),

    /**
     * 周三
     */
    WEDNESDAY("Wed", "Wednesday", "三", 4, 3),

    /**
     * 周四
     */
    THURSDAY("Thu", "Thursday", "四", 5, 4),

    /**
     * 周五
     */
    FRIDAY("Fri", "Friday", "五", 6, 5),

    /**
     * 周六
     */
    SATURDAY("Sat", "Saturday", "六", 7, 6),

    /**
     * 周日
     */
    SUNDAY("Sun", "Sunday", "日", 1, 7),

    ;

    /**
     * 一周有七天
     */
    public static final int WEEK_LENGTH = 7;

    /**
     * 国内所有星期
     */
    private static final Week[] ENUMS = Week.values();

    /**
     * 英文简称
     */
    private final String enName;

    /**
     * 英文全称
     */
    private final String fullEnName;

    /**
     * 中文名
     */
    private final String cn;

    /**
     * 国外的数值
     */
    private final int enValue;

    /**
     * 国内的数值
     */
    private final int cnValue;

    /**
     * 构造器
     *
     * @param enName     英文简称
     * @param fullEnName 英文全称
     * @param cn         中文名
     */
    Week(String enName, String fullEnName, String cn, int enValue, int cnValue) {
        this.enName = enName;
        this.fullEnName = fullEnName;
        this.cn = cn;
        this.cnValue = cnValue;
        this.enValue = enValue;
    }

    /**
     * 返回星期的英文全名
     *
     * @return 英文全名
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 返回星期的英文缩写
     *
     * @return 英文缩写
     */
    public String getFullEnName() {
        return fullEnName;
    }

    /**
     * 返回星期的数值(1-7),1 代表周日
     *
     * @return 数值，范围在1-7
     */
    public int getEnValue() {
        return enValue;
    }

    /**
     * 返回星期的数值(1-7),1 代表周一
     *
     * @return 数值，范围在1-7
     */
    public int getCnValue() {
        return cnValue;
    }

    /**
     * 转换为中文名
     *
     * @return 星期的中文名
     */
    public String toChinese() {
        return toChinese("星期");
    }

    /**
     * 转换为中文名
     *
     * @param weekNamePre 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 星期的中文名
     */
    public String toChinese(String weekNamePre) {
        return weekNamePre + this.cn;
    }

    /**
     * 转为JDK8的星期
     *
     * @return 星期
     */
    public DayOfWeek to() {
        return DayOfWeek.of(ordinal() + 1);
    }

    /**
     * 当天星期的下一天
     *
     * @return 下一天的星期几
     */
    public Week next() {
        return plus(1);
    }

    /**
     * 返回星期几的之后的{@code days}的星期几
     *
     * @param days 增加天数
     * @return 星期几
     */
    public Week plus(int days) {
        int amount = days % 7;
        return ENUMS[(ordinal() + (amount + WEEK_LENGTH)) % WEEK_LENGTH];
    }

    /**
     * 返回星期几的之签的{@code days}的星期几
     *
     * @param days 减去天数
     * @return 星期几
     */
    public Week minus(int days) {
        return plus(-(days % WEEK_LENGTH));
    }

    /**
     * 根据数值返回对应的星期，1-代表周一，7-周日
     *
     * @param weekInt 数值
     * @return 星期
     */
    public static Week of(int weekInt) {
        if (weekInt < 1 || weekInt > WEEK_LENGTH) {
            throw new DateRuntimeException("Invalid value for weekInt: " + weekInt);
        }
        return ENUMS[weekInt - 1];
    }

}
