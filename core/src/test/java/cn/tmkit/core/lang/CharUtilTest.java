package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain CharUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class CharUtilTest {

    @Test
    public void isAscii() {
        char ch = CharUtil.TAB;
        assertTrue(CharUtil.isAscii(ch));
        ch = '0';
        assertTrue(CharUtil.isAscii(ch));
        ch = ' ';
        assertTrue(CharUtil.isAscii(ch));
        ch = CharUtil.DOT;
        assertTrue(CharUtil.isAscii(ch));
        ch = CharUtil.COMMA;
        assertTrue(CharUtil.isAscii(ch));
        ch = CharUtil.UNDERLINE;
        assertTrue(CharUtil.isAscii(ch));
        ch = '。';
        assertFalse(CharUtil.isAscii(ch));
    }

    @Test
    public void isVisibleAscii() {
        assertTrue(CharUtil.isVisibleAscii(CharUtil.HYPHEN));
        assertFalse(CharUtil.isVisibleAscii(CharUtil.TAB));
        assertFalse(CharUtil.isVisibleAscii(CharUtil.CR));
        assertFalse(CharUtil.isVisibleAscii(CharUtil.LF));
        char ch = 255;
        assertFalse(CharUtil.isVisibleAscii(ch));
    }

    @Test
    public void isControlAscii() {
        char ch = 13;
        assertTrue(CharUtil.isControlAscii(ch));
        ch = 49;
        assertFalse(CharUtil.isControlAscii(ch));
    }

    @Test
    public void isLetter() {
        char ch = 'A';
        assertTrue(CharUtil.isLetter(ch));
        ch = '9';
        assertFalse(CharUtil.isLetter(ch));
        ch = '0';
        assertFalse(CharUtil.isLetter(ch));
    }

    @Test
    public void isLetterUpper() {
        char ch = 'A';
        assertTrue(CharUtil.isLetterUpper(ch));
        ch = '9';
        assertFalse(CharUtil.isLetterUpper(ch));
        ch = 'a';
        assertFalse(CharUtil.isLetterUpper(ch));
    }

    @Test
    public void isLetterLower() {
        char ch = 'A';
        assertFalse(CharUtil.isLetterLower(ch));
        ch = '9';
        assertFalse(CharUtil.isLetterLower(ch));
        ch = 'a';
        assertTrue(CharUtil.isLetterLower(ch));
    }

    @Test
    public void isNumber() {
        char ch = 'A';
        assertFalse(CharUtil.isNumber(ch));
        ch = '9';
        assertTrue(CharUtil.isNumber(ch));
        ch = 'a';
        assertFalse(CharUtil.isNumber(ch));
    }

    @Test
    public void isHexChar() {
        char ch = CharUtil.LEFT_CURLY_BRACKET;
        assertFalse(CharUtil.isHexChar(ch));
        ch = 'A';
        assertTrue(CharUtil.isHexChar(ch));
        ch = 'a';
        assertTrue(CharUtil.isHexChar(ch));
        ch = 'z';
        assertFalse(CharUtil.isHexChar(ch));
        ch = CharUtil.LEFT_MIDDLE_BRACKET;
        assertFalse(CharUtil.isHexChar(ch));
        ch = '2';
        assertTrue(CharUtil.isHexChar(ch));
        ch = '9';
        assertTrue(CharUtil.isHexChar(ch));
    }

    @Test
    public void isLetterOrNumber() {
        char ch = CharUtil.LEFT_CURLY_BRACKET;
        assertFalse(CharUtil.isLetterOrNumber(ch));
        ch = 'A';
        assertTrue(CharUtil.isLetterOrNumber(ch));
        ch = 'z';
        assertTrue(CharUtil.isLetterOrNumber(ch));
        ch = CharUtil.RIGHT_CURLY_BRACKET;
        assertFalse(CharUtil.isLetterOrNumber(ch));
        ch = '2';
        assertTrue(CharUtil.isLetterOrNumber(ch));
    }

    @Test
    public void isBlankChar() {
        char ch = CharUtil.SPACE;
        assertTrue(CharUtil.isBlankChar(ch));
        ch = CharUtil.TAB;
        assertTrue(CharUtil.isBlankChar(ch));
        ch = '0';
        assertFalse(CharUtil.isBlankChar(ch));
    }

    @Test
    public void testIsBlankChar() {
    }

    @Test
    public void isFileSeparator() {
        char ch = '/';
        assertTrue(CharUtil.isFileSeparator(ch));
        ch = '\\';
        assertTrue(CharUtil.isFileSeparator(ch));
        ch = '0';
        assertFalse(CharUtil.isFileSeparator(ch));
    }

    @Test
    public void isChar() {
        Integer i = 10;
        assertFalse(CharUtil.isChar(i));
        char ch = CharUtil.SINGLE_QUOTE;
        assertTrue(CharUtil.isChar(ch));

    }

    @Test
    public void testEquals() {
        char ch = '"';
        assertTrue(CharUtil.equals(ch, CharUtil.DOUBLE_QUOTE, false));
        ch = 'A';
        char c1 = 'a';
        assertFalse(CharUtil.equals(ch, c1, false));
        assertTrue(CharUtil.equals(ch, c1, true));
    }

    @Test
    public void testToString() {
        assertEquals("*", CharUtil.toString(CharUtil.STAR));
        assertEquals("&", CharUtil.toString(CharUtil.AMP));
        assertEquals("%", CharUtil.toString(CharUtil.PERCENT));
        assertEquals("@", CharUtil.toString(CharUtil.AT));
    }

}
