package cn.tmkit.core.tuple;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain  Pair}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class PairTest {

    @Test
    public void of() {
        Pair<String, String> pair = Pair.of("a", "b");
        assertEquals("a", pair.getLeft());
        assertEquals("b", pair.getRight());

        Map<String, String> map = new HashMap<>();
        map.put("k", "v");
        pair = Pair.of(map.entrySet().iterator().next());
        assertEquals("k", pair.getKey());
        assertEquals("v", pair.getValue());
    }

}
