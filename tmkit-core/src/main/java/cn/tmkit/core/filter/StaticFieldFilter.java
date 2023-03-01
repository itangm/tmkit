package cn.tmkit.core.filter;

import cn.tmkit.core.lang.reflect.ReflectUtil;

import java.lang.reflect.Field;

/**
 * 静态的field过滤器实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class StaticFieldFilter implements FieldFilter {

    /**
     * 是否接受该对象
     *
     * @param ele 当前对象
     * @return 接受则返回{@code true},否则返回{@code false}
     */
    @Override
    public boolean accept(Field ele) {
        return ReflectUtil.isStatic(ele);
    }

}
