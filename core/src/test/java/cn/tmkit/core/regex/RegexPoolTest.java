package cn.tmkit.core.regex;

import cn.tmkit.core.lang.PatternUtil;
import cn.tmkit.core.lang.RegexUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void digits() {
        String str = "";
        assertFalse(PatternUtil.isMatch(PatternPool.DIGITS, str));
        str += "0";
        assertTrue(PatternUtil.isMatch(PatternPool.DIGITS, str));
        str = "1.0";
        assertFalse(PatternUtil.isMatch(PatternPool.DIGITS, str));
    }

    @Test
    public void money() {
        String str = "11";
        assertTrue(PatternUtil.isMatch(PatternPool.MONEY, str));
        str = "11000000";
        assertTrue(PatternUtil.isMatch(PatternPool.MONEY, str));
        str = "11.22";
        assertTrue(PatternUtil.isMatch(PatternPool.MONEY, str));
        str = "0.223";
        assertFalse(PatternUtil.isMatch(PatternPool.MONEY, str));
    }

    @Test
    public void chineseAny() {
        String str = "好";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "好的";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "00";
        assertFalse(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "0好0";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "author";
        assertFalse(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "好OK";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
        str = "oK的";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ANY, str));
    }

    @Test
    public void chinese() {
        String str = "好的";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "中国:00";
        assertFalse(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "00";
        assertFalse(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "《";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "》";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "、";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "？";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "，";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "。";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "；";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "：";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "‘";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "“";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "【";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "】";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "、";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "|";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "——";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "）";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "（";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "……";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "￥";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
        str = "！";
        assertTrue(PatternUtil.isMatch(PatternConstant.CHINESE_ONLY, str));
    }

    @Test
    public void urlOrHttpOrHttpsOrFtpOrSftp() {
        String str = "";
        assertFalse(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "tmkit";
        assertFalse(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "tmkit.cn";
        assertTrue(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "http://tmkit.cn";
        assertTrue(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "https://tmkit.cn";
        assertTrue(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "ftp://tmkit.cn";
        assertTrue(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
        str = "sftp://tmkit.cn";
        assertTrue(PatternUtil.isMatch(PatternPool.URL_HTTP_OR_FTP, str));
    }

    @Test
    public void imei() {
        String str = "";
        assertFalse(PatternUtil.isMatch(RegexPool.IMEI, str));
        str = "imei";
        assertFalse(PatternUtil.isMatch(RegexPool.IMEI, str));
        str = "862807051330749";
        assertTrue(PatternUtil.isMatch(RegexPool.IMEI, str));
    }

    @Test
    public void mobilePhoneCompatible() {
        String str = "";
        assertFalse(PatternUtil.isMatch(PatternPool.MOBILE_PHONE_COMPATIBLE, str));
        str = "13888888888888888888888888";
        assertFalse(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE_COMPATIBLE, str));
        str = "23456789123";
        assertFalse(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE_COMPATIBLE, str));
        str = "11111111111";
        assertFalse(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE_COMPATIBLE, str));

    }

    @Test
    public void mobilePhone() {
        String str = "13888888888";
        assertTrue(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE, str));
        str = "13888888888888888888888888";
        assertFalse(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE, str));
        str = "23456789123";
        assertFalse(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE, str));
        str = "11111111111";
        assertTrue(PatternUtil.isMatch(PatternConstant.MOBILE_PHONE, str));
    }

    @Test
    public void date() {
        String str = "2020-01-01";
        assertTrue(PatternUtil.isMatch(PatternConstant.DATE, str));
        str = "2020-01-01_0";
        assertFalse(PatternUtil.isMatch(PatternConstant.DATE, str));
    }

    @Test
    public void email() {
        String str = "a@a.com";
        assertTrue(PatternUtil.isMatch(PatternConstant.EMAIL, str));
        str = "a@a";
        assertFalse(PatternUtil.isMatch(PatternConstant.EMAIL, str));
    }

    @Test
    public void idCardNumber15() {
        String str = "1";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER_15, str));
        str = "123";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER_15, str));
        str = "130503670401001";
        assertTrue(RegexUtil.isMatch(PatternPool.ID_CARD_NUMBER_15, str));
    }

    @Test
    public void idCardNumber18() {
        String str = "1";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER_18, str));
        str = "123";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER_18, str));
        str = "110101199003077539";
        assertTrue(RegexUtil.isMatch(PatternPool.ID_CARD_NUMBER_18, str));
    }

    @Test
    public void idCardNumber() {
        String str = "1";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER, str));
        str = "123";
        assertFalse(PatternUtil.isMatch(PatternConstant.ID_CARD_NUMBER, str));
        str = "110101199003077539";
        assertTrue(RegexUtil.isMatch(PatternPool.ID_CARD_NUMBER_STR, str));
    }

    @Test
    public void time() {
        String str = "09:00";
        assertTrue(PatternUtil.isMatch(PatternPool.TIME, str));
        str = "09:00:00";
        assertTrue(PatternUtil.isMatch(PatternPool.TIME_STR, str));
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
    public void longitude() {
        String str = "39.984154";
        assertTrue(PatternUtil.isMatch(RegexConstant.LONGITUDE, str));
        str = "116.307490";
        assertTrue(PatternUtil.isMatch(RegexConstant.LONGITUDE, str));
    }

    @Test
    public void latitude() {
        String str = "39.984154";
        assertTrue(PatternUtil.isMatch(RegexConstant.LATITUDE, str));
        str = "116.307490";
        assertFalse(PatternUtil.isMatch(RegexConstant.LATITUDE, str));
    }

    @Test
    public void zipCode() {
        String str = "311000";
        assertTrue(PatternUtil.isMatch(ExtraPatternConstant.ZIPCODE, str));
        str = "311000:00";
        assertFalse(PatternUtil.isMatch(ExtraPatternConstant.ZIPCODE, str));
    }

}
