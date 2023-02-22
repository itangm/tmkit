package cn.tmkit.core.lang;

/**
 * 布尔工具类，{@code true}为1，{@code false}为0
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-02-22
 */
public class Booleans {

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
