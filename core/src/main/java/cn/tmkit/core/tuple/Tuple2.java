package cn.tmkit.core.tuple;

/**
 * 有二个元素的元组
 *
 * @param <T1> 元素类型
 * @param <T2> 元素类型
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Tuple2<T1, T2> extends BaseTuple {

    /**
     * 内置数组
     */
    private final Object[] elements;

    public Tuple2(T1 t1, T2 t2) {
        elements = new Object[]{t1, t2};
    }

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return 2;
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
     * @return 元组的第一个元素
     */
    public T1 one() {
        return element(0);
    }

    /**
     * 返回元素数据
     *
     * @return 元组的第二个元素
     */
    public T2 two() {
        return element(1);
    }

}
