package cn.tmkit.core.lang;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class Dates {

    /**
     * 从日期中获取年份
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateUtil.getYear(date); 2014
     * </pre>
     *
     * @param date 日期对象
     * @return 年份
     */
    public static int getYear(Date date) {
        Asserts.notNull(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateUtil.isLeapYear(date); false
     * </pre>
     *
     * @param date 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(final Date date) {
        return isLeapYear(getYear(date));
    }

    /**
     * 判断是否为闰年
     *
     * @param year 年,从1900年开始
     * @return 是否为闰年
     */
    public static boolean isLeapYear(final int year) {
        if (year < 1900) {
            return false;
        }

        //世纪闰年:能被400整除的为世纪闰年
        if (year % 400 == 0) {
            return true;
        }
        //普通闰年:能被4整除但不能被100整除的年份为普通闰年。
        return year % 4 == 0 && year % 100 != 0;
    }

}
