package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain CollectionUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class CollectionUtilTest {

    @Test
    public void isEmpty() {
        Set<String> sets = new HashSet<>();
        assertTrue(CollectionUtil.isEmpty(sets));
        sets.add(null);
        assertFalse(CollectionUtil.isEmpty(sets));
        sets.add("1");
        assertFalse(CollectionUtil.isEmpty(sets));
    }

    @Test
    public void isNotEmpty() {
        Set<String> sets = new HashSet<>();
        assertFalse(CollectionUtil.isNotEmpty(sets));
        sets.add(null);
        assertTrue(CollectionUtil.isNotEmpty(sets));
    }

    @Test
    public void join() {
        List<String> list = null;
        assertNull(Collections.join(list));
        list = new ArrayList<>();
        assertEquals("", CollectionUtil.join(list));
        list.add(null);
        assertEquals("", CollectionUtil.join(list));
        list.add("A");
        assertEquals("A", CollectionUtil.join(list));
        list.add("Op");
        assertEquals("A,Op", CollectionUtil.join(list));
    }

    @Test
    public void list() {
        assertTrue(CollectionUtil.list(false) instanceof ArrayList);
        assertTrue(CollectionUtil.list(true) instanceof LinkedList);
        String[] array = new String[]{"hello", "world"};
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        assertEquals(list, CollectionUtil.list(false, array));
    }

    @Test
    public void sort() {
        List<String> list = new ArrayList<>();
        list.add("hi");
        list.add("world");
        list.add("miles");
        list.add("author");

        CollectionUtil.sort(list);
        List<String> expected = new ArrayList<>();
        expected.add("author");
        expected.add("hi");
        expected.add("miles");
        expected.add("world");
        assertEquals(expected, list);

        list = new ArrayList<>();
        list.add("hi");
        list.add("world");
        list.add("miles");
        list.add("author");
        Collections.sort(list, Comparator.comparingInt(String::length));
        expected = new ArrayList<>();
        expected.add("hi");
        expected.add("miles");
        expected.add("world");
        expected.add("author");
        assertEquals(expected, list);
    }

    @Test
    public void testJoin() {
        String separator = "|";
        List<String> list = new ArrayList<>();
        list.add("A");
        assertEquals("A", Collections.join(list, separator));
        list.add("Op");
        assertEquals("A|Op", Collections.join(list, separator));
    }

    @Test
    public void testJoin1() {
        List<String> sets = new ArrayList<>();
        sets.add("fAST");
        String separator = "^";
        boolean ignoreNull = false, sortable = true;
        assertEquals("fAST", Collections.join(sets, separator, ignoreNull, sortable));
        sets.add("Author");
        assertEquals("Author^fAST", Collections.join(sets, separator, ignoreNull, sortable));
        sortable = false;
        assertEquals("fAST^Author", Collections.join(sets, separator, ignoreNull, sortable));
    }

    @Test
    void testJoin2() {
    }

    @Test
    void testJoin3() {
    }

    @Test
    public void addAll() {

    }

    @Test
    public void mergeToColl() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String[] elements = {"A", "B"};

        Collection<String> expected = java.util.Arrays.asList("A", "B", "C", "A", "B");
        assertEquals(expected, Collections.merge(list, elements));

        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        expected = new HashSet<>();
        expected.add("A");
        expected.add("C");
        expected.add("B");
        assertEquals(expected, set);

    }

}
