package cn.tmkit.core.date;

import cn.tmkit.core.support.SimpleCache;

import java.time.format.DateTimeFormatter;

/**
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class CustomFormatterCache {

    /**
     * 日期格式化缓存，{@linkplain DateTimeFormatter}是线程安全的
     */
    private static final SimpleCache<String, DateTimeFormatter> CACHE = new SimpleCache<>(32);

    static {
        for (DefaultCustomFormatter value : DefaultCustomFormatter.values()) {
            CACHE.put(value.getPattern(), value.getFormatter());
        }
    }

    /**
     * 获取格式化器
     *
     * @param pattern 日期格式字符串
     * @return 返回Formatter
     */
    public static DateTimeFormatter ofPattern(String pattern) {
        DateTimeFormatter formatter = CACHE.get(pattern);
        if (formatter == null) {
            synchronized (CACHE) {
                formatter = DateTimeFormatter.ofPattern(pattern);
                CACHE.put(pattern, formatter);
            }
        }
        return formatter;
    }

}
