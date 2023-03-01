package cn.tmkit.core.convert;

import cn.tmkit.core.lang.reflect.Singletons;
import cn.tmkit.core.support.BooleanEvaluator;
import org.jetbrains.annotations.NotNull;

/**
 * 布尔转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

    private static final long serialVersionUID = 2022L;

    /**
     * 返回{@linkplain  BooleanConverter}实例对象
     *
     * @return {@linkplain  BooleanConverter}
     */
    public static BooleanConverter getInstance() {
        return Singletons.get(BooleanConverter.class);
    }

    @Override
    protected Boolean handleInternal(@NotNull Object value) {
        if (value instanceof Number) {
            // 0为false，其它数字为true
            return 0 != ((Number) value).doubleValue();
        }
        return BooleanEvaluator.DEFAULT_TRUE_EVALUATOR.evalTrue(execToStr(value));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Boolean> getTargetClass() {
        return Boolean.class;
    }

    @Override
    public String toString() {
        return "BooleanConverter";
    }

}
