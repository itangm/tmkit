package cn.tmkit.core.tuple;

import cn.tmkit.core.lang.Asserts;
import cn.tmkit.core.support.CombineIterator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * 元组数据类型的接口定义
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public interface Tuple extends CombineIterator<Object>, Serializable {

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    int size();

    /**
     * 返回元组中所有的元素
     *
     * @return 元素列表
     */
    Object[] elements();

    /**
     * 获取指定位置的元素
     *
     * @param index 在元组中的位置，从0开始
     * @param <T>   元素类型
     * @return 元素值
     */
    @SuppressWarnings("unchecked")
    default <T> T element(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException("The index[" + index + "] is out of tuple size[" + size() + "]");
        }
        return (T) elements()[index];
    }

    /**
     * 创建一个空的元组
     *
     * @return 空的元组
     */
    static Tuple0 empty() {
        return Tuple0.instance();
    }

    /**
     * 创建一个元素的元组
     *
     * @param t1   元素的值
     * @param <T1> 元素的类型
     * @return {@linkplain Tuple1}
     */
    static <T1> Tuple1<T1> of(T1 t1) {
        return new Tuple1<>(t1);
    }

    /**
     * 根据{@linkplain Map.Entry}创建一个{@code Tuple2}
     *
     * @param entry 一个键值对
     * @param <T1>  第一个元素的类型
     * @param <T2>  第二个元素的类型
     * @return {@linkplain Tuple2}
     */
    static <T1, T2> Tuple2<T1, T2> fromEntry(@NotNull Map.Entry<? extends T1, ? extends T2> entry) {
        return new Tuple2<>(Asserts.notNull(entry).getKey(), entry.getValue());
    }

    /**
     * 创建二个元素的元组
     *
     * @param t1   第一个元素的值
     * @param t2   第二个元素的值
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @return {@linkplain Tuple2}
     */
    static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }

    /**
     * 创建三个元素的元组
     *
     * @param t1   第一个元素的值
     * @param t2   第二个元素的值
     * @param t3   第三个元素的值
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @return {@linkplain Tuple3}
     */
    static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<>(t1, t2, t3);
    }

    /**
     * 创建四个元素的元组
     *
     * @param t1   第一个元素的值
     * @param t2   第二个元素的值
     * @param t3   第三个元素的值
     * @param t4   第四个元素的值
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @return {@linkplain Tuple4}
     */
    static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<>(t1, t2, t3, t4);
    }

    /**
     * 创建五个元素的元组
     *
     * @param t1   第一个元素的值
     * @param t2   第二个元素的值
     * @param t3   第三个元素的值
     * @param t4   第四个元素的值
     * @param t5   第五个元素的值
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @return {@linkplain Tuple5}
     */
    static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<>(t1, t2, t3, t4, t5);
    }

}
