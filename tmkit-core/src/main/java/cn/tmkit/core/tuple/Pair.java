package cn.tmkit.core.tuple;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * 一对，只能在创建时传入，不允许修改元素
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Pair<L, R> extends BaseTuple implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable {

    private static final long serialVersionUID = 2023L;

    /**
     * 左侧对象
     */
    protected L left;

    /**
     * 右侧对象
     */
    protected R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
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
        return new Object[]{left, right};
    }

    /**
     * 构建{@code Pair}对象
     *
     * @param <L>   左侧对象类型
     * @param <R>   右侧对象类型
     * @param left  左侧值
     * @param right 右侧值
     * @return {@linkplain  Pair}
     */
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    /**
     * 构建{@code Pair}对象
     *
     * @param <L>   左侧对象类型
     * @param <R>   右侧对象类型
     * @param entry 键值对
     * @return {@linkplain  Pair}
     */
    public static <L, R> Pair<L, R> of(Map.Entry<L, R> entry) {
        if (entry != null) {
            return new Pair<>(entry.getKey(), entry.getValue());
        }
        return new Pair<>(null, null);
    }

    /**
     * 返回左侧的值
     *
     * @return 左侧值
     */
    public L getLeft() {
        return left;
    }

    /**
     * 返回右侧的值
     *
     * @return 右侧值
     */
    public R getRight() {
        return right;
    }

    /**
     * 作为键值对来用返回键
     *
     * @return 键
     */
    @Override
    public L getKey() {
        return left;
    }

    /**
     * 作为键值对来用返回值
     *
     * @return 值
     */
    @Override
    public R getValue() {
        return right;
    }

    /**
     * 设置值，该操作不允许
     *
     * @param value 值
     * @return 不允许操作，无返回值
     */
    @Override
    public R setValue(R value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据{@linkplain  Pair}的左侧和右侧元素进行对比，所以左侧和右侧的元素类型必须实现{@code Comparable}
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(@NotNull Pair<L, R> other) {
        Comparable<Object> thisLeft = (Comparable<Object>) this.left;
        Comparable<Object> otherLeft = (Comparable<Object>) other.left;
        int comparison = thisLeft.compareTo(otherLeft);
        if (comparison != 0) {
            return comparison;
        }

        Comparable<Object> thisRight = (Comparable<Object>) this.right;
        Comparable<Object> otherRight = (Comparable<Object>) other.right;
        return thisRight.compareTo(otherRight);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString() {
        return "Pair [left=" + left + ", right=" + right + "]";
    }

}
