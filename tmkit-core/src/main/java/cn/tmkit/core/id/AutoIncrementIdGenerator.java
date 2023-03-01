package cn.tmkit.core.id;

import cn.tmkit.core.lang.Strings;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 实现ID自增长的生成器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class AutoIncrementIdGenerator implements IdGenerator<Long> {

    private final AtomicLong val;

    public AutoIncrementIdGenerator() {
        this(0);
    }

    public AutoIncrementIdGenerator(long initVal) {
        this.val = new AtomicLong(initVal);
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public Long get() {
        return val.getAndIncrement();
    }

    /**
     * 返回指定位数的ID，如果不够则左补零
     * 生成ID长度超出给定的长度，则返回{@code null}
     *
     * @param formatSize ID长度
     * @return 固定长度的ID
     */
    public String get(int formatSize) {
        long next = get();
        String id = Long.toString(next);
        if (formatSize == id.length()) {
            return id;
        } else if (formatSize < id.length()) {
            return Strings.EMPTY_STRING;
        }
        return String.format("%0" + formatSize + "d", next);
    }

}
