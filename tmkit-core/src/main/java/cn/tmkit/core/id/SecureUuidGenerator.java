package cn.tmkit.core.id;

import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.lang.reflect.Singletons;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 较为安全的UUID，采用{@linkplain SecureRandom}获取更加安全的随机码，当然其性能相对慢一些。
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class SecureUuidGenerator extends UuidGenerator {

    public SecureUuidGenerator() {
        super();
    }

    public SecureUuidGenerator(boolean ignoreDash) {
        super(ignoreDash);
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        final byte[] randomBytes = new byte[16];
        Singletons.get(SecureRandom.class).nextBytes(randomBytes);
        String id = UUID.nameUUIDFromBytes(randomBytes).toString();
        return ignoreDash ? Strings.replace(id, Strings.DASH, Strings.EMPTY_STRING) : id;
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstance() {
        return Singletons.get(SecureUuidGenerator.class);
    }

    /**
     * 返回一个不带-的默认实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstanceNoDash() {
        return Singletons.get(SecureUuidGenerator.class, true);
    }

}
