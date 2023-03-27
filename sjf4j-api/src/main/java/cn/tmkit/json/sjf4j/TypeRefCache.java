package cn.tmkit.json.sjf4j;

import cn.tmkit.core.support.SimpleCache;

import java.util.List;

/**
 * 缓存类型
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-27
 */
public class TypeRefCache {

    /**
     * 缓存泛型类型的Type
     */
    private static final SimpleCache<String, BaseTypeRef<?>> TYPE_REF_CACHE = new SimpleCache<>(32);

    /**
     * {@code java.util.LIst<String>}的泛型包装的类型
     */
    public static final BaseTypeRef<List<String>> LIST_STRING = new BaseTypeRef<List<String>>() {
    };

    /**
     * {@code java.util.LIst<Long>}的泛型包装的类型
     */
    public static final BaseTypeRef<List<Long>> LIST_LONG = new BaseTypeRef<List<Long>>() {
    };

    /**
     * {@code java.util.LIst<Integer>}的泛型包装的类型
     */
    public static final BaseTypeRef<List<Integer>> LIST_INTEGER = new BaseTypeRef<List<Integer>>() {
    };

}
