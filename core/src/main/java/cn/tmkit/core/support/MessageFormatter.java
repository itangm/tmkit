package cn.tmkit.core.support;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Chars;
import cn.tmkit.core.lang.Strings;

/**
 * Message Formatter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class MessageFormatter {

    /**
     * 消息模板（消息表达式）
     */
    private final String messagePattern;

    public MessageFormatter(String messagePattern) {
        this.messagePattern = messagePattern;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    /**
     * 格式化传入一个参数消息
     *
     * @param arg 参数
     * @return 格式化后的消息
     */
    public String format(Object arg) {
        return format(new Object[]{arg});
    }

    /**
     * 格式化消息
     *
     * @param args 参数列表
     * @return 格式化的消息
     */
    public String format(Object... args) {
        if (messagePattern == null || Arrays.isEmpty(args)) {
            return messagePattern;
        }

        int argLength = args.length, pos = 0, findIndex;
        StringBuilder builder = new StringBuilder(messagePattern.length() + argLength * 50);

        for (int i = 0; i < argLength; i++) {
            findIndex = messagePattern.indexOf(Strings.EMPTY_JSON, pos);
            if (findIndex == -1) {
                // no more variables
                if (pos == 0) {
                    // this is a simple string
                    return messagePattern;
                }
                // add the tail string which contains no variables and return the result.
                builder.append(messagePattern, pos, messagePattern.length());
                return builder.toString();
            }

            // normal case
            if (isEscapedSeparator(messagePattern, findIndex)) {
                if (!isDoubleEscaped(messagePattern, findIndex)) {
                    // {被转义了
                    i--;
                    builder.append(messagePattern, pos, findIndex - 1);
                    builder.append(Strings.DELIMITER_START);
                    pos = findIndex + 1;
                } else {
                    // aa x:\\{}
                    builder.append(messagePattern, pos, findIndex - 1);
                    deeplyAppendParameter(builder, args[i]);
                    pos = findIndex + 2;
                }
            } else {
                builder.append(messagePattern, pos, findIndex);
                deeplyAppendParameter(builder, args[i]);
                pos = findIndex + 2;
            }
        }

        builder.append(messagePattern, pos, messagePattern.length());
        return builder.toString();
    }

    private void deeplyAppendParameter(StringBuilder builder, Object arg) {
        if (arg == null) {
            builder.append((String) null);
            return;
        }

        if (Arrays.isArray(arg)) {
            builder.append(Arrays.toString(arg));
        } else {
            builder.append(arg);
        }
    }

    /**
     * 判断前一个字符是不是\，如果是则表示转义字符
     *
     * @param messagePattern      消息模板
     * @param delimiterStartIndex 当前分隔符{}所在的位置
     * @return 如果是转义字符则返回{@code true}，否则返回{@code false}
     */
    private boolean isEscapedSeparator(String messagePattern, int delimiterStartIndex) {
        if (delimiterStartIndex == 0) {
            return false;
        }
        char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
        return potentialEscape == Chars.BACKSLASH;
    }

    /**
     * 判断是不是双\\，如果是则\不具有转义字符的含义了
     *
     * @param messagePattern      消息模板
     * @param delimiterStartIndex 当前分隔符{}所在的位置
     * @return 如果是返回{@code true}，否则返回{@code false}
     */
    private boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
        return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == Chars.BACKSLASH;
    }

}
