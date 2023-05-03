package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tester for {@linkplain NumberUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-05-03
 */
public class NumberUtilTest {

    @Test
    public void fen2YuanStrLong() {
        Long fen = null;
        assertNull(NumberUtil.fen2YuanStr(fen));
        fen = 0L;
        assertEquals("0.00", Numbers.fen2YuanStr(fen));
        fen = 10L;
        assertEquals("0.10", Numbers.fen2YuanStr(fen));
        fen = 100L;
        assertEquals("1.00", Numbers.fen2YuanStr(fen));
    }

    @Test
    public void fen2YuanStrInteger() {
        Integer fen = null;
        assertNull(NumberUtil.fen2YuanStr(fen));
        fen = 0;
        assertEquals("0.00", Numbers.fen2YuanStr(fen));
        fen = 10;
        assertEquals("0.10", Numbers.fen2YuanStr(fen));
        fen = 100;
        assertEquals("1.00", Numbers.fen2YuanStr(fen));
    }

}
