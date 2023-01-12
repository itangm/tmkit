package cn.tmkit.core.lang;

/**
 * 断言工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Asserts {

    // region Assert

    /**
     * 判断表达式是否为{@code true}
     *
     * @param expression 表达式
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, null);
    }

    /**
     * 判断表达式是否为{@code true}
     *
     * @param expression 表达式
     * @param message    异常提示消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            if (message == null) {
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 确认参数非空w
     *
     * @param reference 对象
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T notNull(final T reference) {
        return notNull(reference, null);
    }

    /**
     * 确认参数非空
     *
     * @param reference 对象
     * @param message   异常消息,会通过{@linkplain String#valueOf(Object)}包装,即消息可{@code null}
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T notNull(final T reference, String message) {
        if (reference == null) {
            if (message == null) {
                throw new NullPointerException();
            }
            throw new NullPointerException(message);
        }
        return reference;
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的对象
     * @return 非空后返回原始字符串
     */
    public static <T> T notEmpty(final T reference) {
        return notEmpty(reference, null);
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的字符串
     * @param message   如果为空抛出异常信息
     * @return 非空后返回原始字符串
     */
    public static <T> T notEmpty(final T reference, final String message) {
        if (Objects.isEmpty(reference)) {
            if (message == null) {
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException(message);
        }
        return reference;
    }

    // endregion

}
