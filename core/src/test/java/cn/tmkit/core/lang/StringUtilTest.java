package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain StringUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class StringUtilTest {

    @Test
    public void isBlank() {
        assertTrue(Strings.isBlank(null));
        assertTrue(Strings.isBlank(""));
        assertTrue(Strings.isBlank(" "));
        assertTrue(Strings.isBlank("  "));
        assertTrue(Strings.isBlank("  \t"));
        assertTrue(Strings.isBlank("  \t\n"));
        assertTrue(Strings.isBlank("\n"));
        assertTrue(Strings.isBlank("\t"));
        assertFalse(Strings.isBlank("  \t\n."));
        assertFalse(Strings.isBlank("-"));
        assertFalse(Strings.isBlank("\\n"));
    }

    @Test
    public void isNotBlank() {
        assertFalse(Strings.isNotBlank(null));
        assertFalse(Strings.isNotBlank(""));
        assertFalse(Strings.isNotBlank(" "));
        assertFalse(Strings.isNotBlank("  "));
        assertFalse(Strings.isNotBlank("\t"));
        assertFalse(Strings.isNotBlank("\n"));
        assertFalse(Strings.isNotBlank("  \t"));
        assertFalse(Strings.isNotBlank("  \t\n"));
        assertTrue(Strings.isNotBlank("  \t\n."));
        assertTrue(Strings.isNotBlank("\\n"));
    }

    @Test
    public void isNotBlankInWebEnv() {
        assertFalse(Strings.isNotBlankInWeb(null));
        assertFalse(Strings.isNotBlankInWeb(""));
        assertFalse(Strings.isNotBlankInWeb(" "));
        assertFalse(Strings.isNotBlankInWeb("  "));
        assertFalse(Strings.isNotBlankInWeb("\t"));
        assertTrue(Strings.isNotBlankInWeb(" a "));
        assertFalse(Strings.isNotBlankInWeb(Strings.NULL));
        assertFalse(Strings.isNotBlankInWeb(Strings.UNDEFINED));
    }

    @Test
    public void isAnyBlank() {
        CharSequence[] csa = null;
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[0];
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[1];
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "-"};
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertTrue(Strings.isAnyBlank(csa));
        csa = new CharSequence[]{"-"};
        assertFalse(Strings.isAnyBlank(csa));
    }

    @Test
    public void isAllBlank() {
        CharSequence[] csa = null;
        assertTrue(Strings.isAllBlank(csa));
        csa = new CharSequence[0];
        assertTrue(Strings.isAllBlank(csa));
        csa = new CharSequence[1];
        assertTrue(Strings.isAllBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertTrue(Strings.isAllBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertTrue(Strings.isAllBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertFalse(Strings.isAllBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertFalse(Strings.isAllBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertFalse(Strings.isAllBlank(csa));
        csa = new CharSequence[]{"-"};
        assertFalse(Strings.isAllBlank(csa));
    }

    @Test
    public void isAnyNotBlank() {
        CharSequence[] csa = null;
        assertFalse(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[0];
        assertFalse(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[1];
        assertFalse(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertFalse(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertFalse(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertTrue(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertTrue(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertTrue(Strings.isAnyNotBlank(csa));
        csa = new CharSequence[]{"-"};
        assertTrue(Strings.isAnyNotBlank(csa));
    }

    @Test
    public void testIsAnyNotBlank() {
        List<CharSequence> list = null;
        assertFalse(Strings.isAnyNotBlank(list));
        list = new ArrayList<>();
        assertFalse(Strings.isAnyNotBlank(list));
        list = new ArrayList<>(10);
        assertFalse(Strings.isAnyNotBlank(list));
        list = new ArrayList<>();
        list.add(null);
        assertFalse(Strings.isAnyNotBlank(list));
        list.clear();
        list.add("");
        list.add(" ");
        list.add("\t");
        list.add("\n");
        list.add("\r");
        list.add("\n");
        list.add("\r\n");
        assertFalse(Strings.isAnyNotBlank(list));
        list.add("-");
        assertTrue(Strings.isAnyNotBlank(list));
    }

    @Test
    public void isEmpty() {
        assertTrue(Strings.isEmpty(null));
        assertTrue(Strings.isEmpty(""));
        assertFalse(Strings.isEmpty(" "));
        assertFalse(Strings.isEmpty("\t"));
        assertFalse(Strings.isEmpty("\n"));
        assertFalse(Strings.isEmpty("-"));
    }

    @Test
    void isAnyEmpty() {
        CharSequence[] csa = null;
        assertTrue(Strings.isAnyEmpty(csa));
        csa = new CharSequence[0];
        assertTrue(Strings.isAnyEmpty(csa));
        csa = new CharSequence[1];
        assertTrue(Strings.isAnyEmpty(csa));
        csa = new CharSequence[]{"", "", null};
        assertTrue(Strings.isAnyEmpty(csa));
        csa = new CharSequence[]{" ", "", null};
        assertTrue(Strings.isAnyEmpty(csa));
        csa = new CharSequence[]{" ", "  "};
        assertFalse(Strings.isAnyEmpty(csa));
    }

    @Test
    public void isAllEmpty() {
        CharSequence[] csa = null;
        assertTrue(Strings.isAllEmpty(csa));
        csa = new CharSequence[0];
        assertTrue(Strings.isAllEmpty(csa));
        csa = new CharSequence[1];
        assertTrue(Strings.isAllEmpty(csa));
        csa = new CharSequence[]{"", "", null};
        assertTrue(Strings.isAllEmpty(csa));
        csa = new CharSequence[]{" ", "", null};
        assertFalse(Strings.isAllEmpty(csa));
    }

    @Test
    public void isNotEmpty() {

    }

    @Test
    public void hasLength() {

    }

    @Test
    public void hasText() {
        assertFalse(Strings.hasText(null));
        assertFalse(Strings.hasText(""));
        assertFalse(Strings.hasText(" "));
        assertFalse(Strings.hasText("  "));
        assertFalse(Strings.hasText("\t"));
        assertFalse(Strings.hasText("\n"));
        assertFalse(Strings.hasText("  \t"));
        assertFalse(Strings.hasText("  \t\n"));
        assertTrue(Strings.hasText("  \t\n."));
        assertTrue(Strings.hasText("\\n"));
    }

    @Test
    public void isAllNotBlank() {
        CharSequence[] csa = null;
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[0];
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[1];
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertFalse(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{"-"};
        assertTrue(Strings.isAllNotBlank(csa));
        csa = new CharSequence[]{"\\t", "-"};
        assertTrue(Strings.isAllNotBlank(csa));
    }

    @Test
    public void testIsAllNotBlank() {
        List<CharSequence> list = null;
        assertFalse(Strings.isAllNotBlank(list));
        list = new ArrayList<>();
        assertFalse(Strings.isAllNotBlank(list));
        list = new ArrayList<>(10);
        assertFalse(Strings.isAllNotBlank(list));
        list = new ArrayList<>();
        list.add(null);
        assertFalse(Strings.isAllNotBlank(list));
        list.clear();
        list.add("");
        list.add(" ");
        list.add("\t");
        list.add("\n");
        list.add("\r");
        list.add("\n");
        list.add("\r\n");
        assertFalse(Strings.isAllNotBlank(list));
        list.add("-");
        assertFalse(Strings.isAllNotBlank(list));
        list.clear();
        list.add("-");
        list.add("0");
        assertTrue(Strings.isAllNotBlank(list));
    }

    @Test
    public void isNoneEmpty() {

    }

    @Test
    public void containsBlank() {

    }

    @Test
    public void containsWhitespace() {
        CharSequence cs = null;
        assertFalse(Strings.containsWhitespace(cs));
        cs = "";
        assertFalse(Strings.containsWhitespace(cs));
        cs = "author";
        assertFalse(Strings.containsWhitespace(cs));
        cs = " author ";
        assertTrue(Strings.containsWhitespace(cs));
        cs = "hello world";
        assertTrue(Strings.containsWhitespace(cs));
    }

    @Test
    public void getIfEmpty() {
        String str = Strings.getIfEmpty(null, null);
        assertNull(str);
        str = Strings.getIfEmpty(null, Strings.NULL);
        assertEquals(Strings.NULL, str);
        str = Strings.getIfEmpty("", Strings.NULL);
        assertEquals(Strings.NULL, str);
        str = Strings.getIfEmpty(" ", Strings.NULL);
        assertEquals(" ", str);
        str = Strings.getIfEmpty("\\w", Strings.NULL);
        assertEquals("\\w", str);
    }

    @Test
    public void getIfBlank() {
        String str = Strings.getIfBlank(null, null);
        assertNull(str);
        str = Strings.getIfBlank(null, Strings.NULL);
        assertEquals(Strings.NULL, str);
        str = Strings.getIfBlank("", Strings.NULL);
        assertEquals(Strings.NULL, str);
        str = Strings.getIfBlank(" ", Strings.NULL);
        assertEquals(Strings.NULL, str);
        str = Strings.getIfBlank("\\w", Strings.NULL);
        assertEquals("\\w", str);
        str = Strings.getIfBlank("\t\r", Strings.NULL);
        assertEquals(Strings.NULL, str);

    }

    @Test
    public void trimLeft() {
        CharSequence cs = null;
        assertNull(Strings.trimLeft(cs));
        cs = "";
        assertEquals(cs, Strings.trimLeft(cs));
        cs = "\t\t\t\t";
        assertEquals(Strings.EMPTY_STRING, Strings.trimLeft(cs));
        cs = "\na";
        assertEquals("a", Strings.trimLeft(cs));
        cs = "a\r";
        assertEquals(cs, Strings.trimLeft(cs));

    }

    @Test
    public void trimRight() {
        CharSequence cs = null;
        assertNull(Strings.trimRight(cs));
        cs = "";
        assertEquals(cs, Strings.trimRight(cs));
        cs = "\t\t\t\t";
        assertEquals(Strings.EMPTY_STRING, Strings.trimRight(cs));
        cs = "\na";
        assertEquals(cs, Strings.trimRight(cs));
        cs = "a\r";
        assertEquals("a", Strings.trimRight(cs));
        cs = "\ba\r";
        assertEquals("\ba", Strings.trimRight(cs));

    }

    @Test
    public void testTrim() {
        CharSequence[] array = null;
        assertArrayEquals(Arrays.EMPTY_STRING_ARRAY, Strings.trim(array));
        array = new CharSequence[0];
        assertArrayEquals(Arrays.EMPTY_STRING_ARRAY, Strings.trim(array));
        array = new CharSequence[2];
        assertArrayEquals(new String[]{null, null}, Strings.trim(array));
        array = new CharSequence[]{"", " ", " \t", "\r\n\t"};
        assertArrayEquals(new String[]{"", "", "", ""}, Strings.trim(array));
        array = new CharSequence[]{"", " ", "\t", "\r author: miles \n"};
        assertArrayEquals(new String[]{"", "", "", "author: miles"}, Strings.trim(array));

    }

    @Test
    public void trimToList() {
        List<CharSequence> array = null;
        assertEquals(Collections.emptyList(), Strings.trim(array));
        array = new ArrayList<>();
        assertEquals(Collections.emptyList(), Strings.trim(array));
        array.add("");
        array.add(" ");
        array.add("\t\tauthor: miles\r");
        List<CharSequence> expected = java.util.Arrays.asList("", "", "author: miles");
        assertEquals(expected, Strings.trim(array));
    }

    @Test
    public void trimAllWhitespace() {
        CharSequence cs = null;
        assertNull(Strings.trimAllWhitespace(cs));
        cs = "";
        assertEquals(Strings.EMPTY_STRING, Strings.trimAllWhitespace(cs));
        cs = " \t \r \n ";
        assertEquals(Strings.EMPTY_STRING, Strings.trimAllWhitespace(cs));
        cs = "miles tang";
        assertEquals("milestang", Strings.trimAllWhitespace(cs));

    }

    @Test
    public void endsWithIgnoreCase() {
        CharSequence cse = null;
        CharSequence suffix = null;
        assertFalse(Strings.endsWithIgnoreCase(cse, suffix));
        cse = "";
        assertFalse(Strings.endsWithIgnoreCase(cse, suffix));
        suffix = "";
        assertTrue(Strings.endsWithIgnoreCase(cse, suffix));
        cse = "miles tang";
        assertTrue(Strings.endsWithIgnoreCase(cse, suffix));
        suffix = "G";
        assertTrue(Strings.endsWithIgnoreCase(cse, suffix));
        cse = "author: miles.tang\n";
        suffix = "\n";
        assertTrue(Strings.endsWithIgnoreCase(cse, suffix));
    }

    @Test
    public void testEquals() {
        CharSequence str1 = null, str2 = null;
        assertTrue(Strings.equals(str1, str2));
        str1 = "";
        assertFalse(Strings.equals(str1, str2));
        str2 = "";
        assertTrue(Strings.equals(str1, str2));
        str1 = "author";
        assertFalse(Strings.equals(str1, str2));
        str2 = "author";
        assertTrue(Strings.equals(str1, str2));
        str2 = "Author";
        assertFalse(Strings.equals(str1, str2));
        str1 = "\t";
        str2 = "\t";
        assertTrue(Strings.equals(str1, str2));

    }

    @Test
    public void equalsIgnoreCase() {
        CharSequence str1 = null, str2 = null;
        assertTrue(Strings.equalsIgnoreCase(str1, str2));
        str1 = "";
        assertFalse(Strings.equalsIgnoreCase(str1, str2));
        str2 = "";
        assertTrue(Strings.equalsIgnoreCase(str1, str2));
        str1 = "author";
        assertFalse(Strings.equalsIgnoreCase(str1, str2));
        str2 = "author";
        assertTrue(Strings.equalsIgnoreCase(str1, str2));
        str2 = "Author";
        assertTrue(Strings.equalsIgnoreCase(str1, str2));
        str1 = "\t";
        str2 = "\t";
        assertTrue(Strings.equalsIgnoreCase(str1, str2));

    }

    @Test
    public void containsAll() {
        CharSequence cse = null;
        char[] matchChars = null;
        assertFalse(Strings.containsAll(cse, matchChars));
        cse = "";
        assertFalse(Strings.containsAll(cse, matchChars));
        // 空字符串不等价于 \u0000
        matchChars = new char[]{'\0', '\0'};
        assertFalse(Strings.containsAll(cse, matchChars));
        cse = "miles ";
        matchChars = new char[]{' '};
        assertTrue(Strings.containsAll(cse, matchChars));
        matchChars = new char[]{' ', 'i'};
        assertTrue(Strings.containsAll(cse, matchChars));
        matchChars = new char[]{' ', 'a'};
        assertFalse(Strings.containsAll(cse, matchChars));
        cse = "author\n\tmiles";
        matchChars = new char[]{'\n', '\t'};
        assertTrue(Strings.containsAll(cse, matchChars));

    }

    @Test
    public void testContains() {
        CharSequence str = null;
        CharSequence[] array = null;
        assertFalse(Strings.containsAny(str, array));
        array = new CharSequence[0];
        assertFalse(Strings.containsAny(str, array));
        str = "";
        assertFalse(Strings.containsAny(str, array));
        array = new CharSequence[]{""};
        assertTrue(Strings.containsAny(str, array));
        str = "author:miles;\nlocation:hangzhou";
        assertFalse(Strings.containsAny(str, array));
        str = "loc";
        array = new CharSequence[]{"\n", "location"};
        assertTrue(Strings.containsAny(str, array));
        str = "\n";
        array = new CharSequence[]{" ", "\n"};
        assertTrue(Strings.containsAny(str, array));
        str = "au";
        array = new CharSequence[]{"AUTHOR"};
        assertFalse(Strings.containsAny(str, array));

    }

    @Test
    public void containsIgnoreCase() {
        CharSequence str = null;
        CharSequence[] array = null;
        assertFalse(Strings.containsAnyIgnoreCase(str, array));
        array = new CharSequence[0];
        assertFalse(Strings.containsAnyIgnoreCase(str, array));
        str = "";
        assertFalse(Strings.containsAnyIgnoreCase(str, array));
        array = new CharSequence[]{""};
        assertTrue(Strings.containsAnyIgnoreCase(str, array));
        str = "author";
        assertFalse(Strings.containsAnyIgnoreCase(str, array));
        array = new CharSequence[]{"\n", "location"};
        assertFalse(Strings.containsAnyIgnoreCase(str, array));
        str = "\n";
        array = new CharSequence[]{"", "\n"};
        assertTrue(Strings.containsAnyIgnoreCase(str, array));
        str = " ";
        array = new CharSequence[]{" ", "\n"};
        assertTrue(Strings.containsAnyIgnoreCase(str, array));
        str = "author";
        array = new CharSequence[]{"AUTHOR"};
        assertTrue(Strings.containsAnyIgnoreCase(str, array));

    }

    @Test
    public void containsAllIgnoreCase() {
        CharSequence cse = null;
        CharSequence[] array = null;
        assertFalse(Strings.containsAllIgnoreCase(cse, array));
        array = new CharSequence[0];
        assertFalse(Strings.containsAllIgnoreCase(cse, array));
        cse = "ta";
        assertFalse(Strings.containsAllIgnoreCase(cse, array));
        array = new CharSequence[]{"Ta", "ata"};
        assertTrue(Strings.containsAllIgnoreCase(cse, array));
    }

    @Test
    public void hideLength() {
        CharSequence cse = null;
        int startInclude = 0;
        int length = 4;
        assertNull(Strings.hideLength(cse, startInclude, length));
        cse = "";
        assertEquals(Strings.EMPTY_STRING, Strings.hideLength(cse, startInclude, length));
        cse = "          ";
        assertEquals("****      ", Strings.hideLength(cse, startInclude, length));
        cse = "  ";
        assertEquals("**", Strings.hideLength(cse, startInclude, length));

    }

    @Test
    public void delete() {
        CharSequence cse = null;
        CharSequence deleteStr = null;
        assertNull(Strings.delete(cse, deleteStr));
        deleteStr = "a";
        assertNull(Strings.delete(cse, deleteStr));
        cse = "";
        assertEquals("", Strings.delete(cse, deleteStr));
        cse = "thank";
        assertEquals("thnk", Strings.delete(cse, deleteStr));
        deleteStr = "A";
        assertEquals("thank", Strings.delete(cse, deleteStr));
        deleteStr = "\\w";
        assertEquals("thank", Strings.delete(cse, deleteStr));

    }

    @Test
    void testDelete() {
        CharSequence cse = null;
        int start = 0, end = 0;
        assertNull(Strings.delete(cse, start, end));
        cse = "";
        assertEquals("", Strings.delete(cse, start, end));
        cse = "AaBbCc";
        assertEquals("aBbCc", Strings.delete(cse, start, end));
        end = -1;
        assertEquals("", Strings.delete(cse, start, end));
        start = 10;
        assertEquals(cse.toString(), Strings.delete(cse, start, end));
        end = -20;
        assertEquals(cse.toString(), Strings.delete(cse, start, end));
    }

    @Test
    public void deletePrefixIgnoreCase() {
        CharSequence cse = null;
        CharSequence prefix = null;
        assertNull(Strings.deletePrefixIgnoreCase(cse, prefix));
        cse = "author";
        assertEquals(cse, Strings.deletePrefixIgnoreCase(cse, prefix));
        prefix = "az";
        assertEquals(cse, Strings.deletePrefixIgnoreCase(cse, prefix));
        prefix = "\\w";
        assertEquals(cse, Strings.deletePrefixIgnoreCase(cse, prefix));
        prefix = "author";
        assertEquals("", Strings.deletePrefixIgnoreCase(cse, prefix));
        prefix = "AUTHOR";
        assertEquals("", Strings.deletePrefixIgnoreCase(cse, prefix));

    }

    @Test
    public void deletePrefix() {
        CharSequence cse = null;
        CharSequence prefix = null;
        assertNull(Strings.deletePrefix(cse, prefix));
        cse = "author: miles";
        assertEquals(cse, Strings.deletePrefix(cse, prefix));
        prefix = "";
        assertEquals(cse, Strings.deletePrefix(cse, prefix));
        prefix = "auth";
        assertEquals("or: miles", Strings.deletePrefix(cse, prefix));
        prefix = "AUTH";
        assertEquals(cse, Strings.deletePrefix(cse, prefix));
        cse = "\ta\ruthor";
        prefix = "\t";
        assertEquals("a\ruthor", Strings.deletePrefix(cse, prefix));

    }

    @Test
    public void deleteSuffix() {
        CharSequence cse = null;
        CharSequence suffix = null;
        assertNull(Strings.deleteSuffix(cse, suffix));
        cse = "author: miles";
        assertEquals(cse, Strings.deleteSuffix(cse, suffix));
        suffix = "";
        assertEquals(cse, Strings.deleteSuffix(cse, suffix));
        suffix = "auth";
        assertEquals("author: miles", Strings.deleteSuffix(cse, suffix));
        suffix = "les";
        assertEquals("author: mi", Strings.deleteSuffix(cse, suffix));
        suffix = "AUTH";
        assertEquals(cse, Strings.deleteSuffix(cse, suffix));
        cse = "\ta\ruthor";
        suffix = "\t";
        assertEquals("\ta\ruthor", Strings.deleteSuffix(cse, suffix));
        cse = "\ta\ruthor\t";
        assertEquals("\ta\ruthor", Strings.deleteSuffix(cse, suffix));
    }

    @Test
    public void capitalize() {
        CharSequence cse = null;
        assertNull(Strings.capitalize(cse));
        cse = "";
        assertEquals(cse, Strings.capitalize(cse));
        cse = "\tau";
        assertEquals(cse, Strings.capitalize(cse));
        cse = "author";
        assertEquals("Author", Strings.capitalize(cse));
        cse = "An";
        assertEquals(cse, Strings.capitalize(cse));
    }

    @Test
    public void unCapitalize() {
        CharSequence cse = null;
        assertNull(Strings.unCapitalize(cse));
        cse = "";
        assertEquals(cse, Strings.unCapitalize(cse));
        cse = "\tau";
        assertEquals(cse, Strings.unCapitalize(cse));
        cse = "author";
        assertEquals("author", Strings.unCapitalize(cse));
        cse = "An";
        assertEquals("an", Strings.unCapitalize(cse));

    }

    @Test
    public void changeCharacterCase() {
    }

    @Test
    public void camelCaseToUnderline() {
        CharSequence cse = null;
        assertNull(StrUtil.camelCaseToUnderline(cse));
        cse = "";
        assertEquals(cse, StrUtil.camelCaseToUnderline(cse));
        cse = "USA";
        assertEquals("usa",StringUtil.camelCaseToUnderline(cse));
    }

}
