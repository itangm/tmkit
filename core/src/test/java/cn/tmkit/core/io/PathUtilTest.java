package cn.tmkit.core.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain PathUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-16
 */
@SuppressWarnings("ConstantConditions")
public class PathUtilTest {

    @Test
    public void isDirEmpty() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource("");
        Path dir = PathUtil.get(url, "empty-folder");
        PathUtil.mkdir(dir);
        assertTrue(PathUtil.isDirEmpty(dir));

        url = classLoader.getResource("non-empty-folder");
        dir = java.nio.file.Paths.get(url.toURI());
        assertFalse(PathUtil.isDirEmpty(dir));
    }

    @Test
    public void listFiles() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource("list-files");
        Path dir = PathUtil.get(url);
        List<File> files = PathUtil.listFiles(dir);
        assertEquals(4, files.size());

        files = PathUtil.listFiles(dir, pathname -> {
            String fileExt = FileUtil.getFileExt(pathname);
            return "json".equals(fileExt);
        });
        assertEquals(2, files.size());
        files.forEach(System.out::println);
    }


}
