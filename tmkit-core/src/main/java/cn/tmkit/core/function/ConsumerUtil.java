package cn.tmkit.core.function;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * 消费工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-02-05
 */
public class ConsumerUtil {

    /**
     * 非空操作的消费
     *
     * @param c        集合
     * @param consumer 非空的操作
     * @param <E>      元素类型
     */
    public static <E> void isNotEmpty(Collection<E> c, Consumer<Collection<E>> consumer) {
        if (Collections.isEmpty(c) || Objects.isNull(consumer)) {
            return;
        }
        consumer.accept(c);
    }

    /**
     * 非空操作的消费
     *
     * @param value    值
     * @param consumer 非空的操作
     * @param <E>      元素类型
     */
    public static <E> void isNotNull(E value, Consumer<E> consumer) {
        if (value == null || consumer == null) {
            return;
        }
        consumer.accept(value);
    }

}
