package cn.tmkit.core.support;

import cn.tmkit.core.exception.CloneRuntimeException;

/**
 * 标记接口，标记类或接口可以被克隆
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public interface Cloneable<T> extends java.lang.Cloneable {

    /**
     * 克隆当前对象，浅复制
     *
     * @return 克隆后的对象
     * @throws CloneRuntimeException 克隆时抛出的异常
     */
    T clone() throws CloneRuntimeException;

}
