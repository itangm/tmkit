package cn.tmkit.core.io;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.lang.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class Files extends Paths {

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
    public static @NotNull File touch(@NotNull String pathname) {
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
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void touch(File file) {
        if (Objects.isNotNull(file)) {
            if (!file.exists()) {
                mkdir(file.getParentFile());
                try {
                    file.createNewFile();
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
    public static File createTmpFile(String prefix) throws IoRuntimeException {
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
    public static File createTmpFile(String prefix, String suffix) throws IoRuntimeException {
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
            mkdir(dir);
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
            return fileSize + " bytes";
        }
        // KB
        size /= 1024;
        if (size < 1024) {
            // 字符串格式化支持float和double，并且支持四舍五入
            return String.format("%.2f KB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f MB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f GB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f TB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f PB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f EB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f ZB", size);
        }
        size /= 1024;
        if (size < 1024) {
            return String.format("%.2f YB", size);
        }
        return String.format("%.2f BB", size);
    }

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为{@code byte}
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
        if (Objects.isNull(file)) {
            return null;
        }
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
        return determineFileFormat(file);
    }

    /**
     * 获取真实文件类型，读取文件头N个字节
     *
     * @param file 文件
     * @return 文件后缀
     */
    public static String determineFileFormat(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream in = null;
        try {
            in = openFileInputStream(file);
            return IoUtil.determineFileFormat(in);
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
        String[] pathArray = Strings.splitToArr(pathTemp, Chars.SLASH);
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
     *
     * <p><a href="https://www.freebuf.com/vuls/180383.html">Zip Slip任意文件覆盖漏洞分析</a></p>
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

    // region 读文件

    // region 读文本文件

    /**
     * 将文件的内容全部读取,采用系统默认编码
     *
     * @param path 待读的文件
     * @return 文件内容
     */
    public static List<String> readLines(String path) throws IoRuntimeException {
        return readLines(path, (String) null);
    }

    /**
     * 将文件的内容全部读取
     *
     * @param path    待读的文件
     * @param charset 字符集，如果为空则为系统默认编码
     * @return 文件内容
     */
    public static List<String> readLines(String path, String charset) throws IoRuntimeException {
        return readLines(path, Charsets.forName(charset));
    }

    /**
     * 将文件的内容全部读取
     *
     * @param path    待读的文件
     * @param charset 字符集，如果为空则为系统默认编码
     * @return 文件内容
     */
    public static List<String> readLines(String path, Charset charset) throws IoRuntimeException {
        if (Strings.isBlank(path)) {
            return null;
        }
        return readLines(new File(path), charset);
    }

    /**
     * 将文件的内容全部读取,采用系统默认编码
     *
     * @param file 待读的文件
     * @return 文件内容
     */
    public static List<String> readLines(final File file) throws IoRuntimeException {
        return readLines(file, null);
    }

    /**
     * 将文件的内容全部读取,采用{@code UTF-8}编码
     *
     * @param file 待读的文件
     * @return 文件内容
     */
    public static List<String> readUtf8Lines(final File file) throws IoRuntimeException {
        return readLines(file, Charsets.UTF_8);
    }

    /**
     * 将文件的内容全部读取
     *
     * @param file    待读的文件
     * @param charset 字符编码，如果为空则为系统编码编码
     * @return 文件内容
     */
    public static List<String> readLines(File file, Charset charset) throws IoRuntimeException {
        FileInputStream fis = null;
        try {
            fis = openFileInputStream(file);
            return IoUtil.readLines(fis, charset);
        } finally {
            IoUtil.closeQuietly(fis);
        }
    }

    /**
     * 读取文本类型文件内容
     *
     * @param path 待读取的文件路径
     * @return 返回文件内容
     */
    public static String readStr(String path) throws IoRuntimeException {
        return readStr(path, (String) null);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param path    待读取的文件路径
     * @param charset 字符集，如果为空则为系统默认编码
     * @return 返回文件内容
     */
    public static String readStr(String path, String charset) throws IoRuntimeException {
        return readStr(path, Charsets.forName(charset));
    }

    /**
     * 读取文本类型文件内容
     *
     * @param path    待读取的文件路径
     * @param charset 字符集，如果为空则为系统默认编码
     * @return 返回文件内容
     */
    public static String readStr(String path, Charset charset) throws IoRuntimeException {
        if (Strings.isBlank(path)) {
            return null;
        }
        return readStr(new File(path), charset);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file 文件对象
     * @return 返回文件内容
     */
    public static String readStr(File file) throws IoRuntimeException {
        return readStr(file, (Charset) null);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file    文件对象
     * @param charset 字符集，如果为空则为系统编码编码
     * @return 返回文件内容
     */
    public static String readStr(File file, String charset) throws IoRuntimeException {
        return readStr(file, Charsets.forName(charset));
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file    文件对象
     * @param charset 字符集
     * @return 返回文件内容
     */
    public static String readStr(File file, Charset charset) throws IoRuntimeException {
        try (FileInputStream in = openFileInputStream(file)) {
            return IoUtil.read(in, charset);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 读取文本类型文件内容
     *
     * @param path 待读取的文件路径
     * @return 返回文件内容
     */
    public static String readUtf8Str(String path) throws IoRuntimeException {
        if (Strings.isBlank(path)) {
            return null;
        }
        return readStr(new File(path), Charsets.UTF_8);
    }

    /**
     * 读取文本类型文件内容
     *
     * @param file 文件对象
     * @return 返回文件内容
     */
    public static String readUtf8Str(File file) throws IoRuntimeException {
        return readStr(file, Charsets.UTF_8);
    }

    // endregion

    // region 读二进制文件

    /**
     * 读取文件的二进制内容
     *
     * @param path 待读取的文件路径
     * @return 文件内容
     * @throws IoRuntimeException 读取文件异常
     */
    public static byte[] readBytes(String path) throws IoRuntimeException {
        if (Strings.isBlank(path)) {
            return null;
        }
        return readBytes(new File(path));
    }

    /**
     * 读取文件的二进制内容
     *
     * @param file 文件，非空
     * @return 文件内容
     * @throws IoRuntimeException 读取文件异常
     */
    public static byte[] readBytes(File file) throws IoRuntimeException {
        try (FileInputStream in = openFileInputStream(file)) {
            FastByteArrayOutputStream output = new FastByteArrayOutputStream();
            IoUtil.copy(in, output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 读取文件内容并转为{@linkplain ByteArrayInputStream}
     *
     * @param path 待读取的文件路径
     * @return {@linkplain ByteArrayInputStream}
     * @throws IoRuntimeException IO异常
     */
    public static ByteArrayInputStream readByteStream(String path) throws IoRuntimeException {
        if (Strings.isBlank(path)) {
            return null;
        }
        return readByteStream(new File(path));
    }

    /**
     * 读取文件内容并转为{@linkplain ByteArrayInputStream}
     *
     * @param file 文件对象
     * @return {@linkplain ByteArrayInputStream}
     * @throws IoRuntimeException IO异常
     */
    public static ByteArrayInputStream readByteStream(File file) throws IoRuntimeException {
        try (FileInputStream in = openFileInputStream(file)) {
            return IoUtil.toByteArrayInputStream(in);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

    // endregion


    // region 写文件

    // region 写文本文件

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines 待写入的内容
     * @param path  文件路径
     * @return 文件对象
     */
    public static File writeUtf8Lines(Collection<? extends CharSequence> lines, String path) throws IoRuntimeException {
        return writeUtf8Lines(lines, path, false);
    }

    /**
     * 将集合字符串一行行写入文件中
     *
     * @param lines    待写入的内容
     * @param path     文件路径
     * @param isAppend 是否追加内容
     * @return 文件对象
     */
    public static File writeUtf8Lines(Collection<? extends CharSequence> lines, String path, boolean isAppend) throws IoRuntimeException {
        File file = file(path);
        writeUtf8Lines(lines, file, isAppend);
        return file;
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines 待写入的内容
     * @param file  文件对象
     */
    public static void writeUtf8Lines(Collection<? extends CharSequence> lines, File file) throws IoRuntimeException {
        writeUtf8Lines(lines, file, false);
    }

    /**
     * 将集合字符串一行行写入文件中
     *
     * @param lines    待写入的内容
     * @param file     文件对象
     * @param isAppend 是否追加内容
     */
    public static void writeUtf8Lines(Collection<? extends CharSequence> lines, File file, boolean isAppend) throws IoRuntimeException {
        writeLines(lines, file, Charsets.UTF_8, isAppend);
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines   待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件对象
     */
    public static File writeLines(Collection<? extends CharSequence> lines, String path, String charset) throws IoRuntimeException {
        File file = file(path);
        writeLines(lines, file, charset, false);
        return file;
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines   待写入的内容
     * @param file    文件对象
     * @param charset 字符集
     */
    public static void writeLines(Collection<? extends CharSequence> lines, File file, String charset) throws IoRuntimeException {
        writeLines(lines, file, charset, false);
    }

    /**
     * 将集合字符串一行行写入文件中
     *
     * @param lines    待写入的内容
     * @param path     文件路径
     * @param charset  字符集
     * @param isAppend 是否追加内容
     * @return 文件对象
     */
    public static File writeLines(Collection<? extends CharSequence> lines, String path, String charset, boolean isAppend) throws IoRuntimeException {
        File file = file(path);
        writeLines(lines, file, CharsetUtil.forName(charset), isAppend);
        return file;
    }

    /**
     * 将集合字符串一行行写入文件中
     *
     * @param lines    待写入的内容
     * @param file     文件对象
     * @param charset  字符集
     * @param isAppend 是否追加内容
     */
    public static void writeLines(Collection<? extends CharSequence> lines, File file, String charset, boolean isAppend) throws IoRuntimeException {
        writeLines(lines, file, CharsetUtil.forName(charset), isAppend);
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines   待写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件对象
     */
    public static File writeLines(Collection<? extends CharSequence> lines, String path, Charset charset) throws IoRuntimeException {
        File file = file(path);
        writeLines(lines, file, charset, false);
        return file;
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines   待写入的内容
     * @param file    文件对象
     * @param charset 字符集
     */
    public static void writeLines(Collection<? extends CharSequence> lines, File file, Charset charset) throws IoRuntimeException {
        writeLines(lines, file, charset, false);
    }

    /**
     * 将集合字符串一行行写入文件中，原内容将会呗覆盖
     *
     * @param lines   待写入的内容
     * @param file    文件对象
     * @param charset 字符集
     */
    public static void writeLines(Collection<? extends CharSequence> lines, File file, Charset charset, boolean isAppend) throws IoRuntimeException {
        if (Objects.isAnyNull(lines, file)) {
            return;
        }
        Charset encoding = Charsets.getCharset(charset);
        try (BufferedWriter bufferedWriter = getWriter(file, encoding, isAppend);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            lines.forEach(line -> printWriter.println(line == null ? "" : line));
            // 最后刷盘
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param path    文件路径
     * @return 写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static File writeUtf8Str(CharSequence content, String path) throws IoRuntimeException {
        File file = file(path);
        writeUtf8Str(content, file);
        return file;
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void writeUtf8Str(CharSequence content, File file) throws IoRuntimeException {
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
    public static File write(CharSequence content, String path, String charset) throws IoRuntimeException {
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
    public static File write(CharSequence content, String path, Charset charset) throws IoRuntimeException {
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
    public static void write(CharSequence content, File file, String charset) throws IoRuntimeException {
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
    public static void write(CharSequence content, File file, Charset charset) throws IoRuntimeException {
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
    public static File appendUtf8Str(CharSequence content, String path) throws IoRuntimeException {
        return append(content, path, Charsets.UTF_8);
    }

    /**
     * 将内容写入到文件，原内容被覆盖，默认采用UTF-8字符集
     *
     * @param content 待写入的内容
     * @param file    写入的文件
     * @throws IoRuntimeException IO异常
     */
    public static void appendUtf8Str(CharSequence content, File file) throws IoRuntimeException {
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
    public static File append(CharSequence content, String path, String charset) throws IoRuntimeException {
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
    public static File append(CharSequence content, String path, Charset charset) throws IoRuntimeException {
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
    public static void append(CharSequence content, File file, String charset) throws IoRuntimeException {
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
    public static void append(CharSequence content, File file, Charset charset) throws IoRuntimeException {
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
    private static void doWrite(CharSequence content, File file, Charset charset, boolean isAppend) throws IoRuntimeException {
        if (Objects.isAnyNull(content, file)) {
            return;
        }
        try (BufferedWriter bufferedWriter = getWriter(file, charset, isAppend)) {
            bufferedWriter.write(content.toString());
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

    // region 写二进制文件

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

    // endregion


    // region 复制文件或文件夹

    /**
     * 文件内容拷贝指定的输出流中
     *
     * @param srcFile 原文件
     * @param output  输出流
     */
    public static void copy(File srcFile, OutputStream output) {
        try (BufferedInputStream in = openBufferedInputStream(srcFile)) {
            IoUtil.copy(in, output);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 文件拷贝
     *
     * @param source 源路径
     * @param target 目标路径
     */
    public static void copy(File source, File target) {
        copy(source, target, false);
    }

    /**
     * 文件拷贝
     *
     * @param source     源路径
     * @param target     目标路径
     * @param isOverride 是否覆盖
     */
    public static void copy(File source, File target, boolean isOverride) {
        if (Objects.isAllNotNull(source, target)) {
            copy(source.toPath(), target.toPath(), isOverride);
        }
    }

    /**
     * 将输入流的数据输出到文件中，会自动关闭输入流
     *
     * @param in         输入流,非空
     * @param targetFile 目标文件,非空
     */
    public static void copyStream(InputStream in, File targetFile) {
        if (targetFile == null) {
            return;
        }
        copy(in, targetFile.toPath());
    }

    // endregion


    // region 创建文件夹

    /**
     * 创建目录
     *
     * @param directoryPath 目录地址
     */
    public static void mkdir(String directoryPath) {
        if (Strings.isNotBlank(directoryPath)) {
            mkdir(new File(directoryPath));
        }
    }

    /**
     * 创建目录
     *
     * @param directory 目录地址
     */
    public static void mkdir(File directory) {
        if (Objects.nonNull(directory)) {
            if (directory.exists()) {
                if (!directory.isDirectory()) {
                    throw new IoRuntimeException("The file exists but not a directory,Unable to create directory.");
                }
            } else if (!directory.mkdirs() && !directory.isDirectory()) {
                throw new IoRuntimeException("Unable to create directory[" + directory + "]");
            }
        }
    }

    // endregion


    // region 移动文件或文件夹

    /**
     * 移动文件或目录，默认文件存在
     *
     * <p>如果{@code dest}是目录，则{@code src}可以为文件或目录；如果{@code dest}是文件，则{@code src}则必须为文件</p>
     *
     * @param src  源文件或源目录
     * @param dest 目标文件或目录
     */
    public static void mv(File src, File dest) {
        mv(src, dest, false);
    }

    /**
     * 移动文件或目录，默认文件存在
     *
     * @param src        源文件或源目录
     * @param dest       目标文件或目录
     * @param isOverride 是否覆盖
     */
    public static void mv(File src, File dest, boolean isOverride) {
        if (Arrays.isAnyNull(src, dest)) {
            return;
        }
        mv(src.toPath(), dest.toPath(), isOverride);
    }

    // endregion


    // region 删除文件

    /**
     * 删除文件或目录
     *
     * @param pathname 文件路径
     * @throws IoRuntimeException 文件处理异常
     */
    public static void del(String pathname) {
        File file = file(pathname);
        del(file);
    }

    /**
     * 删除文件或目录
     *
     * @param file 文件
     * @throws IoRuntimeException 文件处理异常
     */
    public static void del(File file) throws IoRuntimeException {
        delete(file);
    }

    /**
     * 删除文件或目录
     *
     * @param file 文件
     * @throws IoRuntimeException 文件处理异常
     */
    public static void rm(File file) throws IoRuntimeException {
        delete(file);
    }

    /**
     * 删除文件或目录
     *
     * @param pathname 文件路径
     * @throws IoRuntimeException 文件处理异常
     */
    public static void delete(String pathname) {
        del(pathname);
    }

    /**
     * 删除文件或目录
     *
     * @param file 文件
     * @throws IoRuntimeException 文件处理异常
     */
    public static void delete(File file) throws IoRuntimeException {
        if (Objects.isNull(file) || !file.exists()) {
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
     * 清理目录
     *
     * @param directory 目录
     * @throws IoRuntimeException 文件处理异常
     */
    public static void cleanDirectory(File directory) throws IoRuntimeException {
        if (Objects.isNull(directory) || !directory.exists() || !directory.isDirectory()) {
            return;
        }
        File[] listFiles = directory.listFiles();
        if (Arrays.isEmpty(listFiles)) {
            return;
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


    // region 打开输入流

    /**
     * 打开文件的输入流，提供了比<code>new FileInputStream(file)</code>更好更优雅的方式.
     *
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream getFileInputStream(File file) {
        return openFileInputStream(file);
    }

    /**
     * 打开文件的输入流，提供了比<code>new FileInputStream(file)</code>更好更优雅的方式.
     *
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream openFileInputStream(File file) {
        if (Objects.isNull(file)) {
            return null;
        }
        if (!file.exists()) {
            throw new IoRuntimeException("The file does not exist");
        }
        if (file.isDirectory()) {
            throw new IoRuntimeException("The file exists but is a directory");
        }
        if (!file.canRead()) {
            throw new IoRuntimeException("The file cannot be read");
        }
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 通过文件获取缓存输入流
     *
     * @param file 文件
     * @return {@linkplain BufferedInputStream}
     */
    public static BufferedInputStream getBufferedInputStream(File file) {
        return IoUtil.getBufferedInputStream(getFileInputStream(file));
    }

    /**
     * 通过文件获取缓存输入流
     *
     * @param file 文件
     * @return {@linkplain BufferedInputStream}
     */
    public static BufferedInputStream openBufferedInputStream(File file) {
        return getBufferedInputStream(file);
    }

    // endregion


    // region 打开输出流

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
     * 打开件输出流
     *
     * @param file     文件
     * @param isAppend 附加
     * @return {@link FileOutputStream}
     */
    private static FileOutputStream openFileOutputStream(File file, boolean isAppend) {
        return getFileOutputStream(file, isAppend);
    }

    /**
     * 打开件输出流
     *
     * @param file 文件
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(File file) {
        return openFileOutputStream(file);
    }

    /**
     * 打开件输出流
     *
     * @param file     文件
     * @param isAppend 附加
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(File file, boolean isAppend) {
        if (Objects.isNull(file)) {
            return null;
        }
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
            return new FileOutputStream(file, isAppend);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
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
        return openBufferedOutputStream(file, isAppend);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     */
    public static BufferedOutputStream openBufferedOutputStream(File file) {
        return getBufferedOutputStream(file);
    }

    /**
     * 获得一个输出流对象
     *
     * @param file     文件
     * @param isAppend 追加
     * @return 输出流对象
     */
    public static BufferedOutputStream openBufferedOutputStream(final File file, final boolean isAppend) {
        FileOutputStream fos = openFileOutputStream(file, isAppend);
        return IoUtil.toBuffered(fos);
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
