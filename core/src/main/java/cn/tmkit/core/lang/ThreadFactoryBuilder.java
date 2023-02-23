package cn.tmkit.core.lang;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ThreadFactory构建
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class ThreadFactoryBuilder implements Builder<ThreadFactory> {

    /**
     * 线程名的前缀
     */
    private String namePrefix;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public ThreadFactory build() {
        return build(this);
    }

    private ThreadFactory build(ThreadFactoryBuilder builder) {
        final AtomicLong count = new AtomicLong(1);
        return r -> {
            Thread thread = new Thread(r);
            if (builder.namePrefix != null) {
                thread.setName(builder.namePrefix + count.getAndIncrement());
            }
            return thread;
        };
    }

    /**
     * 创建{@linkplain ThreadFactoryBuilder}
     *
     * @return {@code ThreadFactoryBuilder}
     */
    public static ThreadFactoryBuilder builder() {
        return new ThreadFactoryBuilder();
    }

    /**
     * 设置线程名前缀，比如设置前缀为fast-thread-，那么真实的线程名如fast-thread-1
     *
     * @param namePrefix 线程名前缀
     * @return 当前对象
     */
    public ThreadFactoryBuilder namePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

}
