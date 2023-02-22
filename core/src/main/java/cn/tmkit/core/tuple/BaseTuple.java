package cn.tmkit.core.tuple;

import cn.tmkit.core.lang.Objects;

import java.util.NoSuchElementException;

/**
 * 元组的基础实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public abstract class BaseTuple implements Tuple {

    private static final long serialVersionUID = 2023L;

    /**
     * 当前位置
     */
    private int current;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0, size = size(); i < size; i++) {
            sb.append(Objects.toString(element(i)));
            if (i + 1 < size) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return this.current < this.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object next() throws NoSuchElementException {
        if (hasNext()) {
            return element(this.current++);
        }
        throw new NoSuchElementException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseTuple tuple = (BaseTuple) o;
        return Objects.equals(elements(), tuple.elements());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(elements());
        return result;
    }

}
