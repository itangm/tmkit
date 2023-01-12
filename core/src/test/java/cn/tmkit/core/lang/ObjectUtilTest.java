package cn.tmkit.core.lang;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain ObjectUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class ObjectUtilTest {

    @Test
    public void isEmpty() {
    }

    @Test
    public void isNotEmpty() {
        Object obj = null;
        assertFalse(Objects.isNotEmpty(obj));
        obj = "";
        assertFalse(Objects.isNotEmpty(obj));
        obj = 1;
        assertTrue(Objects.isNotEmpty(obj));
        obj = " ";
        assertTrue(Objects.isNotEmpty(obj));
        obj = "\n";
        assertTrue(Objects.isNotEmpty(obj));
        obj = new int[0];
        assertFalse(Objects.isNotEmpty(obj));
        obj = new Integer[2];
        assertTrue(Objects.isNotEmpty(obj)); // 数组不为空就代表对象不为空
        obj = new String[]{null, null};
        assertTrue(Objects.isNotEmpty(obj));

    }

    @Test
    public void isAnyEmpty() {
        Object[] array = null;
        assertTrue(Objects.isAnyEmpty(array));
        array = new Object[0];
        assertTrue(Objects.isAnyEmpty(array));
        array = new Object[2];
        assertTrue(Objects.isAnyEmpty(array));
        array = new Object[]{null, "1"};
        assertTrue(Objects.isAnyEmpty(array));
        array = new Object[]{"", 1};
        assertTrue(Objects.isAnyEmpty(array));
        array = new Object[]{"1", 0};
        assertFalse(Objects.isAnyEmpty(array));
    }

    @Test
    public void isNull() {
    }

    @Test
    public void isAnyNull() {
    }

    @Test
    public void isAllNull() {
    }

    @Test
    public void nonNull() {

    }

    @Test
    public void isNotNull() {

    }

    @Test
    public void nullSafeEquals() {
        Object a = null, b = null;
        assertTrue(Objects.equals(a, b));
        a = 1;
        assertFalse(Objects.equals(a, b));
        b = 2;
        assertFalse(Objects.equals(a, b));
        b = 1;
        assertTrue(Objects.equals(a, b));
        a = "";
        assertFalse(Objects.equals(a, b));
        a = new Object[]{1, 2};
        b = new Object[]{1, 2};
        assertTrue(Objects.equals(a, b));
        a = new int[]{0, 1};
        b = new int[]{0, 1};
        assertTrue(Objects.equals(a, b));
        a = new String[]{"a", "b"};
        b = new String[]{"a", "b"};
        assertTrue(Objects.equals(a, b));

    }

    @Test
    public void testEquals() {
        Object a = null, b = null;
        assertTrue(Objects.equals(a, b));
        a = 1;
        assertFalse(Objects.equals(a, b));
        b = 2;
        assertFalse(Objects.equals(a, b));
        b = 1;
        assertTrue(Objects.equals(a, b));
        a = "";
        assertFalse(Objects.equals(a, b));
        a = new Object[]{1, 2};
        b = new Object[]{1, 2};
        assertTrue(Objects.equals(a, b));
    }

    @Test
    public void compare() {
        Integer a = null, b = null;
        assertEquals(0, Objects.compare(a, b));
        a = 10;
        assertEquals(1, Objects.compare(a, b));
        b = 10;
        assertEquals(0, Objects.compare(a, b));
        a = 20;
        assertEquals(1, Objects.compare(a, b));
        String str1 = "Abc", str2 = "abc";
        assertEquals(-32, Objects.compare(str1, str2));
    }

    @Test
    public void testCompare() {
        Apple apple1 = new Apple(1L, 10), apple2 = new Apple(2L, 5),
                apple3 = new Apple(3L, 12);
        Comparator<Apple> comparator = Comparator.comparing(o -> o.weight);
        assertEquals(1, Objects.compare(apple1, apple2, comparator));
        assertEquals(-1, Objects.compare(apple2, apple3, comparator));
    }

    @Test
    public void testCompare1() {
    }

    @Test
    public void testHashCode() {
        Object a = null;
        assertEquals(0, Objects.hashCode(a));
        Integer val = 100;
        assertEquals(val, Objects.hashCode(val));
    }

    @Test
    public void getIfNull() {
        assertEquals("a", Objects.getIfNull(null, "a"));
        assertEquals("res", Objects.getIfNull("res", "a"));
    }

    @Test
    public void testGetIfNull() {
        assertEquals("a", Objects.getIfNull(null, (Supplier<Object>) () -> "a"));
        assertEquals("res", Objects.getIfNull("res", (Supplier<Object>) () -> "a"));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Apple implements Serializable {

        private Long id;

        private Integer weight;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Apple)) return false;
            Apple apple = (Apple) o;
            return id.equals(apple.id) && weight.equals(apple.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, weight);
        }
    }

}
