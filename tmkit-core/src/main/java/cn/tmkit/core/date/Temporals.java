package cn.tmkit.core.date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

/**
 * 工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-21
 */
public class Temporals {

    /**
     * 计算两个日期之间的相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenDays(Temporal start, Temporal end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 获取两个日期的差，如果结束时间早于开始时间，获取结果为负。
     * 返回结果为{@link Duration}对象，通过调用toXXX方法返回相差单位
     *
     * @param startTimeInclude 开始时间（包含）
     * @param endTimeExclude   结束时间（不包含）
     * @return 时间差 {@link Duration}对象
     * @see Duration#between(Temporal, Temporal)
     */
    public static Duration between(LocalDateTime startTimeInclude, LocalDateTime endTimeExclude) {
        return Duration.between(startTimeInclude, endTimeExclude);
    }

}
