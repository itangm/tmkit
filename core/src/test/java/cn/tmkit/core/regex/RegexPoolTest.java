package cn.tmkit.core.regex;

import cn.tmkit.core.lang.PatternUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain RegexPool}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class RegexPoolTest {

    @Test
    public void hex() {
        String hexStr = "e98008278401114d6e1ea10afa4abb57";
        assertTrue(PatternUtil.isMatch(PatternPool.HEX_STR, hexStr));
        hexStr = "ming.tang";
        assertFalse(PatternUtil.isMatch(PatternPool.HEX, hexStr));
    }

    @Test
    public void general() {
        String str = "Aa123_";
        assertTrue(PatternUtil.isMatch(PatternPool.GENERAL, str));
        str += "-";
        assertFalse(PatternUtil.isMatch(PatternPool.GENERAL_STR, str));
    }

    @Test
    public void numbers() {
        String str = "12";
        assertTrue(PatternUtil.isMatch(PatternPool.NUMBERS, str));
        str += "-";
        assertFalse(PatternUtil.isMatch(PatternPool.NUMBERS_STR, str));
    }

    @Test
    public void birthday() {
        String str = "2023年01月05日";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY, str));
        str = "2023年01月5日";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
        str = "2023年1月5日";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
        str = "2023年1月05日";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
        str = "2023/01/5";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
        str = "2023/01/05";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
        str = "2023-01-05";
        assertTrue(PatternUtil.isMatch(PatternPool.BIRTHDAY_STR, str));
    }

    @Test
    public void time() {
        String str = "09:00";
        assertTrue(PatternUtil.isMatch(PatternPool.TIME, str));
        str = "09:00:00";
        assertTrue(PatternUtil.isMatch(PatternPool.TIME_STR, str));
    }

    @Test
    public void zipCode() {
        String str = "311000";
        assertTrue(PatternUtil.isMatch(PatternPool.ZIPCODE, str));
        str = "311000:00";
        assertFalse(PatternUtil.isMatch(PatternPool.ZIPCODE_STR, str));
    }

}
