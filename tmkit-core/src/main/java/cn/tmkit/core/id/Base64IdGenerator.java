package cn.tmkit.core.id;

import cn.tmkit.core.codec.Base64s;
import cn.tmkit.core.lang.reflect.Singletons;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 参考实现 <a href="https://github.com/elastic/elasticsearch/tree/master/server/src/main/java/org/elasticsearch/common">elasticsearch</a>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class Base64IdGenerator implements IdGenerator<String> {

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return getBase64Uuid(Singletons.get(SecureRandom.class));
    }

    /**
     * Returns a Base64 encoded version of a Version 4.0 compatible UUID
     * randomly initialized by the given {@link Random} instance
     * as defined here: http://www.ietf.org/rfc/rfc4122.txt
     */
    public String getBase64Uuid(Random random) {
        return Base64s.encode(getUuidBytes(random), true);
    }

    private byte[] getUuidBytes(Random random) {
        final byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        /* Set the version to version 4 (see http://www.ietf.org/rfc/rfc4122.txt)
         * The randomly or pseudo-randomly generated version.
         * The version number is in the most significant 4 bits of the time
         * stamp (bits 4 through 7 of the time_hi_and_version field).*/
        randomBytes[6] &= 0x0f;  /* clear the 4 most significant bits for the version  */
        randomBytes[6] |= 0x40;  /* set the version to 0100 / 0x40 */

        /* Set the variant:
         * The high field of th clock sequence multiplexed with the variant.
         * We set only the MSB of the variant*/
        randomBytes[8] &= 0x3f;  /* clear the 2 most significant bits */
        randomBytes[8] |= 0x80;  /* set the variant (MSB is set)*/
        return randomBytes;
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static Base64IdGenerator getInstance() {
        return Singletons.get(Base64IdGenerator.class);
    }

}
