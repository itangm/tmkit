package cn.tmkit.core.lang;

import cn.tmkit.core.exception.ArrayEmptyException;
import cn.tmkit.core.exception.GenericRuntimeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain Arrays}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class ArrayUtilTest {

    @Test
    public void isArray() {
        assertFalse(ArrayUtil.isArray(null));
        assertFalse(ArrayUtil.isArray("123"));
        assertFalse(ArrayUtil.isArray(123));
        assertTrue(ArrayUtil.isArray(new String[]{"aa", "bb"}));
        assertTrue(ArrayUtil.isArray(new int[]{}));
    }

    @Test
    public void isPrimitiveArray() {
        assertFalse(ArrayUtil.isPrimitiveArray(null));
        assertFalse(ArrayUtil.isPrimitiveArray(1));
        assertFalse(ArrayUtil.isPrimitiveArray(1L));
        assertFalse(ArrayUtil.isPrimitiveArray("1"));
        assertFalse(ArrayUtil.isPrimitiveArray(new String[]{}));
        assertTrue(ArrayUtil.isPrimitiveArray(new int[]{}));
        assertTrue(ArrayUtil.isPrimitiveArray(new byte[]{}));
    }

    @Test
    public void testIsEmpty() {
        assertThrows(GenericRuntimeException.class, () -> ArrayUtil.isEmpty(new ArrayList<>()));
        assertThrows(GenericRuntimeException.class, () -> ArrayUtil.isEmpty("ming.tang"));
        assertThrows(GenericRuntimeException.class, () -> ArrayUtil.isEmpty(1));
        Object array = new int[0];
        assertTrue(ArrayUtil.isEmpty(array));
        array = new int[2];
        assertFalse(ArrayUtil.isEmpty(array));
        array = new String[]{null, null};
        assertFalse(ArrayUtil.isEmpty(array));
    }

    @Test
    public void testIsEmpty1() {
    }

    @Test
    public void testIsEmpty2() {
    }

    @Test
    public void testIsEmpty3() {
    }

    @Test
    public void testIsEmpty4() {
    }

    @Test
    public void testIsEmpty5() {
    }

    @Test
    public void testIsEmpty6() {
    }

    @Test
    public void testIsEmpty7() {
    }

    @Test
    public void isNotEmpty() {
    }

    @Test
    public void testIsNotEmpty() {
        boolean[] array = new boolean[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new boolean[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new boolean[]{true};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty1() {
        byte[] array = new byte[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new byte[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new byte[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty2() {
        short[] array = new short[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new short[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new short[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty3() {
        int[] array = new int[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new int[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new int[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty4() {
        float[] array = new float[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new float[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new float[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty5() {
        double[] array = new double[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new double[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new double[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty6() {
        long[] array = new long[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new long[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new long[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty7() {
        String[] array = new String[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new String[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new String[]{"1"};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsEmpty8() {
        char[] array = new char[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new char[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new char[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void isAnyNull() {
        String[] array = null;
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[0];
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[1];
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[]{"1"};
        assertFalse(ArrayUtil.isAnyNull(array));
        array = new String[]{"1", null};
        assertTrue(ArrayUtil.isAnyNull(array));
    }

    @Test
    public void join() {
        String[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new String[0];
        assertEquals("", ArrayUtil.join(array));
        array = new String[1];
        assertEquals("null", ArrayUtil.join(array));
        array = new String[]{"1"};
        assertEquals("1", ArrayUtil.join(array));
        array = new String[]{"1", null};
        assertEquals("1,null", ArrayUtil.join(array));
    }

    @Test
    public void testJoin() {
        char[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new char[0];
        assertEquals("", ArrayUtil.join(array));
        array = new char[1];
        assertEquals("\0", ArrayUtil.join(array));
        array = new char[]{'1'};
        assertEquals("1", ArrayUtil.join(array));
    }

    @Test
    public void testJoin1() {
        boolean[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new boolean[0];
        assertEquals("", ArrayUtil.join(array));
        array = new boolean[1];
        assertEquals("false", ArrayUtil.join(array));
        array = new boolean[]{true};
        assertEquals("true", ArrayUtil.join(array));
        array = new boolean[]{true, false};
        assertEquals("true,false", ArrayUtil.join(array));
    }

    @Test
    public void testJoin2() {
        byte[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new byte[0];
        assertEquals("", ArrayUtil.join(array));
        array = new byte[1];
        assertEquals("0", ArrayUtil.join(array));
        array = new byte[]{1};
        assertEquals("1", ArrayUtil.join(array));
    }

    @Test
    public void testJoin3() {
        short[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new short[0];
        assertEquals("", ArrayUtil.join(array));
        array = new short[1];
        assertEquals("0", ArrayUtil.join(array));
        array = new short[]{1};
        assertEquals("1", ArrayUtil.join(array));
    }

    @Test
    public void testJoin4() {
        int[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new int[0];
        assertEquals("", ArrayUtil.join(array));
        array = new int[1];
        assertEquals("0", ArrayUtil.join(array));
        array = new int[]{1};
        assertEquals("1", ArrayUtil.join(array));
    }

    @Test
    public void testJoin5() {
        long[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new long[0];
        assertEquals("", ArrayUtil.join(array));
        array = new long[1];
        assertEquals("0", ArrayUtil.join(array));
        array = new long[]{1};
        assertEquals("1", ArrayUtil.join(array));
    }

    @Test
    public void testJoin6() {
        float[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new float[0];
        assertEquals("", ArrayUtil.join(array));
        array = new float[1];
        assertEquals("0.0", ArrayUtil.join(array));
        array = new float[]{1};
        assertEquals("1.0", ArrayUtil.join(array));
    }

    @Test
    public void testJoin7() {
        double[] array = null;
        assertNull(ArrayUtil.join(array));
        array = new double[0];
        assertEquals("", ArrayUtil.join(array));
        array = new double[1];
        assertEquals("0.0", ArrayUtil.join(array));
        array = new double[]{1};
        assertEquals("1.0", ArrayUtil.join(array));
    }

    @Test
    public void toPrimitive() {
        Character[] array= new Character[0];
        assertSame(ArrayUtil.EMPTY_CHAR_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Character[] values = new Character[]{49, null};
            char[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Character[]{49, 50};
        assertArrayEquals(new char[]{'1', '2'}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive() {
        Character[] array = new Character[0];
        assertSame(ArrayUtil.EMPTY_CHAR_ARRAY, ArrayUtil.toPrimitive(array, '0'));
        array = new Character[]{49, null};
        assertArrayEquals(new char[]{'1', '0'}, ArrayUtil.toPrimitive(array, '0'));
        array = new Character[]{49, 50};
        assertArrayEquals(new char[]{'1', '2'}, ArrayUtil.toPrimitive(array, '0'));
    }

    @Test
    public void testToPrimitive1() {
        Long[] array = new Long[0];
        assertSame(ArrayUtil.EMPTY_LONG_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Long[] values = new Long[]{49L, null};
            long[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Long[]{49L, 50L};
        assertArrayEquals(new long[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive2() {
        Long[] array = new Long[0];
        assertSame(ArrayUtil.EMPTY_LONG_ARRAY, ArrayUtil.toPrimitive(array, 0));
        array = new Long[]{49L, null};
        assertArrayEquals(new long[]{49, 0}, ArrayUtil.toPrimitive(array, 0));
        array = new Long[]{49L, 50L};
        assertArrayEquals(new long[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive3() {
        Integer[] array = new Integer[0];
        assertSame(ArrayUtil.EMPTY_INT_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Integer[] values = new Integer[]{49, null};
            int[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Integer[]{49, 50};
        assertArrayEquals(new int[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive4() {
        Integer[] array = new Integer[0];
        assertSame(ArrayUtil.EMPTY_INT_ARRAY, ArrayUtil.toPrimitive(array, 0));
        array = new Integer[]{49, null};
        assertArrayEquals(new int[]{49, 0}, ArrayUtil.toPrimitive(array, 0));
        array = new Integer[]{49, 50};
        assertArrayEquals(new int[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive5() {
        Short[] array = new Short[0];
        assertSame(ArrayUtil.EMPTY_SHORT_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Short[] values = new Short[]{49, null};
            short[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Short[]{49, 50};
        assertArrayEquals(new short[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive6() {
        Short[] array = new Short[0];
        assertSame(ArrayUtil.EMPTY_SHORT_ARRAY, ArrayUtil.toPrimitive(array, (short) 0));
        array = new Short[]{49, null};
        assertArrayEquals(new short[]{49, 0}, ArrayUtil.toPrimitive(array, (short) 0));
        array = new Short[]{49, 50};
        assertArrayEquals(new short[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive7() {
        Byte[] array = new Byte[0];
        assertSame(ArrayUtil.EMPTY_BYTE_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Byte[] values = new Byte[]{49, null};
            byte[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Byte[]{49, 50};
        assertArrayEquals(new byte[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive8() {
        Byte[] array  = new Byte[0];
        assertSame(ArrayUtil.EMPTY_BYTE_ARRAY, ArrayUtil.toPrimitive(array, (byte) 0));
        array = new Byte[]{49, null};
        assertArrayEquals(new byte[]{49, 0}, ArrayUtil.toPrimitive(array, (byte) 0));
        array = new Byte[]{49, 50};
        assertArrayEquals(new byte[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive9() {
        Double[] array  = new Double[0];
        assertSame(ArrayUtil.EMPTY_DOUBLE_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Double[] values = new Double[]{49D, null};
            double[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Double[]{49D, 50D};
        assertArrayEquals(new double[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive10() {
        Double[] array = new Double[0];
        assertSame(ArrayUtil.EMPTY_DOUBLE_ARRAY, ArrayUtil.toPrimitive(array, 0));
        array = new Double[]{49D, null};
        assertArrayEquals(new double[]{49, 0}, ArrayUtil.toPrimitive(array, 0));
        array = new Double[]{49D, 50D};
        assertArrayEquals(new double[]{49, 50}, ArrayUtil.toPrimitive(array, 0));
    }

    @Test
    public void testToPrimitive11() {
        Float[] array = new Float[0];
        assertSame(ArrayUtil.EMPTY_FLOAT_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Float[] values = new Float[]{49F, null};
            float[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Float[]{49F, 50F};
        assertArrayEquals(new float[]{49, 50}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive12() {
        Float[] array = new Float[0];
        assertSame(ArrayUtil.EMPTY_FLOAT_ARRAY, ArrayUtil.toPrimitive(array, 0));
        array = new Float[]{49F, null};
        assertArrayEquals(new float[]{49, 0}, ArrayUtil.toPrimitive(array, 0));
        array = new Float[]{49F, 50F};
        assertArrayEquals(new float[]{49, 50}, ArrayUtil.toPrimitive(array, 0));
    }

    @Test
    public void testToPrimitive13() {
        Boolean[] array = new Boolean[0];
        assertSame(ArrayUtil.EMPTY_BOOLEAN_ARRAY, ArrayUtil.toPrimitive(array));
        assertThrows(NullPointerException.class, () -> {
            Boolean[] values = new Boolean[]{true, null};
            boolean[] ignore = ArrayUtil.toPrimitive(values);
        });
        array = new Boolean[]{true, true};
        assertArrayEquals(new boolean[]{true, true}, ArrayUtil.toPrimitive(array));
    }

    @Test
    public void testToPrimitive14() {
        Boolean[] array= new Boolean[0];
        assertSame(ArrayUtil.EMPTY_BOOLEAN_ARRAY, ArrayUtil.toPrimitive(array, false));
        array = new Boolean[]{true, null};
        assertArrayEquals(new boolean[]{true, false}, ArrayUtil.toPrimitive(array, false));
        array = new Boolean[]{true, true};
        assertArrayEquals(new boolean[]{true, true}, ArrayUtil.toPrimitive(array, false));
    }

    @Test
    public void toObject() {
        char[] array = new char[0];
        assertSame(ArrayUtil.EMPTY_CHAR_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new char[]{'1', '2'};
        assertArrayEquals(new Character[]{'1', '2'}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject() {
        long[] array = new long[0];
        assertSame(ArrayUtil.EMPTY_LONG_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new long[]{1, 2};
        assertArrayEquals(new Long[]{1L, 2L}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject1() {
        int[] array = new int[0];
        assertSame(ArrayUtil.EMPTY_INTEGER_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new int[]{1, 2};
        assertArrayEquals(new Integer[]{1, 2}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject2() {
        short[] array = new short[0];
        assertSame(ArrayUtil.EMPTY_SHORT_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new short[]{1, 2};
        assertArrayEquals(new Short[]{1, 2}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject3() {
        byte[] array = new byte[0];
        assertSame(ArrayUtil.EMPTY_BYTE_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new byte[]{1, 2};
        assertArrayEquals(new Byte[]{1, 2}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject4() {
        double[] array = new double[0];
        assertSame(ArrayUtil.EMPTY_DOUBLE_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new double[]{1, 2};
        assertArrayEquals(new Double[]{1D, 2D}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject5() {
        float[] array = new float[0];
        assertSame(ArrayUtil.EMPTY_FLOAT_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new float[]{1, 2};
        assertArrayEquals(new Float[]{1F, 2F}, ArrayUtil.toObject(array));
    }

    @Test
    public void testToObject6() {
        boolean[] array= new boolean[0];
        assertSame(ArrayUtil.EMPTY_BOOLEAN_OBJECT_ARRAY, ArrayUtil.toObject(array));
        array = new boolean[]{true, false};
        assertArrayEquals(new Boolean[]{true, false}, ArrayUtil.toObject(array));
    }

    @Test
    public void requireNotEmpty() {
        assertThrows(ArrayEmptyException.class, () -> {
            String[] array = new String[0];
            ArrayUtil.requireNotEmpty(array);
        });
        String[] array = new String[]{"1"};
        assertArrayEquals(array, ArrayUtil.requireNotEmpty(array));
    }

    @Test
    public void testRequireNotEmpty7() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new String[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new String[]{"1"}, ArrayUtil.requireNotEmpty(new String[]{"1"}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty8() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new char[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new char[]{1}, ArrayUtil.requireNotEmpty(new char[]{1}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty9() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new byte[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new byte[]{1}, ArrayUtil.requireNotEmpty(new byte[]{1}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty10() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new short[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new short[]{1}, ArrayUtil.requireNotEmpty(new short[]{1}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty11() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new int[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new int[]{1}, ArrayUtil.requireNotEmpty(new int[]{1}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty12() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new long[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new long[]{1}, ArrayUtil.requireNotEmpty(new long[]{1}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty13() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new float[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new float[]{1F}, ArrayUtil.requireNotEmpty(new float[]{1F}, "array is empty"));
    }

    @Test
    public void testRequireNotEmpty14() {
        ArrayEmptyException arrayEmptyException = assertThrows(ArrayEmptyException.class, () ->
                ArrayUtil.requireNotEmpty(new double[0], "array is empty"));
        assertEquals("array is empty", arrayEmptyException.getMessage());
        assertArrayEquals(new double[]{1D}, ArrayUtil.requireNotEmpty(new double[]{1D}, "array is empty"));
    }

    @Test
    public void getComponentType() {
        assertEquals(int.class, ArrayUtil.getComponentType(new int[]{}));
    }

    @Test
    public void indexOf() {
        String[] array = new String[]{"1", "2", "10", "", "a"};
        assertEquals(0, ArrayUtil.indexOf(array, "1"));
    }

    @Test
    public void testIndexOf() {
        long[] array = new long[]{10, 20, 10, 1, 2, 5};
        assertEquals(0, ArrayUtil.indexOf(array, 10L));
        assertEquals(3, ArrayUtil.indexOf(array, 1L));
    }

    @Test
    public void reverse() {
        Integer[] array = null;
        assertNull(ArrayUtil.reverse(array));
        array = new Integer[]{};
        assertEquals(0, ArrayUtil.reverse(array).length);
        array = new Integer[]{1, null, 20, 5};
        assertArrayEquals(new Integer[]{5, 20, null, 1}, ArrayUtil.reverse(array));
    }

    @Test
    public void contains() {
        String[] array = new String[]{"1", null, "2"};
        assertTrue(ArrayUtil.contains(array, null));
    }

    @Test
    public void testContains1() {
        int[] array = new int[]{0, 1, 2, 10, 4};
        assertTrue(ArrayUtil.contains(array, 2));
        assertFalse(ArrayUtil.contains(array, 20));
    }

    @Test
    public void testContains2() {
        char[] array = new char[]{0, 1, 2, 10, 4};
        assertTrue(ArrayUtil.contains(array, (char) 2));
        assertFalse(ArrayUtil.contains(array, (char) 20));
    }

    @Test
    public void min() {
        int[] array = new int[]{1, 4, 10, 9};
        assertEquals(1, ArrayUtil.min(array));
    }

    @Test
    public void testMin() {
        long[] array = new long[]{10, 9, 2, 10, 12};
        assertEquals(2, ArrayUtil.min(array));
    }

    @Test
    public void testMin1() {
        short[] array = new short[]{10, 9, 2, 10, 12};
        assertEquals(2, ArrayUtil.min(array));
    }

    @Test
    public void testMin2() {
        char[] array = new char[]{10, 9, 2, 10, 12};
        assertEquals(2, ArrayUtil.min(array));
    }

    @Test
    public void testMin3() {
        byte[] array = new byte[]{10, 9, 2, 10, 12};
        assertEquals(2, ArrayUtil.min(array));
    }

    @Test
    public void testMin4() {
        double[] array = new double[]{1, 2, 5.5D, 0.5D, 0.4D};
        assertEquals(ArrayUtil.min(array), 0.4D);
    }

    @Test
    public void testMin5() {
        float[] array = new float[]{1, 2, 5.5F, 0.5F, 0.4F};
        assertEquals(ArrayUtil.min(array), 0.4F);
    }

    @Test
    public void max() {
        long[] array = new long[]{10, 9, 2, 10, 12};
        assertEquals(12, ArrayUtil.max(array));
    }

    @Test
    public void testMax() {
    }

    @Test
    public void testMax1() {
        short[] array = new short[]{10, 20, 15, 4};
        assertEquals(ArrayUtil.max(array), 20);
    }

    @Test
    public void testMax2() {
        char[] array = new char[]{10, 20, 15, 4};
        assertEquals(ArrayUtil.max(array), 20);
    }

    @Test
    public void testMax3() {
        byte[] array = new byte[]{10, 20, 15, 4};
        assertEquals(ArrayUtil.max(array), 20);
    }

    @Test
    public void testMax4() {
        double[] array = new double[]{1, 2, 5.5D, 0.5D, 0.4D};
        assertEquals(ArrayUtil.max(array), 5.5D);
    }

    @Test
    public void testMax5() {
        float[] array = new float[]{1, 2, 5.5F, 0.5F, 0.4F};
        assertEquals(ArrayUtil.max(array), 5.5F);
    }

}
