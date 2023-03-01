package cn.tmkit.core.filter;

import java.lang.reflect.Field;

/**
 * {@linkplain Field}过滤器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public interface FieldFilter extends Filter<Field> {

    /**
     * 静态属性过滤器
     */
    StaticFieldFilter STATIC_FIELD_FILTER = new StaticFieldFilter();

}
