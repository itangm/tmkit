package cn.tmkit.core.support;

import lombok.experimental.UtilityClass;

/**
 * Console Utilities
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
@UtilityClass
public class Console {

    //region ================ Standard Print ================

    /**
     * 向控制台打印打印空行
     *
     * @since {@code System.out.println()}
     */
    public static void log() {
        System.out.println();
    }

    /**
     * 向控制台打印对象
     *
     * @param obj 要打印的对象
     */
    public static void log(Object obj) {
        log("{}", obj);
    }

    /**
     * 向控制台打印消息
     *
     * @param format 消息，支持{}站位
     * @param args   占位符值
     */
    public static void log(String format, Object... args) {
        println(format, args);
    }

    /**
     * 向控制台打印消息
     *
     * @param format 消息，支持{}站位
     * @param args   占位符值
     */
    public static void println(String format, Object... args) {
        MessageFormatter mf = new MessageFormatter(format);
        System.out.println(mf.format(args));
    }

    /**
     * 向控制台打印消息，消息末尾不会有换行符
     *
     * @param format 消息，支持{}站位
     * @param args   占位符值
     */
    public static void print(String format, Object... args) {
        MessageFormatter mf = new MessageFormatter(format);
        System.out.print(mf.format(args));
    }

    //endregion

    //region ================ Error Print ================

    /**
     * 向控制台打印打印空行
     *
     * @since {@code System.error.println()}
     */
    public static void error() {
        System.err.println();
    }

    /**
     * 向控制台输出错误消息或异常，其效果等同于{@code System.err#.println()}
     *
     * @param obj 要打印的对象
     */
    public static void error(Object obj) {
        if (obj instanceof Throwable) {
            Throwable e = (Throwable) obj;
            error(e, e.getMessage());
        } else {
            error("{}", obj);
        }
    }

    /**
     * 向控制台输出错误消息，其效果等同于{@code System.err#.println()}
     *
     * @param format 错误消息，支持{}站位
     * @param args   占位符对应的参数值
     */
    public static void error(String format, Object... args) {
        error(null, format, args);
    }

    /**
     * 向控制台输出错误消息和异常，其效果等同于{@code System.err#.println()}
     *
     * @param e      异常对象
     * @param format 错误消息，支持{}站位
     * @param args   占位符对应的参数值
     */
    public static void error(Throwable e, String format, Object... args) {
        //Formatter
        MessageFormatter mf = new MessageFormatter(format);
        System.err.println(mf.format(args));

        if (e != null) {
            e.printStackTrace(System.err);
            System.err.flush();
        }
    }

    //endregion

}
