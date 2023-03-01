package cn.tmkit.core.lang.reflect;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Asserts;
import cn.tmkit.core.lang.Classes;
import cn.tmkit.core.support.SimpleCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 单例类的对象统一管理
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class Singletons {

    /**
     * 对象池
     */
    private static final SimpleCache<String, Object> OBJECT_POOLS = new SimpleCache<>(256);

    private static final Map<String, Lock> KEY_LOCK_MAP = new ConcurrentHashMap<>();

    private Singletons() {
    }

    /**
     * 获取指定类的单例对象
     *
     * @param clazz  类
     * @param params 构造器参数列表
     * @param <T>    泛型限定类型
     * @return 对象
     */
    public static <T> T get(Class<T> clazz, Object... params) {
        final String key = buildKey(clazz.getName(), params);
        return get(key, () -> Reflects.newInstance(clazz, params));
    }

    /**
     * 获取指定类的单例对象
     *
     * @param className 类名
     * @param params    构造器参数列表
     * @param <T>       泛型限定类型
     * @return 对象
     */
    public static <T> T get(String className, Object... params) {
        Asserts.notEmpty(className);
        Class<T> clazz = Classes.loadClass(className);
        return get(clazz, params);
    }

    /**
     * 获取指定类的单例对象
     *
     * @param key      自定义名
     * @param supplier 单例对象的提供者
     * @param <T>      泛型限定类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Supplier<T> supplier) {
        T instance = (T) OBJECT_POOLS.get(key);
        if (instance == null) {
            Lock lock = KEY_LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());
            lock.lock();
            try {
                // double check
                instance = (T) OBJECT_POOLS.get(key);
                if (instance == null && supplier != null) {
                    instance = (T) OBJECT_POOLS.put(key, supplier.get());
                }
            } finally {
                lock.unlock();
                KEY_LOCK_MAP.remove(key);
            }
        }
        return instance;
    }

    /**
     * 将对象放到单例池中，{@code key}为对象的完整类名
     *
     * @param obj 对象
     * @param <T> 泛型限定类型
     * @return 原对象
     */
    public static <T> T put(T obj) {
        return put(obj.getClass().getName(), obj);
    }

    /**
     * 将对象放到单例池中
     *
     * @param key 自定义key
     * @param obj 对象
     * @param <T> 泛型限定类型
     * @return 原对象
     */
    public static <T> T put(String key, T obj) {
        OBJECT_POOLS.put(key, obj);
        return obj;
    }

    /**
     * 移除key
     *
     * @param key 自定义key
     * @param <T> 泛型限定类型
     * @return 移除的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T remove(String key) {
        return (T) OBJECT_POOLS.remove(key);
    }

    /**
     * 移除key
     *
     * @param clazz 对象类型
     * @param <T>   泛型限定类型
     * @return 移除的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T remove(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return (T) OBJECT_POOLS.remove(clazz.getName());
    }

    /**
     * 清空单例缓存池
     */
    public static void destroy() {
        OBJECT_POOLS.clear();
    }

    /**
     * 构建key
     *
     * @param className 类的完整名
     * @param params    参数列表
     * @return key
     */
    private static String buildKey(String className, Object... params) {
        if (Arrays.isEmpty(params)) {
            return className;
        }
        return className + "(" + Arrays.join(params) + ")";
    }

}
