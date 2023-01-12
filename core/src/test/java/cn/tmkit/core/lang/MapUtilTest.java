package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain MapUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class MapUtilTest {

    @Test
    public void newHashMap() {
        HashMap<Object, Object> hashMap = Maps.newHashMap(8);
        assertNotNull(hashMap);
    }

    @Test
    public void newLinkedHashMap() {
        LinkedHashMap<Object, Object> hashMap = Maps.newLinkedHashMap(8);
        assertNotNull(hashMap);
    }

    @Test
    public void isEmpty() {
        Map<String, Object> map = new HashMap<>();
        assertTrue(MapUtil.isEmpty(map));
        map.put(null, null);
        assertFalse(MapUtil.isEmpty(map));
    }

    @Test
    public void isNotEmpty() {
    }

    @Test
    public void toSignStr() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "OK");
        map.put("key", "val1");
        map.put("version", 1);
        assertEquals("key=val1&status=OK&version=1", Maps.toSignStr(map));
    }

    @Test
    public void sort() {
        Map<String, Object> map = new HashMap<>();
        map.put("version", 1);
        map.put("key", "val1");
        map.put("status", "OK");
        Set<String> keys = MapUtil.sort(map).keySet();
        Set<String> expected = new TreeSet<>();
        expected.add("key");
        expected.add("status");
        expected.add("version");
        assertEquals(expected, keys);
    }

    @Test
    public void reverse() {

        Map<String, String> map = new HashMap<>();
        map.put("version", "1");
        map.put("key", "val1");
        map.put("status", "OK");
        Map<String, String> result = Maps.reverse(map);

        Map<String, String> expected = new HashMap<>();
        expected.put("1", "version");
        expected.put("val1", "key");
        expected.put("OK", "status");
        assertEquals(expected, result);
    }

}
