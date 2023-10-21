package cn.tmkit.core.lang;

import cn.tmkit.core.lang.regex.Regexes;
import cn.tmkit.core.math.BigDecimals;
import cn.tmkit.core.math.FluentBigDecimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数字工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class Numbers {

    private static final int DEFAULT_SCALE = 2;

    private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * 10
     */
    public static final int TEN = 10;

    /**
     * 100
     */
    public static final int ONE_HUNDRED = 100;

    /**
     * 1000
     */
    public static final int ONE_THOUSAND = 1000;

    /**
     * 将科学计数法转为字符串
     *
     * @param val 以科学计数法形式的数字
     * @return 字符串
     */
    public static String toPlainStr(double val) {
        return toPlainStr(String.valueOf(val));
    }

    /**
     * 将科学计数法转为字符串
     *
     * @param val 以科学计数法形式的数字
     * @return 字符串
     */
    public static String toPlainStr(String val) {
        if (val == null) {
            return null;
        }
        if (val.length() == 0) {
            return Strings.EMPTY_STRING;
        }
        return val.matches("^\\d(.\\d+)?[eE](\\d+)$") ? new BigDecimal(val).toPlainString() : val;
    }

    /**
     * 判断字符串是否为纯数字组成
     *
     * @param str 数值字符串
     * @return {@link Boolean}
     * @see #isNumeric(CharSequence) 带有小数点的判断请调用该方法
     */
    public static boolean isDigits(CharSequence str) {
        if (Strings.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是数值,如果是则返回true,否则返回false
     * <p>
     * 支持类似'1','-1','0.01','.01'等格式的数值字符串,但是不支持进制数字字符串
     * </p>
     *
     * @param str 数值字符串
     * @return {@linkplain Boolean}
     */
    public static boolean isNumeric(CharSequence str) {
        if (Strings.hasText(str)) {
            if (str.length() == 1 && ((str.charAt(0) == Chars.PLUS || str.charAt(0) == Chars.MINUS))) {
                return false;
            }
            String regex = "^[-+]?(\\d+)?(\\.\\d+)?$";
            return Regexes.isMatch(regex, str);
        }
        return false;
    }

    /**
     * 判断是否是数值,如果是则返回true,否则返回false
     *
     * @param str 数值字符串
     * @return {@linkplain Boolean}
     * @see #isNumeric(CharSequence)
     */
    public static boolean isDecimal(CharSequence str) {
        return isNumeric(str);
    }

    // region 向右取整的最小值

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param val 数值
     * @return 向右取最小值
     * @see Math#ceil(double)
     */
    public static double ceil(double val) {
        return Math.ceil(val);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param val 数值
     * @return 向右取最小值
     */
    public static int ceilToInt(double val) {
        return (int) ceil(val);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param val 数值
     * @return 向右取最小值
     */
    public static long ceilToLong(double val) {
        return (long) ceil(val);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向右取最小值
     */
    public static int ceilDiv(int x, int y) {
        return ceilToInt((double) x / y);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向右取最小值
     */
    public static long ceilDiv(long x, long y) {
        return ceilToLong((double) x / y);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向右取最小值
     */
    public static double ceilDiv(double x, double y) {
        return ceil(x / y);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向右取最小值
     */
    public static int ceilDivToInt(double x, double y) {
        return ceilToInt(x / y);
    }

    /**
     * 向上取整，也可以立即为向右取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向右取最小值
     */
    public static long ceilDivToLong(double x, double y) {
        return ceilToLong(x / y);
    }

    // endregion


    // region 向左取整的最大值

    /**
     * 向下取整，也可以立即为向左取整
     *
     * @param val 数值
     * @return 向左取最大值
     * @see Math#floor(double)
     */
    public static double floor(double val) {
        return Math.floor(val);
    }

    /**
     * 向下取整，也可以立即为向左取整
     *
     * @param val 数值
     * @return 向左取最大值
     */
    public static int floorToInt(double val) {
        return (int) floor(val);
    }

    /**
     * 向下取整，也可以立即为向左取整
     *
     * @param val 数值
     * @return 向左取最大值
     */
    public static long floorToLong(double val) {
        return (long) floor(val);
    }

    /**
     * 将两个数的商向下取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 商的结果向下取整
     * @see Math#floorDiv(int, int)
     */
    public static int floorDiv(int x, int y) {
        return Math.floorDiv(x, y);
    }

    /**
     * 将两个数的商向下取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 商的结果向下取整
     * @see Math#floorDiv(long, long)
     */
    public static long floorDiv(long x, long y) {
        return Math.floorDiv(x, y);
    }

    /**
     * 将两个数的商向下取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 商的结果向下取整
     */
    public static double floorDiv(double x, double y) {
        return floor(x / y);
    }

    /**
     * 将两个数的商向下取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 商的结果向下取整
     */
    public static int floorDivToInt(double x, double y) {
        return floorToInt(x / y);
    }

    /**
     * 将两个数的商向下取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 商的结果向下取整
     */
    public static long floorDivToLong(double x, double y) {
        return floorToLong(x / y);
    }

    // endregion


    // region 数值转换

    /**
     * long的数值转为int的数值，如果超出{@linkplain Integer#MAX_VALUE}则抛出异常
     *
     * @param val 数值
     * @return 转换后的数值
     * @throws ArithmeticException 数据溢出
     */
    public static int toIntExact(long val) throws ArithmeticException {
        int i = (int) val;
        if (i != val) {
            throw new ArithmeticException("integer overflow");
        }
        return i;
    }

    /**
     * 安装金额格式格式化，即保留2位有效小数进行四舍五入。
     *
     * @param val 数值
     * @return 格式化后的数值
     */
    public static double formatAsMoney(double val) {
        return FluentBigDecimal.of(val).doubleValue();
    }

    /**
     * 金额元转为金额分
     *
     * @param yuan 元
     * @return 分
     */
    public static Integer yuan2Fen(BigDecimal yuan) {
        if (yuan == null) {
            return null;
        }
        return FluentBigDecimal.of(yuan).multiply(100).intValueExact();
    }

    /**
     * 金额元转为金额分
     *
     * @param yuan 元
     * @return 分
     */
    public static Long yuan2FenLong(BigDecimal yuan) {
        if (yuan == null) {
            return null;
        }
        return FluentBigDecimal.of(yuan).multiply(100).longValueExact();
    }

    /**
     * 人民币金额转为分
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(long yuan) {
        return yuan * 100;
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(double yuan) {
        return yuan2Fen(Double.toString(yuan));
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(String yuan) {
        Asserts.notNull(yuan);
        return BigDecimals.multiply(yuan, 100).longValue();
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenStr(String yuan) {
        return String.valueOf(yuan2Fen(yuan));
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenStr(long yuan) {
        return String.valueOf(yuan2Fen(yuan));
    }

    /**
     * 将人民币金额(字符串)转成分(结果为仍为字符串)
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额，单位为元
     * @return 金额，单位为分
     */
    public static String yuan2FenStr(double yuan) {
        return String.valueOf(yuan2Fen(yuan));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static double fen2Yuan(long fen) {
        return fen2Yuan(Long.toString(fen));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static double fen2Yuan(String fen) {
        Asserts.notNull(fen);
        return BigDecimals.divide(fen, 100).doubleValue();
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static String fen2YuanStr(String fen) {
        if (Objects.isNull(fen)) {
            return null;
        }
        return String.format("%.2f", fen2Yuan(fen));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static String fen2YuanStr(long fen) {
        return fen2YuanStr(Long.toString(fen));
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static String fen2YuanStr(Long fen) {
        return Objects.isNull(fen) ? null : fen2YuanStr(fen.toString());
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     */
    public static String fen2YuanStr(Integer fen) {
        return Objects.isNull(fen) ? null : fen2YuanStr(fen.toString());
    }

    /**
     * 将给定的数值转换为目标类型的实例
     *
     * @param number      数值
     * @param targetClass 目标类型
     * @param <T>         泛型
     * @return 目标类型实例
     * @throws IllegalArgumentException 非JDK{@linkplain Number}实现类
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convert(Number number, Class<T> targetClass)
            throws IllegalArgumentException {
        if (Objects.isAnyNull(number, targetClass)) {
            return null;
        }

        if (targetClass.isInstance(number)) {
            return (T) number;
        } else if (Byte.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Byte.valueOf(number.byteValue());
        } else if (Short.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Short.valueOf(number.shortValue());
        } else if (Integer.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Integer.valueOf(number.intValue());
        } else if (Float.class == targetClass) {
            return (T) Float.valueOf(number.floatValue());
        } else if (Double.class == targetClass) {
            return (T) Double.valueOf(number.doubleValue());
        } else if (Long.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            return (T) Long.valueOf(value);
        } else if (BigInteger.class == targetClass) {
            if (number instanceof BigDecimal) {
                // do not lose precision - use BigDecimal's own conversion
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                // original value is not a Big* number - use standard long conversion
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (BigDecimal.class == targetClass) {
            return (T) new BigDecimal(number.toString());
        } else if (AtomicInteger.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new AtomicInteger(number.intValue());
        } else if (AtomicLong.class == targetClass) {
            long value = checkedLongValue(number, targetClass);
            return (T) new AtomicLong(value);
        }
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
    }

    // endregion


    // region min

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see Arrays#min(long...)
     */
    public static long min(long... numberArray) {
        return Arrays.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see Arrays#min(int...)
     */
    public static int min(int... numberArray) {
        return Arrays.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see Arrays#min(short...)
     */
    public static short min(short... numberArray) {
        return Arrays.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see Arrays#min(double...)
     */
    public static double min(double... numberArray) {
        return Arrays.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数字数组
     * @return 最小值
     * @see Arrays#min(float...)
     */
    public static float min(float... numberArray) {
        return Arrays.min(numberArray);
    }

    /**
     * 取最小值
     *
     * @param numberArray 数组
     * @return 最小值
     * @see Arrays#min(Comparable[])
     */
    public static BigDecimal min(BigDecimal... numberArray) {
        return Arrays.min(numberArray);
    }

    // endregion


    // region max

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(long...)
     */
    public static long max(long... numberArray) {
        return Arrays.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(int...)
     */
    public static int max(int... numberArray) {
        return Arrays.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(short...)
     */
    public static short max(short... numberArray) {
        return Arrays.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(double...)
     */
    public static double max(double... numberArray) {
        return Arrays.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(float...)
     */
    public static float max(float... numberArray) {
        return Arrays.max(numberArray);
    }

    /**
     * 取最大值
     *
     * @param numberArray 数组
     * @return 最大值
     * @see Arrays#max(Comparable[])
     */
    public static BigDecimal max(BigDecimal... numberArray) {
        return Arrays.max(numberArray);
    }

    // endregion


    // region 创建数值对象

    /**
     * 将字符串转为数值
     *
     * @param str 字符串数值
     * @return 数值
     * @throws NumberFormatException 转换异常
     */
    public static Number createNumber(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        if (Strings.isBlank(str)) {
            return null;
            //throw new NumberFormatException("A blank string is not a valid number");
        }
        if (str.startsWith("--")) {
            // this is protection for poorness in java.lang.BigDecimal.
            // it accepts this as a legal value, but it does not appear
            // to be in specification of class. OS X Java parses it to
            // a wrong value.
            return null;
        }
        if (str.startsWith("0x") || str.startsWith("-0x")) {
            return createInteger(str);
        }
        char lastChar = str.charAt(str.length() - 1);
        String mant;
        String dec;
        String exp;
        int decPos = str.indexOf('.');
        int expPos = str.indexOf('e') + str.indexOf('E') + 1;

        if (decPos > -1) {
            if (expPos > -1) {
                if (expPos < decPos) {
                    throw new NumberFormatException(str + " is not a valid number.");
                }
                dec = str.substring(decPos + 1, expPos);
            } else {
                dec = str.substring(decPos + 1);
            }
            mant = str.substring(0, decPos);
        } else {
            if (expPos > -1) {
                mant = str.substring(0, expPos);
            } else {
                mant = str;
            }
            dec = null;
        }
        if (!Character.isDigit(lastChar) && lastChar != Chars.DOT) {
            if (expPos > -1 && expPos < str.length() - 1) {
                exp = str.substring(expPos + 1, str.length() - 1);
            } else {
                exp = null;
            }
            //Requesting a specific type..
            String numeric = str.substring(0, str.length() - 1);
            boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
            switch (lastChar) {
                case 'l':
                case 'L':
                    if (dec == null
                            && exp == null
                            && (numeric.charAt(0) == '-' && isDigits(numeric.substring(1)) || isDigits(numeric))) {
                        try {
                            return createLong(numeric);
                        } catch (NumberFormatException nfe) {
                            //Too big for a long
                        }
                        return createBigInteger(numeric);
                    }
                    throw new NumberFormatException(str + " is not a valid number.");
                case 'f':
                case 'F':
                    try {
                        Float f = createFloat(numeric);
                        if (!(f.isInfinite() || (f == 0.0F && !allZeros))) {
                            //If it's too big for a float or the float value = 0 and the string
                            //has non-zeros in it, then float does not have the precision we want
                            return f;
                        }
                    } catch (NumberFormatException nfe) {
                        // ignore the bad number
                    }
                    //$FALL-THROUGH$
                case 'd':
                case 'D':
                    try {
                        Double d = createDouble(numeric);
                        if (!(d.isInfinite() || (d.floatValue() == 0.0D && !allZeros))) {
                            return d;
                        }
                    } catch (NumberFormatException nfe) {
                        // ignore the bad number
                    }
                    try {
                        return createBigDecimal(numeric);
                    } catch (NumberFormatException e) {
                        // ignore the bad number
                    }
                    //$FALL-THROUGH$
                default:
                    throw new NumberFormatException(str + " is not a valid number.");
            }
        } else {
            //User doesn't have a preference on the return type, so let's start
            //small and go from there...
            if (expPos > -1 && expPos < str.length() - 1) {
                exp = str.substring(expPos + 1);
            } else {
                exp = null;
            }
            if (dec == null && exp == null) {
                //Must be an int,long,bigint
                try {
                    return createInteger(str);
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                try {
                    return createLong(str);
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                return createBigInteger(str);
            } else {
                //Must be a float,double,BigDecimal
                boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
                try {
                    Float f = createFloat(str);
                    if (!(f.isInfinite() || (f == 0.0F && !allZeros))) {
                        return f;
                    }
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                try {
                    Double d = createDouble(str);
                    if (!(d.isInfinite() || (d == 0.0D && !allZeros))) {
                        return d;
                    }
                } catch (NumberFormatException nfe) {
                    // ignore the bad number
                }
                return createBigDecimal(str);
            }
        }
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
        return Integer.decode(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Float</code>.</p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Float</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Float createFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Long</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Long</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>Double</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Double</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>BigInteger</code>.</p>
     * <p>
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     *
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>BigInteger</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static BigInteger createBigInteger(String str) {
        if (str == null) {
            return null;
        }
        if (Strings.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigInteger(str);
    }

    /**
     * 数字字符串转{@linkplain BigDecimal}，如果字符串为<code>null</code>则返回<code>null</code>
     *
     * @param str 待转的数字字符串，可为null
     * @return converted <code>BigDecimal</code>
     * @throws NumberFormatException if the value cannot be converted
     */
    public static BigDecimal createBigDecimal(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        // handle JDK1.3.1 bug where "" throws IndexOutOfBoundsException
        if (Strings.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(str);
    }

    /**
     * 数值转{@linkplain BigDecimal},如果参数为<code>null</code>则返回<code>null</code>
     *
     * @param value 数值
     * @return converted <code>BigDecimal</code>
     */
    public static BigDecimal createBigDecimal(Number value) {
        if (value == null) {
            return null;
        } else if (value instanceof FluentBigDecimal) {
            return ((FluentBigDecimal) value).getValue();
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Long) {
            return new BigDecimal((Long) value);
        } else if (value instanceof Integer) {
            return new BigDecimal((Integer) value);
        } else if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        } else {
            return createBigDecimal(value.toString());
        }
    }

    // endregion


    // region 内部私有方法

    /**
     * 判断字符串是否都为0
     *
     * @param str 字符串
     * @return {@code true} or {@code false}
     */
    private static boolean isAllZeros(String str) {
        if (str == null) {
            return true;
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) != '0') {
                return false;
            }
        }
        return str.length() > 0;
    }

    /**
     * Check for a {@code BigInteger}/{@code BigDecimal} long overflow
     * before returning the given number as a long value.
     *
     * @param number      the number to convert
     * @param targetClass the target class to convert to
     * @return the long value, if convertible without overflow
     * @throws IllegalArgumentException if there is an overflow
     * @see #raiseOverflowException
     */
    private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {
        BigInteger bi = null;
        if (number instanceof BigInteger) {
            bi = (BigInteger) number;
        } else if (number instanceof BigDecimal) {
            bi = ((BigDecimal) number).toBigInteger();
        }
        // Effectively analogous to JDK 8's BigInteger.longValueExact()
        if (bi != null && (bi.compareTo(LONG_MIN) < 0 || bi.compareTo(LONG_MAX) > 0)) {
            raiseOverflowException(number, targetClass);
        }
        return number.longValue();
    }

    /**
     * Raise an <em>overflow</em> exception for the given number and target class.
     *
     * @param number      the number we tried to convert
     * @param targetClass the target class we tried to convert to
     * @throws IllegalArgumentException if there is an overflow
     */
    private static void raiseOverflowException(Number number, Class<?> targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    // endregion

}
