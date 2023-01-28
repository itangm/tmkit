package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain IPUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-28
 */
public class IPUtilTest {

    @Test
    public void isIPv4() {
        String str = "";
        assertFalse(IPUtil.isIPv4(str));
        str = "1";
        assertFalse(IPUtil.isIPv4(str));
        str = "123";
        assertFalse(IPUtil.isIPv4(str));
        str = "1.1";
        assertFalse(IPUtil.isIPv4(str));
        str = "1.1.1.1";
        assertTrue(IPUtil.isIPv4(str));
        str = "0.0.0.0";
        assertTrue(IPUtil.isIPv4(str));
        str = "255.255.255.255";
        assertTrue(IPUtil.isIPv4(str));
        str = "122.220.255.256";
        assertFalse(IPUtil.isIPv4(str));
    }

    @Test
    public void isIPv6() {
        String str = "::";
        assertTrue(IPUtil.isIPv6(str));
        str = "::1";
        assertTrue(IPUtil.isIPv6(str));

    }

}
