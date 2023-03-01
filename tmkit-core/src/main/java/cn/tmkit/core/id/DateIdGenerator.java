package cn.tmkit.core.id;

import cn.tmkit.core.date.DefaultCustomFormatter;
import cn.tmkit.core.date.LocalDateTimeUtil;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.lang.reflect.Singletons;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 带有日期的ID生成器,32位ID组成规则：
 * yyyyMMddHHmmssSSS组成的17为日期字符串
 * 用一个中划线分割
 * 剩余14个字符串随机字符串
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class DateIdGenerator implements IdGenerator<String> {

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        String datetimePrefix = LocalDateTimeUtil.format(DefaultCustomFormatter.PURE_DATETIME_MS);
        return datetimePrefix + Strings.DASH + getSeqId() + getRandomString(8);
    }

    private static final int maxId = 999999;
    //    private static int curId = 0;
    private static final AtomicInteger cursor = new AtomicInteger();
    private static final Object lock = new Object();

//    private String getShortId() { //25
//        return datetimePrefix() + getSeqId("#000000") + getRandomString(2);
//    }

    private String getSeqId() {
        int id = cursor.incrementAndGet();
        if (id > maxId) {
            synchronized (lock) {
                cursor.set(0);
                id = cursor.incrementAndGet();
            }
        }
//        synchronized (lock) {
//            if (curId >= maxId) {
//                curId = 0;
//            }
////            NumberFormat format = new DecimalFormat(type);
////            return format.format(value);
//        }
        return String.format("%06d", id);
    }

    private String getRandomString(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int psd = (int) (Math.random() * (26 * 2 + 10));
            if (psd >= 26 + 10) { //a~z
                char a = (char) (psd + 97 - 10 - 26);
                sb.append(a);
            } else if (psd >= 10) { //A~Z
                char a = (char) (psd + 65 - 10);
                sb.append(a);
            } else { //0~9
                sb.append(psd);
            }
        }
        return sb.toString();
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static DateIdGenerator getInstance() {
        return Singletons.get(DateIdGenerator.class);
    }
}
