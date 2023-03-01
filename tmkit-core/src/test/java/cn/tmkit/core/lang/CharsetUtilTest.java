package cn.tmkit.core.lang;

import org.junit.jupiter.api.Test;

import java.nio.charset.UnsupportedCharsetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tester for {@linkplain CharsetUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class CharsetUtilTest {

    @Test
    public void defaultCharset() {
        String fileEncoding = System.getProperty("file.encoding", "UTF-8");
        String dcn = CharsetUtil.defaultCharsetName();
        assertEquals(fileEncoding, dcn);
    }

    @Test
    public void defaultCharsetName() {

    }

    @Test
    public void forName() {
        assertEquals(Charsets.UTF_8, CharsetUtil.forName(null));
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName(""));
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName(" "));
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName("\t"));
        assertThrows(UnsupportedCharsetException.class, () ->
                assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName("ISO")));
        assertEquals(CharsetUtil.ISO_8859_1_VALUE, CharsetUtil.forName("ISO-8859-1").name());
    }

    @Test
    public void testForName() {
        assertEquals(CharsetUtil.ISO_8859_1, CharsetUtil.forName("", CharsetUtil.ISO_8859_1));
    }

    @Test
    public void getCharset() {
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.getCharset(CharsetUtil.UTF_8));
    }

    @Test
    public void testGetCharset() {
    }

    @Test
    public void parseQuietly() {
        assertEquals(CharsetUtil.ISO_8859_1, CharsetUtil.parseQuietly("AAA", CharsetUtil.ISO_8859_1));
    }

}
