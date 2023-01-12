package cn.tmkit.core.lang;

import cn.tmkit.core.exception.CloneRuntimeException;

/**
 * 克隆支持类，提供默认的克隆方法
 *
 * @param <T> 继承类的类型
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class CloneSupport<T> implements Cloneable<T> {

    /**
     * 克隆当前对象，浅复制
     *
     * @return 克隆后的对象
     * @throws CloneRuntimeException 克隆时抛出的异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public T clone() throws CloneRuntimeException {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneRuntimeException(e);
        }
    }

}
