package cn.tmkit.core.digest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tester for {@linkplain MD5Util}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
public class MD5UtilTest {

    @Test
    public void digestHex() {
        String str = "123456";
        String hex = MD5Util.digestHex(str);
        assertNotNull(hex);
        assertEquals("e10adc3949ba59abbe56e057f20f883e", hex);

    }

    @Test
    public void digestUpperHex() {
        String str = "miles";
        String hex = MD5Util.digestUpperHex(str);
        assertNotNull(hex);
        assertEquals("6A81060B83B919BC115112BF840ECA63", hex);
    }

    @Test
    public void testDigestHex() {

    }

    @Test
    public void testDigestUpperHex() {

    }

    @Test
    void testDigestHex1() {
    }

    @Test
    void digestBase64() {
    }

    @Test
    void testDigestBase64() {
    }
}
