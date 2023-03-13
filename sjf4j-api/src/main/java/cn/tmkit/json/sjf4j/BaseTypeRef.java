package cn.tmkit.json.sjf4j;

import cn.tmkit.core.lang.reflect.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 该类主要用于传递泛型的类型，避免在运行时期找不到泛型的实际类型。
 * 具体用法：由于本类是一个抽象类，所以需要子类去实现。比如下面的代码实现String的泛型传递。
 * </p>
 * <pre>
 *  TypeRef ref = new TypeRef&lt;List&lt;String&gt;&gt;() { };
 * </pre>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public abstract class BaseTypeRef<T> implements Comparator<T> {

    final Type type;
    final Class<? super T> rawType;

    @SuppressWarnings("unchecked")
    protected BaseTypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.rawType = (Class<? super T>) Types.getRawType(type);
    }

    public Type getType() {
        return type;
    }

    public Class<? super T> getRawType() {
        return rawType;
    }

    @Override
    public int compare(T o1, T o2) {
        return 0;
    }

    /**
     * {@code java.util.LIst<String>}的泛型包装的类型
     */
    public static final Type LIST_STRING = new BaseTypeRef<List<String>>() {
    }.getType();

    /**
     * {@code java.util.LIst<Long>}的泛型包装的类型
     */
    public static final Type LIST_LONG = new BaseTypeRef<List<Long>>() {
    }.getType();

    /**
     * {@code java.util.LIst<Integer>}的泛型包装的类型
     */
    public static final Type LIST_INTEGER = new BaseTypeRef<List<Integer>>() {
    }.getType();

    /**
     * {@code java.util.LIst<Map<String, String>>}的泛型包装的类型
     */
    public static final Type LIST_MAP_STRING = new BaseTypeRef<List<Map<String, String>>>() {
    }.getType();

}
