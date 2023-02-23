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
        assertFalse(IdCardUtil.isValid(str));
        str = "null";
        assertFalse(IdCardUtil.isValid(str));
        str = "112";
        assertFalse(IdCardUtil.isValid(str));
        str = "123123123123123123";
        assertFalse(IdCardUtil.isValid(str));
        str = "112233445566778901";
        assertFalse(IdCardUtil.isValid(str));
        str = "330108199001018039";
        assertTrue(IdCardUtil.isValid(str));
        str = "330108199001018031";
        assertFalse(IdCardUtil.isValid(str));
    }

    @Test
    public void testIsValid18() {
//        String str = "33010819900101031X";
        String str = "130101201602018331";
//        String str = "13010119901010245X";
        assertTrue(IdCardUtil.isValid(str, false));
        str = "33010819900101031x";
        assertFalse(IdCardUtil.isValid(str, false));
    }

}
