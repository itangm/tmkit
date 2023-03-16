package cn.tmkit.core.lang;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-16
 */
public class Threads {

    /**
     * 线程睡眠指定时间
     *
     * @param ms 睡眠的时间，单位毫秒
     */
    public static void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException iex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 使得线程永久睡眠
     */
    public static void sleepForever() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException iex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 线程睡眠随机时间，其控制范围[min,max]
     *
     * @param min 最低睡眠时间，单位毫秒
     * @param max 最高睡眠时间，单位毫秒
     */
    public static void sleepRandomly(final long min, final long max) {
        long ms = RandomUtil.nextLong(min, max + 1);
        sleep(ms);
    }


    /**
     * 创建一个守护进程的线程工厂
     *
     * @param name 线程名的前缀
     * @return {@linkplain ThreadFactory}对象
     */
    public static ThreadFactory daemonThreadFactory(final String name) {
        return daemonThreadFactory(name, Thread.NORM_PRIORITY);
    }

    /**
     * 创建一个守护进程的线程工厂
     *
     * @param name     线程名的前缀
     * @param priority 优先级
     * @return {@linkplain ThreadFactory}对象
     */
    public static ThreadFactory daemonThreadFactory(final String name, final int priority) {
        return ThreadFactoryBuilder.builder().namePrefix(name).priority(priority).daemon(true).build();
    }


}
