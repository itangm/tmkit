package cn.tmkit.core.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain IdCardUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
public class IdCardUtilTest {

    @Test
    public void isValid18() {
        String str = "";
        assertFalse(IdCardUtil.isValid18(str));
        str = "null";
        assertFalse(IdCardUtil.isValid18(str));
        str = "112";
        assertFalse(IdCardUtil.isValid18(str));
        str = "123123123123123123";
        assertFalse(IdCardUtil.isValid18(str));
        str = "112233445566778901";
        assertFalse(IdCardUtil.isValid18(str));
        str = "330108199001018039";
        assertTrue(IdCardUtil.isValid18(str));
        str = "330108199001018031";
        assertFalse(IdCardUtil.isValid18(str));
    }

    @Test
    public void testIsValid18() {
        String str = "33010819900101031X";
        assertTrue(IdCardUtil.isValid18(str, false));
        str = "33010819900101031x";
        assertFalse(IdCardUtil.isValid18(str, false));
    }

}
