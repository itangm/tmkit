package cn.tmkit.core.lang;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * 比较强工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-02-28
 */
public class ComparatorUtil {

    /**
     * 返回一个反向（从大到小）的比较器，根据对象的某一个属性提取
     *
     * @param keyExtractor 属性提取器
     * @param <T>          泛型类型
     * @return 比较器
     */
    public static <T> Comparator<T> reverseComparingInt(@NotNull ToIntFunction<? super T> keyExtractor) {
        return (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c2), keyExtractor.applyAsInt(c1));
    }

    /**
     * 返回一个反向（从大到小）的比较器，根据对象的某一个属性提取
     *
     * @param keyExtractor 属性提取器
     * @param <T>          泛型类型
     * @return 比较器
     */
    public static <T> Comparator<T> reverseComparingLong(@NotNull ToLongFunction<? super T> keyExtractor) {
        return (c1, c2) -> Long.compare(keyExtractor.applyAsLong(c2), keyExtractor.applyAsLong(c1));
    }

    /**
     * 返回一个反向（从大到小）的比较器，根据对象的某一个属性提取
     *
     * @param keyExtractor 属性提取器
     * @param <T>          泛型类型
     * @return 比较器
     */
    public static <T> Comparator<T> reverseComparingDouble(@NotNull ToDoubleFunction<? super T> keyExtractor) {
        return (c1, c2) -> Double.compare(keyExtractor.applyAsDouble(c2), keyExtractor.applyAsDouble(c1));
    }


}
