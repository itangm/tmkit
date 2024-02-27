package cn.tmkit.core.lang;

import org.jetbrains.annotations.NotNull;

/**
 * 布尔工具类，{@code true}为1，{@code false}为0
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class Booleans {


    /**
     * 或运算，数组元素存在任意一个{@code true}则返回{@code true}
     *
     * <pre>
     *   Booleans.or(true, true)          = true
     *   Booleans.or(false, false)        = false
     *   Booleans.or(true, false)         = true
     * </pre>
     *
     * @param array {@linkplain  boolean}数组
     * @return {@linkplain true} or {@linkplain false}
     */
    public static boolean or(boolean @NotNull ... array) {
        if (Arrays.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (boolean element : array) {
            if (element) {
                return true;
            }
        }
        return false;
    }

    /**
     * 或运算，数组元素存在任意一个{@code true}则返回{@code true}
     *
     * <pre>
     *   Booleans.or(true, true)          = true
     *   Booleans.or(false, false)        = false
     *   Booleans.or(true, false)         = true
     * </pre>
     *
     * @param array {@linkplain  boolean}数组
     * @return {@linkplain true} or {@linkplain false}
     * @see #or(boolean...)
     */
    public static boolean anyTrue(boolean @NotNull ... array) {
        return or(array);
    }

    /**
     * 与运算，数组元素必须所有元素为{@code true}则返回{@code true}
     *
     * <pre>
     *   Booleans.and(true, true)          = true
     *   Booleans.and(false, false)        = false
     *   Booleans.and(true, false)         = false
     * </pre>
     *
     * @param array {@linkplain  boolean}数组
     * @return {@linkplain true} or {@linkplain false}
     */
    public static boolean and(boolean @NotNull ... array) {
        if (Arrays.isEmpty(array)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (boolean element : array) {
            if (!element) {
                return false;
            }
        }
        return true;
    }

    /**
     * 与运算，数组元素必须所有元素为{@code true}则返回{@code true}
     *
     * <pre>
     *   Booleans.and(true, true)          = true
     *   Booleans.and(false, false)        = false
     *   Booleans.and(true, false)         = false
     * </pre>
     *
     * @param array {@linkplain  boolean}数组
     * @return {@linkplain true
     */
    public static boolean allTrue(boolean @NotNull ... array) {
        return and(array);
    }

    /**
     * boolean值转为char
     *
     * @param value Boolean值
     * @return char值
     */
    public static char toChar(boolean value) {
        return (char) (toInt(value));
    }

    /**
     * boolean值转为Character
     *
     * @param value Boolean值
     * @return Character值
     */
    public static Character toCharacter(boolean value) {
        return toChar(value);
    }

    /**
     * boolean值转为Character
     *
     * @param value Boolean值，允许为{@code null}
     * @return Character值
     */
    public static Character toCharacter(Boolean value) {
        if (value == null) {
            return null;
        }
        return toCharacter(value.booleanValue());
    }

    /**
     * boolean值转为int
     *
     * @param value Boolean值
     * @return int值
     */
    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    /**
     * boolean值转为Integer
     *
     * @param value Boolean值
     * @return Integer值
     */
    public static Integer toInteger(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为Integer
     *
     * @param value Boolean值
     * @return Integer值
     */
    public static Integer toInteger(Boolean value) {
        if (value == null) {
            return null;
        }
        return toInteger(value.booleanValue());
    }

}
