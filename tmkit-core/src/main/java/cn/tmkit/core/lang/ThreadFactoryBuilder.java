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
     * 线程优先级
     */
    private int priority = Thread.NORM_PRIORITY;

    /**
     * 守护状态
     */
    private boolean daemon;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public ThreadFactory build() {
        return build(this);
    }

    /**
     * 构建
     *
     * @param builder 构建对象
     * @return {@linkplain ThreadFactory} object
     */
    private ThreadFactory build(ThreadFactoryBuilder builder) {
        final AtomicLong count = new AtomicLong(1);
        return r -> {
            Thread thread = new Thread(r);
            if (builder.namePrefix != null) {
                thread.setName(builder.namePrefix + count.getAndIncrement());
            }
            thread.setPriority(priority);
            thread.setDaemon(daemon);
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

    /**
     * 设置线程优先级
     *
     * @param priority 线程优先级
     * @return 当前对象
     */
    public ThreadFactoryBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * 是否为守护线程
     *
     * @param on 是否开启守护
     * @return 当前对象
     */
    public ThreadFactoryBuilder daemon(boolean on) {
        this.daemon = on;
        return this;
    }

}
