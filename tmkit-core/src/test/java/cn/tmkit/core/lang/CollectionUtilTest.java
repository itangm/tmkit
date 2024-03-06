package cn.tmkit.core.lang;

import lombok.*;
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
    public void toList() {
        Collection<Employee> collection = null;
        List<Employee> expected = CollectionUtil.toList(collection);
        assertNotNull(expected);

        collection = new HashSet<>();
        collection.add(new Employee(1002L, "White", 22));
        collection.add(new Employee(1001L, "Anna", 20));
        collection.add(new Employee(1004L, "White", 24));
        expected = Collections.toList(collection);
        assertNotSame(expected, collection);
        assertEquals(3, expected.size());

        collection = new ArrayList<>();
        collection.add(new Employee(1002L, "White", 22));
        collection.add(new Employee(1001L, "Anna", 20));
        collection.add(new Employee(1004L, "White", 24));
        expected = Collections.toList(collection);
        // 代表了没做任何变化
        assertSame(expected, collection);

        // 测试去重，但是没重写equals方法
        collection.add(new Employee(1004L, "White", 24));
        collection.add(new Employee(1004L, "White", 24));
        expected = Collections.toList(collection, true);
        assertEquals(5, expected.size());

        // 测试去重，重写了Equals
        List<SubEmployee> list = new ArrayList<>();
        list.add(new SubEmployee(1002L, "White", 22));
        list.add(new SubEmployee(1001L, "Anna", 20));
        list.add(new SubEmployee(1004L, "White", 24));
        list.add(new SubEmployee(1004L, "White", 24));
        list.add(new SubEmployee(1004L, "White", 24));
        List<SubEmployee> expectedList = Collections.toList(list, true);
        assertEquals(3, expectedList.size());
    }

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
        List<Object> list = new ArrayList<>();
        assertEquals(StringUtil.EMPTY_STRING, CollectionUtil.join(list));
        list.add(null);
        assertEquals(StringUtil.EMPTY_STRING, CollectionUtil.join(list));
        list.add("A");
        assertEquals("A", CollectionUtil.join(list));
        list.add("Op");
        assertEquals("A,Op", CollectionUtil.join(list));
        list.add(12);
        assertEquals("A,Op,12", CollectionUtil.join(list));
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
        expected.add("world");
        expected.add("miles");
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

    @Test
    public void ascAndDesc() {
        List<Employee> employees = Collections.arrayList(
                new Employee(1002L, "White", 22),
                new Employee(1001L, "Anna", 20),
                new Employee(1004L, "White", 24),
                new Employee(1005L, "Tom", 19),
                new Employee(1003L, "Mask", 18)
        );
        Collections.asc(employees, Employee::getNo);
        assertEquals(1001, Collections.get(employees, 0).getNo());
        assertEquals(1005, Collections.get(employees, 4).getNo());
        Collections.desc(employees, Employee::getAge);
        assertEquals(1004, Collections.get(employees, 0).getNo());
        assertEquals(1003, Collections.get(employees, 4).getNo());

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Employee {

        /**
         * 员工编号
         */
        protected Long no;

        /**
         * 员工姓名
         */
        protected String name;

        /**
         * 员工年龄
         */
        protected int age;

    }

    public static class SubEmployee extends Employee {

        public SubEmployee(Long no, String name, int age) {
            super(no, name, age);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Employee)) {
                return false;
            }
            Employee other = (Employee) obj;
            return this.no.equals(other.no);
        }

        @Override
        public int hashCode() {
            return this.no.hashCode();
        }

    }


}
