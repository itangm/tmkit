package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester for {@linkplain Hexes}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-05-05
 */
public class HexesTest {

    @Test
    public void toHexStr() {
        int value = 6;
        // 0000 1100
        // 06
        assertEquals("6", Hexes.toHexStr(value));
        value = 15;
        assertEquals("f", Hexes.toHexStr(value));
        value = 20;
        assertEquals("14", Hexes.toHexStr(value));
    }

    @Test
    public void toHexStrLeftPad() {
        int value = 6;
        // 0000 1100
        // 06
        assertEquals("06", Hexes.toHexStrLeftPad(value, 2));
        value = 15;
        assertEquals("0f", Hexes.toHexStrLeftPad(value, 2));
        value = 20;
        assertEquals("14", Hexes.toHexStrLeftPad(value, 2));
    }

    @Test
    public void toUpperHexStr() {
        int value = 6;
        // 0000 1100
        // 06
        assertEquals("6", Hexes.toUpperHexStr(value));
        value = 15;
        assertEquals("F", Hexes.toUpperHexStr(value));
        value = 20;
        assertEquals("14", Hexes.toUpperHexStr(value));
    }

    @Test
    public void toUpperHexStrLeftPad() {
        int value = 6;
        // 0000 1100
        // 06
        assertEquals("06", Hexes.toUpperHexStrLeftPad(value, 2));
        value = 15;
        assertEquals("0F", Hexes.toUpperHexStrLeftPad(value, 2));
        value = 20;
        assertEquals("14", Hexes.toUpperHexStrLeftPad(value, 2));
    }

    @Test
    public void toInt() {
        String hex = "FA";
        assertEquals(250, Hexes.toInt(hex));
        hex = "FFFA";
        assertEquals(65530, Hexes.toInt(hex));
    }

    @Test
    public void toShort() {
        String hex = "FFFA";
        assertEquals(-6, Hexes.toShort(hex));
    }

}
