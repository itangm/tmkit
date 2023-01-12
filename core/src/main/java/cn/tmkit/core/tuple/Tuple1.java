package cn.tmkit.core.tuple;

/**
 * 只有一个元素的元组
 *
 * @param <T1> 元素类型
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Tuple1<T1> extends BaseTuple {

    /**
     * 内部的数组
     */
    private transient final Object[] elements;

    public Tuple1(T1 t1) {
        elements = new Object[]{t1};
    }

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return 1;
    }

    /**
     * 返回元组中所有的元素
     *
     * @return 元素列表
     */
    @Override
    public Object[] elements() {
        return elements;
    }

    /**
     * 返回元素数据
     *
     * @return 一个元组的元素数据
     */
    public T1 one() {
        return element(0);
    }

}
