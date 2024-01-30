package cn.tmkit.core.lang;

import cn.tmkit.core.io.Files;

/**
 * 异常工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-01-30
 */
public class ThrowableUtil {

    /**
     * 将异常堆栈全部转为字符串
     *
     * @param e 异常堆栈
     * @return 字符串
     */
    public static String getStacktraceStr(Throwable e) {
        return getStacktraceStr(e, Integer.MAX_VALUE);
    }

    /**
     * 将异常堆栈全部转为字符串
     *
     * @param e     异常堆栈
     * @param lines 限制输出的行数
     * @return 字符串
     */
    public static String getStacktraceStr(Throwable e, int lines) {
        return printStackTrace(e, lines, false);
    }

    /**
     * 将异常堆栈全部转为字符串，异常全部输出到一行内
     *
     * @param e 异常堆栈
     * @return 字符串
     */
    public static String getStacktraceStrToOneLine(Throwable e) {
        return getStacktraceStrToOneLine(e, Integer.MAX_VALUE);
    }

    /**
     * 将异常堆栈全部转为字符串，异常全部输出到一行内
     *
     * @param e     异常堆栈
     * @param lines 限制输出的行数
     * @return 字符串
     */
    public static String getStacktraceStrToOneLine(Throwable e, int lines) {
        return printStackTrace(e, lines, true);
    }

    /**
     * 将异常堆栈全部转为字符串
     *
     * @param e          异常堆栈
     * @param lines      限制输出的行数
     * @param singleLine 是否输出到一行内
     * @return 字符串
     */
    private static String printStackTrace(Throwable e, int lines, boolean singleLine) {
        if (e == null) {
            return Strings.EMPTY_STRING;
        }
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        int length = Numbers.min(stackTraceElements.length, lines);
        StringBuilder sb = new StringBuilder(256 * length);
        for (int i = 0; i < length; i++) {
            if (!singleLine && i != 0) {
                sb.append(Chars.TAB);
            }
            sb.append(stackTraceElements[i]);
            if (!singleLine) {
                sb.append(Files.LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

}
