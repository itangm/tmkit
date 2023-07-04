package cn.tmkit.core.io;

import cn.tmkit.core.lang.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain FileUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class FileUtilTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void touch() {
        File touchFile = ClassLoaderUtil.getFile("touch/touch.txt");
        FileUtil.delete(touchFile);
        Files.touch(touchFile);
        assertTrue(touchFile.exists());
    }

    @Test
    public void createTmpFile() throws Exception {
        File tmpFile = Files.createTmpFile();
        System.out.println(tmpFile.getCanonicalPath());
        String content = "工具解放双手";
        File writeFile = Files.writeUtf8Str(content, tmpFile.getCanonicalPath());
        assertEquals(tmpFile.getCanonicalPath(), writeFile.getCanonicalPath());
        assertEquals(content, Files.readUtf8Str(tmpFile));
        FileUtil.del(tmpFile);
    }

    @Test
    public void createTmpFileWithDir() throws Exception {
        File tmpFile = FileUtil.createTmpFile(ClassLoaderUtil.getFile("tmp/"));
        String content = "工具更快乐一点";
        Files.write(content, tmpFile, Charsets.UTF_8_VALUE);
        assertEquals(content, FileUtil.readUtf8Str(tmpFile.getCanonicalPath()));
        FileUtil.del(tmpFile);
    }

    @Test
    public void formatSize() {
        assertEquals(Strings.EMPTY_STRING, Files.formatSize(-1));
        assertEquals("0.0 bytes", FileUtil.formatSize(0));
        assertEquals("1.0 bytes", FileUtil.formatSize(1));
        assertEquals("1.00 KB", FileUtil.formatSize(1024));
        assertEquals("1.53 KB", FileUtil.formatSize(1024 * 1.526));
        assertEquals("1.52 KB", FileUtil.formatSize(1024 * 1.521));
        assertEquals("1.00 MB", FileUtil.formatSize(1024 * 1024));
        assertEquals("15.04 KB", FileUtil.formatSize("15396"));
    }

    @Test
    public void isFileSeparator() {
        assertTrue(Files.isFileSeparator(Chars.SLASH));
        assertTrue(Files.isFileSeparator(Chars.BACKSLASH));
        assertFalse(Files.isFileSeparator(Chars.SPACE));
        assertFalse(Files.isFileSeparator(Chars.RIGHT_MIDDLE_BRACKET));
        assertFalse(Files.isFileSeparator(Chars.LEFT_BRACKET));
        assertFalse(Files.isFileSeparator(Chars.RIGHT_BRACKET));
        assertFalse(Files.isFileSeparator(Chars.POUND));
        assertFalse(Files.isFileSeparator(Chars.DOLLAR));
        assertFalse(Files.isFileSeparator(Chars.PLUS));
        assertFalse(Files.isFileSeparator(Chars.MINUS));
    }

    @Test
    public void mainName() {
        assertNull(Files.mainName((File) null));
        File file = new File("/tmp/test");
        assertEquals("test", FileUtil.mainName(file));
        file = new File("/tmp/.idea");
        assertEquals("", FileUtil.mainName(file));
        file = new File("/tmp/tmkit.tar.gz");
        assertEquals("tmkit.tar", FileUtil.mainName(file));
        file = new File("/tmp/.idea/vcs.xml");
        assertEquals("vcs", FileUtil.mainName(file));
        file = new File("/tmp/.idea/");
        assertEquals("", FileUtil.mainName(file));
    }

    @Test
    public void getRealBinExt() {
        assertThrows(UnsupportedOperationException.class, () -> {
            String realBinExt = Files.getRealBinExt(ClassLoaderUtil.getFile("touch/empty.txt"));
            assertNotNull(realBinExt);
        });
    }

    @Test
    public void getRelativePath() throws Exception {
        File file1 = ClassLoaderUtil.getFile("touch/empty.txt");
        File file2 = ClassLoaderUtil.getFile("");
        assertNotNull(file1);
        assertNotNull(file2);
        String relativePath = FileUtil.getRelativePath(file1, file2.getCanonicalPath());
        assertEquals("touch/empty.txt", relativePath);
    }

    @Test
    public void isAbsolutePath() {
        assertFalse(FileUtil.isAbsolutePath(""));
        assertFalse(FileUtil.isAbsolutePath("touch"));
        assertTrue(FileUtil.isAbsolutePath("/touch"));
        assertTrue(FileUtil.isAbsolutePath("D:/touch"));
        assertTrue(FileUtil.isAbsolutePath("D:\\tmkit"));
        assertTrue(FileUtil.isAbsolutePath(ClassLoaderUtil.getFilePath("")));
    }

    @Test
    public void readLines() {
        String filePath = ClassLoaderUtil.getFilePath("list-files/author.json");
        List<String> lines = FileUtil.readLines(filePath);
        assertNotNull(lines);
        assertEquals(3, lines.size());
        for (int i = 0; i < lines.size(); i++) {
            switch (i) {
                case 0:
                    assertEquals("{", lines.get(i));
                    break;
                case 1:
                    assertEquals("  \"author\": \"miles.tang\"", lines.get(i));
                    break;
                case 2:
                    assertEquals("}", lines.get(i));
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void readLines2() {
        File file = ClassLoaderUtil.getFile("touch/empty.txt");
        List<String> lines = FileUtil.readLines(file);
        assertNotNull(lines);
        assertEquals(0, lines.size());
    }

    @Test
    public void readUtf8Lines() {
        File file = ClassLoaderUtil.getFile("list-files/desc.txt");
        List<String> lines = FileUtil.readUtf8Lines(file);
        assertNotNull(lines);
        assertEquals(1, lines.size());
        assertEquals("这是一个TXT文本文件", lines.get(0));
    }

    @Test
    public void readStr1() {
        String filePath = ClassLoaderUtil.getFilePath("list-files/desc.txt");
        String content = FileUtil.readStr(filePath);
        assertNotNull(content);
    }

    @Test
    public void readStr2() {
        File file = ClassLoaderUtil.getFile("list-files/desc.txt");
        String content = FileUtil.readStr(file);
        assertNotNull(content);
    }

    @Test
    public void readStr3() {
        File file = ClassLoaderUtil.getFile("list-files/desc.txt");
        String content = FileUtil.readStr(file, CharsetUtil.GBK);
        assertNotNull(content);
    }

    @Test
    public void readStr4() {
        File file = ClassLoaderUtil.getFile("list-files/desc.txt");
        String content = FileUtil.readStr(file, CharsetUtil.GBK_VALUE);
        assertNotNull(content);
    }

    @Test
    public void readBytes() {
        String filePath = ClassLoaderUtil.getFilePath("list-files/desc.txt");
        byte[] content = FileUtil.readBytes(filePath);
        assertNotNull(content);
    }

    @Test
    public void readByteStream() {
        String lines = "这是一个TXT文本文件\n";
        String filePath = ClassLoaderUtil.getFilePath("list-files/desc.txt");
        ByteArrayInputStream bais = FileUtil.readByteStream(filePath);
        assertArrayEquals(lines.getBytes(), IoUtil.readBytes(bais));
    }

    @Test
    public void write() throws Exception {
        File parentFile = ClassLoaderUtil.getFile("tmp");
        File resultFile = new File(parentFile, "write.txt");
        Files.touch(resultFile);
        resultFile = Files.write("用GBK写入的", resultFile.getCanonicalPath(), CharsetUtil.GBK_VALUE);
        assertNotNull(resultFile);
        assertNotEquals("用GBK写入的", Files.readUtf8Str(resultFile));
        assertEquals("用GBK写入的", Files.readStr(resultFile, Charsets.GBK_VALUE));
        FileUtil.rm(resultFile);
    }

    @Test
    public void appendUtf8Str() {

    }

    @Test
    public void copyInputStream() throws Exception {
        final File tempFile = File.createTempFile(UUID.randomUUID().toString().replace("-", ""), "tmkit");
        File sourceFile = new File("C:\\Users\\miles.tang\\Downloads\\树叶.avi");
        try (FileInputStream fis = FileUtil.getFileInputStream(sourceFile)) {
            Files.copy(fis, tempFile);
            System.out.println(tempFile.getAbsolutePath());
        }
    }

}
