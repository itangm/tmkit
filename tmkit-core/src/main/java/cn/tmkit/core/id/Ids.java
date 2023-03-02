package cn.tmkit.core.id;

import cn.tmkit.core.id.snowflake.FastSnowflake;
import cn.tmkit.core.lang.reflect.Singletons;

/**
 * ID生成器工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public  class Ids {

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String uuidNoDash() {
        return UuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String secureUuid() {
        return SecureUuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String secureUuidNoDash() {
        return SecureUuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回带有日期标识的随机ID
     *
     * @return id contains date
     */
    public static String dateId() {
        return DateIdGenerator.getInstance().get();
    }

    /**
     * 返回一个BASE64的ID
     *
     * @return id With BASE64
     */
    public static String base64Id() {
        return Base64IdGenerator.getInstance().get();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     */
    public static long snowflakeId() {
        return Singletons.get(FastSnowflake.class).nextId();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     */
    public static String snowflakeIdStr() {
        return String.valueOf(snowflakeId());
    }

}
