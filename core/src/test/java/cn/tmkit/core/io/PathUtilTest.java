package cn.tmkit.core.io;

import cn.tmkit.core.lang.ClassLoaderUtil;
import cn.tmkit.core.lang.ClassLoaders;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain PathUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-16
 */
@SuppressWarnings("ConstantConditions")
public class PathUtilTest {

    @Test
    public void isDirEmpty() {
        Path dir = PathUtil.get(ClassLoaders.getFilePath(""), "empty-folder");
        PathUtil.mkdir(dir);
        assertTrue(PathUtil.isDirEmpty(dir));

        dir = java.nio.file.Paths.get(ClassLoaderUtil.getFilePath("non-empty-folder"));
        assertFalse(PathUtil.isDirEmpty(dir));
    }

    @Test
    public void listFiles() {
        Path dir = PathUtil.get(ClassLoaders.getUrl("list-files"));
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
