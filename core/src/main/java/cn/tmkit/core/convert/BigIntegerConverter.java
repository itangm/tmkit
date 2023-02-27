package cn.tmkit.core.convert;

import cn.tmkit.core.lang.Numbers;
import cn.tmkit.core.lang.reflect.Singletons;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * BigInteger Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class BigIntegerConverter extends AbstractConverter<BigInteger> {

    private static final long serialVersionUID = 2023L;

    @Override
    protected BigInteger handleInternal(@NotNull Object value) {
        if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        } else if (value instanceof Boolean) {
            return BigInteger.valueOf(((Boolean) value) ? 1 : 0);
        } else {
            return Numbers.createBigInteger(execToStr(value));
        }
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<BigInteger> getTargetClass() {
        return BigInteger.class;
    }

    /**
     * 返回{@linkplain  BigIntegerConverter}实例对象
     *
     * @return {@linkplain  BigIntegerConverter}
     */
    public static BigIntegerConverter getInstance() {
        return Singletons.get(BigIntegerConverter.class);
    }
}
