package cn.tmkit.core.io;

import cn.tmkit.core.exception.FileNotFoundRuntimeException;
import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.lang.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Files {

    // region 内置常量

    /**
     * 1KB
     */
    public static final long ONE_KB = 1024;

    /**
     * 1MB
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * Linux路径分隔符
     */
    public static final char UNIX_SEPARATOR_CHAR = Chars.SLASH;

    /**
     * Linux路径分隔符
     */
    public static final String UNIX_SEPARATOR = Strings.EMPTY_STRING + Chars.SLASH;

    /**
     * Windows路径分隔符
     */
    public static final char WINDOWS_SEPARATOR_CHAR = Chars.BACKSLASH;

    /**
     * Windows路径分隔符
     */
    public static final String WINDOWS_SEPARATOR = Strings.EMPTY_STRING + Chars.BACKSLASH;

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;

    // endregion


    // region 其它

    // endregion


    // region 创建文件

    // region 创建空文件

    /**
     * 创建文件及父目录
     * 1. 如果文件已存在，这直接返回
     * 2. 如果文件不存在，创建空的文件
     *
     * @param pathname 文件的全路径，不能为空
     * @return 返回文件
     */
    public static File touch(@NotNull String pathname) {
        Asserts.notNull("pathname muse not be null");
        File file = new File(pathname);
        touch(file);
        return file;
    }

    /**
     * 创建文件及父目录
     * 1. 如果文件已存在，这直接返回
     * 2. 如果文件不存在，创建空的文件
     * <p>
     * 实现了类似于Linux的<a href="https://www.linuxcool.com/touch">touch命令</a>，不过默认情况下不会修改访问时间。
     * </p>
     *
     * @param file 文件，不能为null
     */
    public static void touch(@Nullable File file) {
        if (Objects.nonNull(file)) {
            if (!file.exists()) {
                forceMakeDir(file.getParentFile());
                try {
                    boolean ignored = file.createNewFile();
                } catch (IOException e) {
                    throw new IoRuntimeException(e);
                }
            }
        }
    }

    // endregion

    // region 创建临时文件

    /**
     * 在系统的临时目录下创建临时文件，临时文件名格式为{@code prefix[Random]suffix}
     *
     * <p>默认的前缀是{@code cn.tmkit}，默认的后缀是{@code .tmp}</p>
     * <p>如果临时文件已存在则会删除重建，即原临时文件的内容被清空</p>
     * <p>默认的临时目录由系统属性<code>java.io.tmpdir</code>指定。
     * 在Windows系统上，默认的临时目录通常为{@code C:\Users\XXX\AppData\Local\Temp}，其中"XXX"登录用户名。
     * 在UNIX系统上，默认的临时目录通常为{@code /tmp}。</p>
     *
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile() throws IoRuntimeException {
        return createTmpFile((String) null);
    }

    /**
     * 在系统的临时目录下创建临时文件，临时文件名格式为{@code prefix[Random].tmp}
     *
     * <p>如果临时文件已存在则会删除重建，即原临时文件的内容被清空</p>
     * <p>默认的后缀是{@code .tmp}</p>
     * <p>默认的临时目录由系统属性<code>java.io.tmpdir</code>指定。
     * 在Windows系统上，默认的临时目录通常为{@code C:\Users\XXX\AppData\Local\Temp}，其中"XXX"登录用户名。
     * 在UNIX系统上，默认的临时目录通常为{@code /tmp}。</p>
     *
     * @param prefix 临时文件的前缀，如果为空则默认值{@code cn.tmkit}，必须不少于三个字符
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile(@Nullable String prefix) throws IoRuntimeException {
        return createTmpFile(prefix, null);
    }

    /**
     * 在系统的临时目录下创建临时文件，临时文件名格式为{@code prefix[Random]suffix}
     *
     * <p>如果临时文件已存在则会删除重建，即原临时文件的内容被清空</p>
     * <p>默认的临时目录由系统属性<code>java.io.tmpdir</code>指定。
     * 在Windows系统上，默认的临时目录通常为{@code C:\Users\XXX\AppData\Local\Temp}，其中"XXX"登录用户名。
     * 在UNIX系统上，默认的临时目录通常为{@code /tmp}。</p>
     *
     * @param prefix 临时文件的前缀，如果为空则默认值{@code cn.tmkit}，必须不少于三个字符
     * @param suffix 临时问价的后缀，如果为空则默认值{@code .tmp}
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile(@Nullable String prefix, @Nullable String suffix) throws IoRuntimeException {
        return createTmpFile(prefix, suffix, true);
    }

    /**
     * 在指定的目录下创建临时文件，临时文件名格式为{@code prefix[Random]suffix}
     *
     * @param dir 临时文件所在的目录，可以为{@code null}，如果为{@code null}则为系统的临时目录
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile(File dir) throws IoRuntimeException {
        return createTmpFile(null, null, dir, true);
    }

    /**
     * 在系统的临时目录下创建临时文件，临时文件名格式为{@code prefix[Random]suffix}。
     *
     * <p>默认的临时目录由系统属性<code>java.io.tmpdir</code>指定。
     * 在Windows系统上，默认的临时目录通常为{@code C:\Users\XXX\AppData\Local\Temp}，其中"XXX"登录用户名。
     * 在UNIX系统上，默认的临时目录通常为{@code /tmp}。</p>
     *
     * @param prefix     临时文件的前缀，如果为空则默认值{@code cn.tmkit}，必须不少于三个字符
     * @param suffix     临时问价的后缀，如果为空则默认值{@code .tmp}
     * @param isRecreate 如果临时文件已存在是否重建，值为{@code true}则删除重建
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile(String prefix, String suffix, boolean isRecreate) throws IoRuntimeException {
        return createTmpFile(prefix, suffix, null, isRecreate);
    }

    /**
     * 在系统的临时目录下创建临时文件，临时文件名格式为{@code prefix[Random]suffix}。
     *
     * @param prefix     临时文件的前缀，如果为空则默认值{@code cn.tmkit}，必须不少于三个字符
     * @param suffix     临时问价的后缀，如果为空则默认值{@code .tmp}
     * @param isRecreate 如果临时文件已存在是否重建，值为{@code true}则删除重建
     * @param dir        指定目录，如果为{@code null}则为系统的临时目录
     * @return 临时文件
     * @throws IoRuntimeException IO异常
     */
    public static File createTmpFile(String prefix, String suffix, File dir, boolean isRecreate) throws IoRuntimeException {
        if (Strings.isBlank(prefix)) {
            prefix = "cn.tmkit";
        }
        if (Strings.isBlank(suffix)) {
            suffix = ".tmp";
        }
        if (dir == null) {
            dir = getTmpDir();
        } else {
            forceMakeDir(dir);
        }
        try {
            File tmpFile = File.createTempFile(prefix, suffix, dir).getCanonicalFile();
            if (isRecreate) {
                delete(tmpFile);
                boolean ignored = tmpFile.createNewFile();
            }
            return tmpFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // endregion

    // endregion


    // region 临时目录和用户目录

    /**
     * 平台临时目录
     *
     * @return 目录文件对象
     */
    public static File getTmpDir() {
        return file(getTmpDirPath());
    }

    /**
     * 平台的临时目录
     *
     * @return 目录路径字符串
     */
    public static String getTmpDirPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 用户主目录
     *
     * @return 用户主目录{@code File}
     */
    public static File getUserHome() {
        return file(getUserHomePath());
    }

    /**
     * 返回用户的主目录
     *
     * @return 用户主目录路径
     */
    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    // endregion


    // region 文件大小格式化

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为{@code Byte}
     * @return 格式化的大小
     */
    public static String formatSize(long fileSize) {
        if (fileSize < 0) {
            return Strings.EMPTY_STRING;
        }
        return formatSize((double) fileSize);
    }

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为{@code Byte}
     * @return 格式化的大小
     */
    public static String formatSize(double fileSize) {
        if (fileSize < 0) {
            return Strings.EMPTY_STRING;
        }
        //byte
        double size = fileSize;
        if (size < 1024) {
            return size + "Byte";
        }
        size /= 1024;
        if (size < 1024) {
            return Math.round(size * 100) / 100.0 + "KB";
        }
        size /= 1024;
        if (size < 1024) {
            return Math.round(size * 100) / 100.0 + "MB";
        }
        size /= 1024;
        if (size < 1024) {
            return Math.round(size * 100) / 100.0 + "GB";
        }
        return Math.round(size * 100) / 100.0 + "TB";
    }

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为{@code Byte}
     * @return 格式化的大小
     */
    public static String formatSize(String fileSize) {
        if (Strings.isEmpty(fileSize)) {
            return Strings.EMPTY_STRING;
        }
        double size = Double.parseDouble(fileSize);
        return formatSize(size);
    }

    // endregion


    // region 常规方法

    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     */
    public static boolean isWindows() {
        return WINDOWS_SEPARATOR_CHAR == File.separatorChar;
    }

    /**
     * 是否为Windows或者Linux/Unix文件分隔符
     *
     * @param ch 字符
     * @return 如果是文件分隔符则返回{@code true},否则返回{@code false}
     * @see Chars#isFileSeparator(char)
     */
    public static boolean isFileSeparator(char ch) {
        return Chars.isFileSeparator(ch);
    }

    /**
     * 从文件路径中提取文件名,不支持Windows系统下的路径
     * <pre class="code">
     * StringUtil.getFilename("/opt/app/config.properties"); //--- config.properties
     * </pre>
     *
     * @param path 文件路径
     * @return 返回文件名或者返回{@code null}如果为空时
     */
    public static String getFilename(String path) {
        if (!Strings.hasLength(path)) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(WINDOWS_SEPARATOR);
        if (separatorIndex == -1) {
            separatorIndex = path.lastIndexOf(UNIX_SEPARATOR);
        }
        return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
    }

    /**
     * 返回主文件名
     *
     * @param file 文件，非空
     * @return 主文件名
     */
    public static String mainName(File file) {
        Asserts.notNull(file, "File cannot be null");
        if (file.isDirectory()) {
            return file.getName();
        }
        return mainName(file.getName());
    }

    /**
     * 返回主文件名
     *
     * @param filename 完整文件名
     * @return 主文件名
     */
    public static String mainName(String filename) {
        if (Strings.isEmpty(filename)) {
            return filename;
        }
        int length = filename.length();
        if (Chars.isFileSeparator(filename.charAt(length - 1))) {
            length--;
        }

        int begin = 0;
        int end = length;
        char ch;
        for (int i = length - 1; i >= 0; i--) {
            ch = filename.charAt(i);
            if (length == end && Chars.DOT == ch) {
                // 查找最后一个文件名和扩展名的分隔符：.
                end = i;
            }
            // 查找最后一个路径分隔符（/或者\），如果这个分隔符在.之后，则继续查找，否则结束
            if (Chars.isFileSeparator(ch)) {
                begin = i + 1;
                break;
            }
        }
        return filename.substring(begin, end);
    }

    /**
     * 从文件路径中提取文件后缀名
     * <pre class="code">
     * StringUtil.getFileExt("/opt/app/config.properties"); //--- properties
     * </pre>
     *
     * @param path 文件路径
     * @return 返回文件后缀名或者返回{@code null}如果为空时
     */
    public static String getFileExt(String path) {
        String filename = getFilename(path);
        if (Strings.isEmpty(filename)) {
            return null;
        }
        int extIndex = filename.lastIndexOf(Strings.DOT);
        if (extIndex == -1) {
            return null;
        }
        return filename.substring(extIndex + 1);
    }

    /**
     * 从文件路径中提取文件后缀名
     * <pre class="code">
     * StringUtil.getFileExt("/opt/app/config.properties"); //--- properties
     * </pre>
     *
     * @param file 文件路径
     * @return 返回文件后缀名或者返回{@code null}如果为空时
     */
    public static String getFileExt(File file) {
        if (file == null) {
            return null;
        }
        return getFileExt(file.getName());
    }

    /**
     * 获取真实文件类型，读取文件头N个字节
     *
     * @param file 文件
     * @return 文件后缀
     */
    public static String getRealBinExt(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream in = null;
        try {
            in = openFileInputStream(file);
            return IoUtil.getRealBinExt(in);
        } finally {
            IoUtil.closeQuietly(in);
        }
    }

    /**
     * 获得相对子路径，忽略大小写
     *
     * @param file     文件
     * @param fromPath 参考路径
     * @return 相对路径
     */
    public static String getRelativePath(@NotNull File file, String fromPath) {
        Asserts.notNull(file, "File cannot be null");
        try {
            return getRelativePath(file.getCanonicalPath(), fromPath);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得相对子路径，忽略大小写
     *
     * @param path     路径
     * @param fromPath 参考路径
     * @return 相对路径
     */
    public static String getRelativePath(String path, String fromPath) {
        if (Strings.isAllNotBlank(path, fromPath)) {
            path = normalize(path);
            if (path.charAt(path.length() - 1) == Chars.SLASH) {
                path = path.substring(0, path.length() - 1);
            }
            fromPath = normalize(fromPath);
            final String result = Strings.deletePrefixIgnoreCase(path, fromPath);
            return Strings.deletePrefix(result, Strings.SLASH);
        }
        return path;
    }

    /**
     * 将文件路径标准化,统一使用{@code UNIX}的路径分隔符
     * 如果是Windows路径且带有盘符，一定会返回盘符而不会返回空串
     * 如果是UNIX路径或不带盘符的Windows路径，可能会返回空串
     * <pre>
     *     /foo//                   --&gt;    /foo/
     *     /foo/./                  --&gt;    /foo/
     *     /foo/../bar              --&gt;    /bar
     *     /foo/../bar/             --&gt;    /bar/
     *     /foo/../bar/../baz       --&gt;    /baz
     *     //foo//./bar             --&gt;    /foo/bar
     *     /../                     --&gt;    /
     *     ../foo                   --&gt;
     *     foo/bar/..               --&gt;    foo
     *     foo/../../bar            --&gt;
     *     foo/../bar               --&gt;    bar
     *     //server/foo/../bar      --&gt;    /server/bar
     *     //server/../bar          --&gt;    /bar
     *     C:\foo\..\bar            --&gt;    C:/bar
     *     C:\..\bar                --&gt;    C:/
     *     ~/foo/../bar/            --&gt;    ~/bar/
     *     ~/../bar                 --&gt;    bar
     * </pre>
     *
     * @param path 路径
     * @return 标准化的路径
     */
    public static String normalize(String path) {
        if (Strings.isEmpty(path)) {
            return path;
        }

        String pathTemp = path.replaceAll("[/\\\\]+", Strings.SLASH).trim();

        String prefix = Strings.EMPTY_STRING;
        int prefixIndex = pathTemp.indexOf(Strings.COLON);
        if (prefixIndex > -1) {
            // 可能Windows风格路径
            prefix = pathTemp.substring(0, prefixIndex + 1);
            if (prefix.indexOf(Strings.SLASH) == 0) {
                prefix = prefix.substring(1);
            }
            pathTemp = pathTemp.substring(prefixIndex + 1);
        }

        if (pathTemp.startsWith(Strings.SLASH)) {
            prefix += Strings.SLASH;
            pathTemp = pathTemp.substring(1);
        }
        String[] pathArray = Strings.split(pathTemp, Chars.SLASH);
        List<String> pathElements = new ArrayList<>(pathArray.length);
        int tops = 0;
        String element;
        for (int i = pathArray.length - 1; i >= 0; i--) {
            element = pathArray[i];
            if (!Strings.DOT.equals(element)) {
                if ("..".equals(element)) {
                    tops++;
                } else {
                    if (tops > 0) {
                        // 有上级目录标记时按照个数依次跳过
                        tops--;
                    } else {
                        pathElements.add(0, element);
                    }
                }
            }
        }
        if (tops > 0) {
            do {
                pathElements.remove(0);
                tops--;
            } while (tops == 0 && pathElements.size() > 0);
        }

        return prefix + Collections.join(pathElements, Strings.SLASH);
    }

    /**
     * 检查父完整路径是否为自路径的前半部分，如果不是说明不是子路径，可能存在slip注入。
     * <p>
     * https://www.freebuf.com/vuls/180383.html
     * </p>
     *
     * @param parentFile 父文件或目录
     * @param file       子文件或目录
     * @throws IllegalArgumentException 检查创建的子文件不在父目录中抛出此异常
     */
    public static void checkSlip(File parentFile, File file) throws IllegalArgumentException {
        if (null != parentFile && null != file) {
            String parentCanonicalPath;
            String canonicalPath;
            try {
                parentCanonicalPath = parentFile.getCanonicalPath();
                canonicalPath = file.getCanonicalPath();
            } catch (IOException e) {
                throw new IoRuntimeException(e);
            }
            if (!canonicalPath.startsWith(parentCanonicalPath)) {
                throw new IllegalArgumentException("New file is outside of the parent dir: " + file.getName());
            }
        }
    }

    /**
     * 给定路径已经是绝对路径
     *
     * @param path 需要检查的Path
     * @return 是否已经是绝对路径
     */
    public static boolean isAbsolutePath(String path) {
        if (Strings.isEmpty(path)) {
            return false;
        }
        return Chars.SLASH == path.charAt(0) || path.matches("^[a-zA-Z]:([/\\\\].*)?");
    }

    /**
     * 创建File对象
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (path == null) {
            return null;
        }
        return new File(path);
    }

    // endregion

    // region Read Files

    /**
     * 将文件的内容全部读取,采用系统默认编码
     *
     * @param file 待读的文件
     * @return 文件内容
     */
    public static List<String> readLines(final File file) {
        return readLines(file, null);
    }

    /**
     * 将文件的内容全部读取,采用系统默认编码
     *
     * @param file 待读的文件
     * @return 文件内容
     */
    public static List<String> readUtf8Lines(final File file) {
        return readLines(file, Charsets.UTF_8);
    }

    /**
     * 将文件的内容全部读取
     *
     * @param file    待读的文件
     * @param charset 字符编码
     * @return 文件内容
     */
    public static List<String> readLines(File file, final Charset charset) {
        try (FileInputStream in = openFileInputStream(file)) {
            return IoUtil.readLines(in, charset);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file 文件对象
     * @return 返回文件内容
     */
    public static String readString(File file) {
        return readString(file, (Charset) null);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file 文件对象
     * @return 返回文件内容
     */
    public static String readUtf8String(File file) {
        return readString(file, Charsets.UTF_8);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file    文件对象
     * @param charset 字符集
     * @return 返回文件内容
     */
    public static String readString(File file, String charset) {
        return readString(file, Charsets.forName(charset));
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file    文件对象
     * @param charset 字符集
     * @return 返回文件内容
     */
    public static String readString(File file, Charset charset) {
        try (FileInputStream in = openFileInputStream(file)) {
            return IoUtil.read(in, charset);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 读取文件的二进制内容
     *
     * @param file 文件，非空
     * @return 文件内容
     * @throws IoRuntimeException 读取文件异常
     */
    public static byte[] readBytes(File file) {
        try (FileInputStream in = openFileInputStream(file)) {
            FastByteArrayOutputStream output = new FastByteArrayOutputStream();
            IoUtil.copy(in, output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion


    // region 写文件

    // region write string

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File writeUtf8Str(String content, String path) throws IoRuntimeException {
        return write(content, path, Charsets.UTF_8);
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void writeUtf8Str(String content, File file) throws IoRuntimeException {
        write(content, file, Charsets.UTF_8);
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File write(String content, String path, String charset) throws IoRuntimeException {
        return write(content, path, Charset.forName(charset));
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File write(String content, String path, Charset charset) throws IoRuntimeException {
        File file = touch(path);
        write(content, file, charset);
        return file;
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @param charset 字符集
     * @throws IoRuntimeException IO异常
     */
    public static void write(String content, File file, String charset) throws IoRuntimeException {
        write(content, file, Charsets.forName(charset));
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @param charset 字符集
     * @throws IoRuntimeException IO异常
     */
    public static void write(@Nullable String content, @Nullable File file, Charset charset) throws IoRuntimeException {
        doWrite(content, file, charset, false);
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File appendUtf8Str(String content, String path) throws IoRuntimeException {
        return append(content, path, Charsets.UTF_8);
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void appendUtf8Str(String content, File file) throws IoRuntimeException {
        append(content, file, Charsets.UTF_8);
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File append(String content, String path, String charset) throws IoRuntimeException {
        return append(content, path, Charset.forName(charset));
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static @NotNull File append(String content, String path, Charset charset) throws IoRuntimeException {
        File file = touch(path);
        append(content, file, charset);
        return file;
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @param charset 字符集
     * @throws IoRuntimeException IO异常
     */
    public static void append(String content, File file, String charset) throws IoRuntimeException {
        append(content, file, Charsets.forName(charset));
    }

    /**
     * 将内容写入到文件，原内容被覆盖
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @param charset 字符集
     * @throws IoRuntimeException IO异常
     */
    public static void append(@Nullable String content, @Nullable File file, Charset charset) throws IoRuntimeException {
        doWrite(content, file, charset, true);
    }

    /**
     * 将内容写入到文件
     *
     * @param content  待写入的内容
     * @param file     写入的文件
     * @param charset  字符集
     * @param isAppend 是否追加
     * @throws IoRuntimeException IO异常
     */
    @SuppressWarnings("ConstantConditions")
    private static void doWrite(@Nullable String content, @Nullable File file, Charset charset, boolean isAppend) throws IoRuntimeException {
        if (Objects.isAnyNull(content, file)) {
            return;
        }
        try (BufferedWriter bufferedWriter = getWriter(file, charset, isAppend)) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

    /**
     * 获取一个带缓存的文件写入对象
     *
     * @param file     文件
     * @param encoding 文件字符集，默认为空采用UTF-8编码
     * @param isAppend 是否追加
     * @return {@linkplain BufferedWriter}
     * @throws IoRuntimeException IO异常
     */
    public static BufferedWriter getWriter(@NotNull File file, Charset encoding, boolean isAppend) throws IoRuntimeException {
        touch(file);
        BufferedOutputStream bos = getBufferedOutputStream(file, isAppend);
        OutputStreamWriter osw = new OutputStreamWriter(bos, Charsets.getCharset(encoding, Charsets.UTF_8));
        return new BufferedWriter(osw);
    }

    // region write bytes

    /**
     * 将数据写入到文件中，原内容被覆盖
     *
     * @param data 待写入的数据
     * @param path 文件路径
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static File write(byte[] data, @NotNull String path) throws IoRuntimeException {
        File file = touch(path);
        write(data, file);
        return file;
    }

    /**
     * 将数据写入到文件中，原内容被覆盖
     *
     * @param data 待写入的数据
     * @param file 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void write(byte[] data, File file) throws IoRuntimeException {
        if (Arrays.isEmpty(data)) {
            return;
        }
        write(data, file, 0, data.length);
    }

    /**
     * 将数据写入到文件中，原内容被覆盖
     *
     * @param data   待写入的数据
     * @param file   写入的文件
     * @param offset 数据开始位置
     * @param length 数据长度
     * @throws IoRuntimeException IO异常
     */
    public static void write(byte[] data, File file, int offset, int length) {
        doWrite(data, file, offset, length, false);
    }

    /**
     * 将数据写入到文件中，追加模式，原内容不会覆盖
     *
     * @param data 待写入的数据
     * @param path 文件路径
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static File append(byte[] data, @NotNull String path) throws IoRuntimeException {
        File file = touch(path);
        append(data, file);
        return file;
    }

    /**
     * 将数据写入到文件中，追加模式，原内容不会覆盖
     *
     * @param data 待写入的数据
     * @param file 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void append(byte[] data, File file) throws IoRuntimeException {
        if (Arrays.isEmpty(data)) {
            return;
        }
        append(data, file, 0, data.length);
    }

    /**
     * 将数据写入到文件中，追加模式，原内容不会覆盖
     *
     * @param data   待写入的数据
     * @param file   写入的文件
     * @param offset 数据开始位置
     * @param length 数据长度
     * @throws IoRuntimeException IO异常
     */
    public static void append(byte[] data, File file, int offset, int length) {
        doWrite(data, file, offset, length, true);
    }

    /**
     * 将数据写入到文件中
     *
     * @param data     待写入的数据
     * @param file     写入的文件
     * @param offset   数据开始位置
     * @param length   数据长度
     * @param isAppend 是否追加模式
     * @throws IoRuntimeException IO异常
     */
    private static void doWrite(byte[] data, File file, int offset, int length, boolean isAppend) throws IoRuntimeException {
        if (Arrays.isEmpty(data) || Objects.isNull(file)) {
            return;
        }
        touch(file);
        try (BufferedOutputStream bos = getBufferedOutputStream(file, isAppend)) {
            bos.write(data, offset, length);
            bos.flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

//    public static void

    // endregion


    // region Copy Files

    /**
     * 文件内容拷贝指定的输出流中
     *
     * @param srcFile 原文件
     * @param output  输出流
     */
    public static void copyFile(File srcFile, OutputStream output) {
        FileInputStream in = openFileInputStream(srcFile);
        try {
            IoUtil.copy(new BufferedInputStream(in), output);
        } finally {
            IoUtil.closeQuietly(in);
        }
    }

    /**
     * 文件拷贝
     *
     * @param srcFile  原文件
     * @param destFile 目标文件
     */
    public static void copyFile(File srcFile, File destFile) {
        copyFile(srcFile, destFile, true);
    }

    /**
     * checks requirements for file copy
     *
     * @param src  the source file
     * @param dest the destination
     */
    private static void checkFileRequirements(final File src, final File dest) {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundRuntimeException("Source '" + src + "' does not exist");
        }
    }

    /**
     * 文件拷贝
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param holdFileDate 保持最后修改日期不变
     */
    public static void copyFile(File srcFile, File destFile, boolean holdFileDate) {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IoRuntimeException("Source [" + srcFile + "] exists but it is a directory");
        }

        try {
            if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
                throw new IoRuntimeException("Source [" + srcFile + "] and destination [" + destFile + "] are the same");
            }
            File parentFile = destFile.getParentFile();
            if (parentFile != null) {
                if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                    throw new IoRuntimeException("Destination [" + parentFile + "] directory cannot be created");
                }
            }
            if (destFile.exists() && !destFile.canWrite()) {
                throw new IoRuntimeException(" ===> Destination [" + parentFile + "] directory cannot be written");
            }
            doCopyFile(srcFile, destFile, holdFileDate);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 文件复制内部方法
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param holdFileDate 保持最后修改日期不变
     * @throws IOException I/O异常
     */
    private static void doCopyFile(File srcFile, File destFile, boolean holdFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IoRuntimeException("Destination [" + destFile + "] exists but it is a directory");
        }

        try (FileInputStream in = new FileInputStream(srcFile);
             FileOutputStream out = new FileOutputStream(destFile);
             FileChannel inChannel = in.getChannel();
             FileChannel outChannel = out.getChannel()) {
            long size = inChannel.size(), pos = 0, count;
            while (pos < size) {
                count = Math.min(size - pos, FILE_COPY_BUFFER_SIZE);
                pos += outChannel.transferFrom(inChannel, pos, count);
            }
        }
        //必须放在try(){}之外，否则该修改无效
        if (srcFile.length() != destFile.length()) {
            throw new IOException(String.format("Failed to copy full contents from [%s] to [%s]", srcFile.getPath(), destFile.getPath()));
        }
        if (holdFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    /**
     * 将文件拷贝到目录
     *
     * @param srcFile 原文件
     * @param destDir 目标目录
     */
    public static void copyFileToDirectory(File srcFile, File destDir) {
        copyFileToDirectory(srcFile, destDir, true);
    }

    /**
     * 将文件拷贝到目录
     *
     * @param srcFile      原文件
     * @param destDir      目标目录
     * @param holdFileDate 保持最后修改日期不变
     */
    private static void copyFileToDirectory(File srcFile, File destDir, boolean holdFileDate) {
        checkFileRequirements(srcFile, destDir);
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IoRuntimeException("Destination [" + destDir + "] is not a directory.");
        }

        File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile, holdFileDate);
    }

    /**
     * 将输入流的数据输出到文件中，会自动关闭输入流
     *
     * @param in         输入流,非空
     * @param targetFile 目标文件,非空
     */
    public static void copyStream(@Nullable InputStream in, @Nullable File targetFile) {
        if (targetFile == null) {
            return;
        }
        copyStream(in, targetFile.toPath());
    }

    /**
     * 将输入流的数据输出到文件中，会自动关闭输入流
     *
     * @param in         输入流,非空
     * @param targetPath 目标文件,非空
     */
    public static void copyStream(InputStream in, Path targetPath) {
        if (targetPath == null || in == null) {
            return;
        }
        try {
            java.nio.file.Files.createDirectories(targetPath.getParent());
            java.nio.file.Files.copy(in, targetPath);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            IoUtil.closeQuietly(in);
        }
    }

    // endregion


    // region Make Directory

    /**
     * 创建目录
     *
     * @param directoryPath 目录地址
     */
    public static void forceMakeDir(String directoryPath) {
        forceMakeDir(new File(directoryPath));
    }

    /**
     * 创建目录
     *
     * @param directory 目录地址
     */
    public static void forceMakeDir(File directory) {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new IoRuntimeException("The file[" + directory + "] exists and is not a directory.Unable to create directory.");
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new IoRuntimeException("Unable to create directory[" + directory + "]");
        }
    }

    /**
     * 创建目录
     *
     * @param path 目录地址
     */
    public static void forceMakeDir(Path path) {
        path = path.toAbsolutePath();
        Path parent = path.getParent();
        if (parent != null) {
            if (java.nio.file.Files.notExists(parent)) {
                forceMakeDir(parent);
            }
        }
        try {
            java.nio.file.Files.createDirectory(path);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion


    // region Copy Directory

    /**
     * 目录复制
     *
     * @param srcDir 原目录
     * @param dstDir 目标目录
     */
    public static void copyDirectory(File srcDir, File dstDir) {
        copyDirectory(srcDir, dstDir, true);
    }

    /**
     * 目录复制
     *
     * @param srcDir       原目录
     * @param dstDir       目标目录
     * @param holdFileDate 保持最后修改日期不变
     */
    public static void copyDirectory(File srcDir, File dstDir, boolean holdFileDate) {
        copyDirectory(srcDir, dstDir, holdFileDate, null);
    }

    /**
     * 目录复制
     *
     * @param srcDir       原目录
     * @param dstDir       目标目录
     * @param holdFileDate 保持最后修改日期不变
     * @param filter       文件过滤器
     */
    public static void copyDirectory(File srcDir, File dstDir, boolean holdFileDate, FileFilter filter) {
        checkFileRequirements(srcDir, dstDir);
        if (dstDir.isFile()) {
            throw new IoRuntimeException("Destination [" + dstDir + "] exists but is not a directory.");
        }

        try {
            if (srcDir.getCanonicalPath().equals(dstDir.getCanonicalPath())) {
                throw new IoRuntimeException(String.format("Source [%s] and destination [%s] are the same.", srcDir, dstDir));
            }
            //当目标目录是原目录的子目录时,不支持复制.
            if (dstDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath() + File.separator)) {
                throw new IoRuntimeException(String.format("Destination [%s] is child directory of source [%s].", dstDir, srcDir));
            }
            doCopyDirectory(srcDir, dstDir, holdFileDate, filter);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 目录复制
     *
     * @param srcDir       原目录
     * @param dstDir       目标目录
     * @param holdFileDate 保持最后修改日期不变
     * @param filter       文件过滤器
     * @throws IOException 拷贝异常
     */
    private static void doCopyDirectory(File srcDir, File dstDir, boolean holdFileDate, FileFilter filter) throws IOException {
        File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) {
            throw new IOException("Failed to list contents of [" + srcDir + "]");
        }
        if (dstDir.exists() && !dstDir.isDirectory()) {
            throw new IOException("Destination [" + dstDir + "] exists but is not a directory.");
        }
        if (!dstDir.mkdirs() && !dstDir.isDirectory()) {
            throw new IOException("Destination [" + dstDir + "] directory cannot be created.");
        }
        if (!dstDir.canWrite()) {
            throw new IOException("Destination [" + dstDir + "] cannot be written to.");
        }

        for (File srcFile : srcFiles) {
            File destFile = new File(dstDir, srcFile.getName());
            if (srcFile.isDirectory()) {
                doCopyDirectory(srcFile, destFile, holdFileDate, filter);
            } else {
                doCopyFile(srcFile, destFile, holdFileDate);
            }
        }

        if (holdFileDate) {
            dstDir.setLastModified(srcDir.lastModified());
        }
    }

    // endregion


    // region Move File

    /**
     * 移动文件
     *
     * @param srcFile  原文件
     * @param destFile 目标文件
     * @throws IoRuntimeException 文件处理异常
     */
    public static void moveFile(File srcFile, File destFile) throws IoRuntimeException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IoRuntimeException("Source [" + srcFile + "] is a directory.");
        }
        if (destFile.isFile() && destFile.exists()) {
            throw new IoRuntimeException("Destination [" + destFile + "] already exists.");
        }
        if (destFile.isDirectory() && !destFile.canWrite()) {
            throw new IoRuntimeException("Destination [" + destFile + "] cannot be written to.");
        }

        File targetFile;
        if (destFile.isDirectory()) {
            targetFile = new File(destFile, srcFile.getName());
        } else {
            targetFile = destFile;
        }
        boolean renameTo = srcFile.renameTo(targetFile);
        if (!renameTo) {
            //调用系统的重命名失败(移动),可能属于不同FS文件系统
            copyFile(srcFile, targetFile);
            if (!srcFile.delete()) {
                targetFile.delete();
                throw new IoRuntimeException(String.format("Failed to delete original file [%s], after copy to [%s]", srcFile, destFile));
            }
        }
    }

    /**
     * 移动目录
     *
     * @param source  原文件或目录
     * @param destDir 目标目录
     * @throws IoRuntimeException 文件处理异常
     */
    public static void moveDirectory(File source, File destDir) throws IoRuntimeException {
        moveDirectory(source, destDir, false);
    }

    /**
     * 移动目录
     *
     * @param srcDir  原文件或目录
     * @param destDir 目标目录
     * @param toDir   如果目录不存在，是否创建
     * @throws IoRuntimeException 文件处理异常
     */
    public static void moveDirectory(File srcDir, File destDir, boolean toDir) throws IoRuntimeException {
        checkFileRequirements(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IoRuntimeException("Destination [" + srcDir + "] is not a directory.");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IoRuntimeException("Destination [" + destDir + "] is not a directory.");
        }

        File targetDir = toDir ? new File(destDir, srcDir.getName()) : destDir;

        if (!targetDir.mkdirs()) {
            throw new IoRuntimeException("Directory [" + targetDir + "] could not be created.");
        }
        boolean renameTo = srcDir.renameTo(targetDir);
        if (!renameTo) {
            copyDirectory(srcDir, targetDir);
            delete(srcDir);
            if (srcDir.exists()) {
                throw new IoRuntimeException(String.format("Failed to delete original directory '%s' after copy to '%s'", srcDir, destDir));
            }
        }
    }

    // endregion


    // region Delete File

    /**
     * 删除文件或目录
     *
     * @param file 文件
     * @throws IoRuntimeException 文件处理异常
     */
    public static void delete(File file) throws IoRuntimeException {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            cleanDirectory(file);
        }
        if (!file.delete()) {
            throw new IoRuntimeException("Unable to delete file: " + file);
        }
    }

    /**
     * 删除文件或目录
     *
     * @param path 带删除的文件对象
     */
    public static void delete(Path path) throws IoRuntimeException {
        if (path == null) {
            return;
        }
        if (java.nio.file.Files.notExists(path)) {
            return;
        }
        try {
            if (java.nio.file.Files.isDirectory(path)) {
                java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        java.nio.file.Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        if (exc == null) {
                            java.nio.file.Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                        throw exc;
                    }
                });

            } else {
                java.nio.file.Files.delete(path);
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 清理目录
     *
     * @param directory 目录
     * @throws IoRuntimeException 文件处理异常
     */
    public static void cleanDirectory(File directory) throws IoRuntimeException {
        Asserts.notNull(directory, "Directory must not be null");
        if (!directory.exists()) {
            throw new IoRuntimeException("Directory [" + directory + "] does not exist.");
        }
        if (!directory.isDirectory()) {
            throw new IoRuntimeException("The [" + directory + "] is not a directory.");
        }
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new IoRuntimeException("Failed to list contents of " + directory);
        }
        for (File listFile : listFiles) {
            if (listFile.isDirectory()) {
                cleanDirectory(listFile);
            }
            if (!listFile.delete()) {
                throw new IoRuntimeException("Unable to delete file: " + listFile);
            }
        }
    }

    // endregion


    // region Stream

    /**
     * 打开文件的输入流，提供了比<code>new FileInputStream(file)</code>更好更优雅的方式.
     *
     * @param file 文件
     * @return {@link FileInputStream}
     * @throws IoRuntimeException 文件处理异常
     */
    public static FileInputStream openFileInputStream(File file) throws IoRuntimeException {
        Asserts.notNull(file, "File cannot be null");
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IoRuntimeException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IoRuntimeException("File '" + file + "' cannot be read");
            }
            try {
                return new FileInputStream(file);
            } catch (IOException e) {
                throw new IoRuntimeException(e);
            }
        }
        throw new IoRuntimeException("File '" + file + "' does not exist");
    }

    /**
     * 通过文件获取缓存输入流
     *
     * @param file 文件
     * @return {@linkplain BufferedInputStream}
     */
    public static BufferedInputStream getBufferedInputStream(File file) {
        return IoUtil.getBufferedInputStream(Files.openFileInputStream(file));
    }

    /**
     * 打开件输出流
     *
     * @param file 文件
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream openFileOutputStream(File file) {
        return openFileOutputStream(file, false);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     */
    public static BufferedOutputStream getBufferedOutputStream(final File file) {
        return getBufferedOutputStream(file, false);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file     文件
     * @param isAppend 追加
     * @return 输出流对象
     */
    public static BufferedOutputStream getBufferedOutputStream(final File file, final boolean isAppend) {
        FileOutputStream fileOutputStream = openFileOutputStream(file, isAppend);
        return new BufferedOutputStream(fileOutputStream);
    }

    /**
     * 打开件输出流
     *
     * @param file   文件
     * @param append 附加
     * @return {@link FileOutputStream}
     */
    private static FileOutputStream openFileOutputStream(File file, boolean append) throws IoRuntimeException {
        Asserts.notNull(file, "File cannot be null");
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IoRuntimeException("Destination [" + file + "] exists but is a directory.");
            }
            if (!file.canWrite()) {
                throw new IoRuntimeException(String.format("Destination [%s] exists but cannot write.", file));
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IoRuntimeException("Directory [" + parent + "] could not be created.");
                }
            }
        }
        try {
            return new FileOutputStream(file, append);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    //endregion


    // region compare

    /**
     * 比对两个文件的路径是否相同：根据绝对路径对比，在Windows下忽略大小写，在Linux下则区分大小写
     *
     * @param src  文件
     * @param dest 文件
     * @return 比较结果
     */
    public static boolean pathEquals(File src, File dest) {
        if (Objects.isAnyNull(src, dest)) {
            return false;
        }
        String str1, str2;
        try {
            str1 = src.getCanonicalPath();
            str2 = dest.getCanonicalPath();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }

        //Windows
        if (isWindows()) {
            return Strings.equalsIgnoreCase(str1, str2);
        }

        // Like Unix
        return str1.equals(str2);
    }

    // endregion


}
