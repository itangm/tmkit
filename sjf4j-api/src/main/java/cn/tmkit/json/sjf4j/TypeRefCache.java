package cn.tmkit.json.sjf4j;

import java.util.List;

/**
 * 缓存类型
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-27
 */
public interface TypeRefCache {

    /**
     * {@code java.util.LIst<String>}的泛型包装的类型
     */
    BaseTypeRef<List<String>> LIST_STRING = new BaseTypeRef<List<String>>() {
    };

    /**
     * {@code java.util.LIst<Long>}的泛型包装的类型
     */
    BaseTypeRef<List<Long>> LIST_LONG = new BaseTypeRef<List<Long>>() {
    };

    /**
     * {@code java.util.LIst<Integer>}的泛型包装的类型
     */
    BaseTypeRef<List<Integer>> LIST_INTEGER = new BaseTypeRef<List<Integer>>() {
    };

}
