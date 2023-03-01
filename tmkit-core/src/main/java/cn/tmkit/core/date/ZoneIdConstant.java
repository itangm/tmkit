package cn.tmkit.core.date;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * 时区常量类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class ZoneIdConstant {

    /**
     * 默认时区
     */
    public static final ZoneId SYSTEM_DEFAULT = ZoneId.systemDefault();

    /**
     * 上海
     */
    public static final ZoneId ASIA_SHANGHAI = ZoneId.of("Asia/Shanghai");

    /**
     * 系统默认时区
     */
    public static final ZoneOffset DEFAULT_ZONE_OFFSET = OffsetDateTime.now().getOffset();


    /**
     * 北京时区，即东八区
     */
    public static final ZoneOffset BEIJING_ZONE_OFFSET = ZoneOffset.ofHours(8);

    /**
     * 东八区
     */
    public static final ZoneId UTC8 = ZoneId.of("UTC+8");

}
