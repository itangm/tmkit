package cn.tmkit.core.convert;

import cn.tmkit.core.bean.BeanUtil;
import cn.tmkit.core.lang.reflect.Reflects;
import org.jetbrains.annotations.NotNull;

/**
 * Bean Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class BeanConverter<R> extends AbstractConverter<R> {

    private static final long serialVersionUID = 2023L;

    private final Class<R> beanClass;

    public BeanConverter(Class<R> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    protected R handleInternal(@NotNull Object value) {
        R result = Reflects.newInstance(beanClass);
        BeanUtil.copyProperties(value, result);
        return result;
    }

    @Override
    public Class<R> getTargetClass() {
        return beanClass;
    }

}
