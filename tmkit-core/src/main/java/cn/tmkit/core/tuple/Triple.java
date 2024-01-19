package cn.tmkit.core.tuple;

import org.jetbrains.annotations.NotNull;

/**
 * 可以存储三个值的元组
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2024-01-19
 */
public class Triple<L, M, R> extends BaseTuple implements Comparable<Triple<L, M, R>> {

    /**
     * 左侧对象
     */
    protected L left;

    /**
     * 中间对象
     */
    protected M middle;

    /**
     * 右侧对象
     */
    protected R right;

    public Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new Triple<>(left, middle, right);
    }

    /**
     * 返回左侧对象
     *
     * @return 左侧对象
     */
    public L getLeft() {
        return left;
    }

    /**
     * 返回中间对象
     *
     * @return 中间对象
     */
    public M getMiddle() {
        return middle;
    }

    /**
     * 返回右侧对象
     *
     * @return 右侧对象
     */
    public R getRight() {
        return right;
    }

    /**
     * 设置左侧对象
     *
     * @param left 左侧对象
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * 设置中间对象
     *
     * @param middle 中间对象
     */
    public void setMiddle(M middle) {
        this.middle = middle;
    }

    /**
     * 设置右侧对象
     *
     * @param right 右侧对象
     */
    public void setRight(R right) {
        this.right = right;
    }

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return 3;
    }

    /**
     * 返回元组中所有的元素
     *
     * @return 元素列表
     */
    @Override
    public Object[] elements() {
        return new Object[]{left, middle, right};
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString() {
        return "Pair [left=" + left + " , middle = " + middle + " , right=" + right + "]";
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(@NotNull Triple<L, M, R> other) {
        Comparable<Object> thisLeft = (Comparable<Object>) this.left;
        Comparable<Object> otherLeft = (Comparable<Object>) other.left;
        int comparison = thisLeft.compareTo(otherLeft);
        if (comparison != 0) {
            return comparison;
        }

        Comparable<Object> thisMiddle = (Comparable<Object>) this.middle;
        Comparable<Object> otherMiddle = (Comparable<Object>) other.middle;
        comparison = thisMiddle.compareTo(otherMiddle);
        if (comparison != 0) {
            return comparison;
        }

        Comparable<Object> thisRight = (Comparable<Object>) this.right;
        Comparable<Object> otherRight = (Comparable<Object>) other.right;
        return thisRight.compareTo(otherRight);
    }

}
