package cn.tmkit.core.io;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.exception.UriSyntaxRuntimeException;
import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;

/**
 * {@linkplain Path}工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-16
 */
public class Paths {

    // region 未分组

    /**
     * 获取{@link Path}文件名
     *
     * @param path {@link Path}
     * @return 文件名
     */
    public static String getName(Path path) {
        if (Objects.isNull(path)) {
            return null;
        }
        return path.getFileName().toString();
    }

    // endregion


    // region 文件或目录是否存在

    /**
     * 检测文件是否存在，如果{@code path}为{@code null}则返回{@code false}
     *
     * @param path 文件路径对象
     * @return 是否存在
     * @see java.nio.file.Files#exists(Path, LinkOption...)
     */
    public static boolean exists(Path path) {
        if (Objects.isNull(path)) {
            return false;
        }
        return java.nio.file.Files.exists(path);
    }

    /**
     * 检测文件是否不存在，如果{@code path}为{@code null}则返回{@code true}
     *
     * <p>注意与{@linkplain #exists(Path)}不完全等价，而本方法会解析{@linkplain java.nio.file.NoSuchFileException}异常</p>
     *
     * @param path 文件路径对象
     * @return 是否不存在
     * @see java.nio.file.Files#notExists(Path, LinkOption...)
     */
    public static boolean notExists(Path path) {
        if (Objects.isNull(path)) {
            return true;
        }
        return java.nio.file.Files.notExists(path);
    }

    /**
     * 判断目录是否为空的目录
     *
     * @param dir 目录，如果为{@code null}则为{@code  false}
     * @return 是否为空目录
     */
    public static boolean isDirEmpty(Path dir) {
        if (Objects.isNull(dir)) {
            return false;
        }
        try (DirectoryStream<Path> ds = java.nio.file.Files.newDirectoryStream(dir)) {
            return !ds.iterator().hasNext();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion


    // region 是否为文件或目录

    /**
     * 判断是否为文件，如果{@code path}为{@code null}则返回{@code false}
     *
     * <p>如果文件是快捷方式（软链接），本方法不会追踪到实际文件地址</p>
     *
     * @param path 文件
     * @return 是否为文件
     */
    public static boolean isFile(Path path) {
        return isFile(path, false);
    }

    /**
     * 判断是否为文件，如果{@code path}为{@code null}则返回{@code false}
     *
     * @param path          文件
     * @param isFollowLinks 是否追踪快捷方式（软链接），即真实文件地址
     * @return 是否为文件
     */
    public static boolean isFile(Path path, boolean isFollowLinks) {
        if (Objects.isNull(path)) {
            return false;
        }
        LinkOption[] linkOptions = isFollowLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        return java.nio.file.Files.isRegularFile(path, linkOptions);
    }

    /**
     * 判断是否为目录，如果{@code file}为{@code null}，则返回{@code false}
     *
     * <p>本方法，不会追踪到快捷方式（软链接）的真是地址，即将快捷方式（软链接）当作文件处理的。</p>
     *
     * @param path 文件对象
     * @return 如果是目录返回{@code true}
     */
    public static boolean isDir(Path path) {
        return isDir(path, false);
    }

    /**
     * 判断是否为目录，如果{@code file}为{@code null}，则返回{@code false}
     *
     * @param path          文件对象
     * @param isFollowLinks 是否追踪快捷方式（软链接），即真实文件地址
     * @return 如果是目录返回{@code true}
     */
    public static boolean isDir(Path path, boolean isFollowLinks) {
        return isDirectory(path, isFollowLinks);
    }

    /**
     * 判断是否为目录，如果{@code file}为{@code null}，则返回{@code false}
     *
     * <p>本方法，不会追踪到快捷方式（软链接）的真是地址，即将快捷方式（软链接）当作文件处理的。</p>
     *
     * @param path 文件对象
     * @return 如果是目录返回{@code true}
     */
    public static boolean isDirectory(Path path) {
        return isDirectory(path, false);
    }

    /**
     * 判断是否为目录，如果{@code file}为{@code null}，则返回{@code false}
     *
     * @param path          文件对象
     * @param isFollowLinks 是否追踪快捷方式（软链接），即真实文件地址
     * @return 如果是目录返回{@code true}
     * @see java.nio.file.Files#isDirectory(Path, LinkOption...)
     */
    public static boolean isDirectory(Path path, boolean isFollowLinks) {
        if (Objects.isNull(path)) {
            return false;
        }
        LinkOption[] linkOptions = isFollowLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        return java.nio.file.Files.isDirectory(path, linkOptions);
    }

    // endregion


    // region 创建文件或目录

    /**
     * 创建{@linkplain Path}对象
     *
     * @param path 路径
     * @return {@linkplain Path}
     */
    public static Path get(String path) {
        return newPath(path);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param path 路径
     * @return {@linkplain Path}
     */
    public static Path newPath(String path) {
        return get(path, null);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param path    路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path get(String path, String subPath) {
        return newPath(path, subPath);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param path    路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path newPath(String path, String subPath) {
        if (Strings.isBlank(path)) {
            return null;
        }
        String[] more = new String[1];
        if (Strings.isNotBlank(subPath)) {
            more[0] = subPath;
        }
        return java.nio.file.Paths.get(path, more);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param url 路径
     * @return {@linkplain Path}
     */
    public static Path get(URL url) {
        return newPath(url);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param url 路径
     * @return {@linkplain Path}
     */
    public static Path newPath(URL url) {
        return get(url, null);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param url     路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path newPath(URL url, String subPath) {
        if (Objects.isNull(url)) {
            return null;
        }
        try {
            return newPath(url.toURI(), subPath);
        } catch (URISyntaxException e) {
            throw new UriSyntaxRuntimeException(e);
        }
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param url     路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path get(URL url, String subPath) {
        return newPath(url, subPath);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param uri 路径
     * @return {@linkplain Path}
     */
    public static Path get(URI uri) {
        return newPath(uri);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param uri 路径
     * @return {@linkplain Path}
     */
    public static Path newPath(URI uri) {
        return get((uri), null);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param uri     路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path get(URI uri, String subPath) {
        return newPath(uri, subPath);
    }

    /**
     * 创建{@linkplain Path}对象
     *
     * @param uri     路径
     * @param subPath 子路径
     * @return {@linkplain Path}
     */
    public static Path newPath(URI uri, String subPath) {
        if (Objects.isNull(uri)) {
            return null;
        }
        Path path = java.nio.file.Paths.get(uri);
        if (Strings.isNotBlank(subPath)) {
            return path.resolve(subPath);
        }
        return path;
    }

    /**
     * 创建目录和其父目录
     *
     * @param dir 文件目录
     */
    public static void mkdir(Path dir) {
        if (Objects.isNull(dir) || exists(dir)) {
            return;
        }
        try {
            java.nio.file.Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

    // region 获取输入/输出流

    /**
     * 获得输出流
     *
     * @param path 文件路径
     * @return 输出流
     */
    public static BufferedOutputStream getBufferedOutputStream(Path path) {
        if (Objects.isNull(path)) {
            return null;
        }
        try {
            return IoUtil.getBufferedOutputStream(java.nio.file.Files.newOutputStream(path));
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得输出流
     *
     * @param path 文件路径
     * @return 输出流
     */
    public static BufferedOutputStream newBufferedOutputStream(Path path) {
        return getBufferedOutputStream(path);
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     */
    public static BufferedInputStream getBufferedInputStream(Path path) {
        if (Objects.isNull(path)) {
            return null;
        }
        try {
            return IoUtil.toBuffered(java.nio.file.Files.newInputStream(path));
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     */
    public static BufferedInputStream newBufferedInputStream(Path path) {
        return getBufferedInputStream(path);
    }

    // endregion

    // region
    // endregion

    // region 遍历处理文件

    /**
     * 递归遍历目录以及子目录中的所有文件，如果{@code path}为文件，直接返回过滤结果
     *
     * @param path 需遍历文件或目录
     * @return 文件列表
     */
    public static List<File> listFiles(Path path) {
        return listFiles(path, null);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件，如果{@code path}为文件，直接返回过滤结果
     *
     * @param path       需遍历文件或目录
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null表示接收全部文件
     * @return 文件列表
     */
    public static List<File> listFiles(Path path, FileFilter fileFilter) {
        return listFiles(path, -1, fileFilter);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件，如果{@code path}为文件，直接返回过滤结果
     *
     * @param path       需遍历文件或目录
     * @param maxDepth   遍历最大深度，-1表示遍历到没有目录为止
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null表示接收全部文件
     * @return 文件列表
     */
    public static @NotNull List<File> listFiles(Path path, int maxDepth, FileFilter fileFilter) {
        List<File> list = Collections.arrayList();
        if (Objects.isNull(path) || !exists(path)) {
            return list;
        }
        if (isFile(path)) {
            File file = path.toFile();
            if (fileFilter == null || fileFilter.accept(file)) {
                list.add(file);
            }
            return list;
        }
        // 目录
        walkFiles(path, maxDepth, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                File file = path.toFile();
                if (fileFilter == null || fileFilter.accept(file)) {
                    list.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return list;
    }

    /**
     * 递归遍历{@code start}下的文件并按照{@code fileVisitor}处理
     *
     * @param start       待递归的根目录
     * @param fileVisitor 如果为{@code null}则直接终止
     */
    public static void walkFiles(Path start, FileVisitor<? super Path> fileVisitor) {
        walkFiles(start, -1, fileVisitor);
    }

    /**
     * 递归遍历{@code start}下的文件并按照{@code fileVisitor}处理
     *
     * @param start       待递归的根目录
     * @param maxDepth    最大遍历深度，-1代表不限制深度
     * @param fileVisitor 文件的操作逻辑，如果为{@code null}则直接终止
     */
    public static void walkFiles(Path start, int maxDepth, FileVisitor<? super Path> fileVisitor) {
        if (Objects.isAnyNull(start, fileVisitor)) {
            return;
        }
        if (maxDepth < 0) {
            maxDepth = Integer.MAX_VALUE;
        }
        try {
            java.nio.file.Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), maxDepth, fileVisitor);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

    // region 复制文件或目录

    /**
     * 文件或目录复制到目标文件
     *
     * @param source 源路径
     * @param target 目标路径
     */
    public static void cp(Path source, Path target) {
        cp(source, target, false);
    }

    /**
     * 文件或目录复制到目标文件
     *
     * @param source     源路径
     * @param target     目标路径
     * @param isOverride 是否覆盖已存在的
     */
    public static void cp(Path source, Path target, boolean isOverride) {
        copy(source, target, isOverride);
    }

    /**
     * 文件或目录复制到目标文件
     *
     * @param source 源路径
     * @param target 目标路径
     */
    public static void copy(Path source, Path target) {
        cp(source, target);
    }

    /**
     * 文件或目录复制到目标文件
     *
     * @param source     源路径
     * @param target     目标路径
     * @param isOverride 是否覆盖已存在的
     */
    public static void copy(Path source, Path target, boolean isOverride) {
        if (Arrays.isAnyNull(source, target)) {
            return;
        }
        if (!exists(source)) {
            throw new IoRuntimeException("The path[source] does not exist");
        }
        if (notExists(target)) {
            mkdir(target);
        }
        // 如果目标是文件，则源路径必须也是文件
        if (isFile(target) && isDirectory(source)) {
            throw new IoRuntimeException("The Path[target] is a file,but the Path[source] is a directory");
        }
        CopyOption[] copyOptions = isOverride ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        try {
            java.nio.file.Files.copy(source, target, copyOptions);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将输入流的数据输出到文件中，会自动关闭输入流
     *
     * @param in     输入流,非空
     * @param target 目标文件,非空
     */
    public static void copy(InputStream in, Path target) {
        if (Arrays.isAnyNull(in, target)) {
            return;
        }
        try {
            mkdir(target.getParent());
            java.nio.file.Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            IoUtil.closeQuietly(in);
        }
    }

    // endregion


    // region 移动文件或目录

    /**
     * 移动文件或目录
     *
     * @param source 源路径
     * @param target 目标路径
     */
    public static void mv(Path source, Path target) {
        move(source, target, false);
    }

    /**
     * 移动文件或目录
     *
     * @param source 源路径
     * @param target 目标路径
     */
    public static void move(Path source, Path target) {
        mv(source, target);
    }

    /**
     * 移动文件或目录
     *
     * @param source     源路径
     * @param target     目标路径
     * @param isOverride 是否覆盖已存在的文件
     */
    public static void mv(Path source, Path target, boolean isOverride) {
        if (Objects.isAnyNull(source, target)) {
            return;
        }
        if (!exists(source)) {
            throw new IoRuntimeException("Teh path[source] does not exist");
        }
        if (notExists(target)) {
            mkdir(target);
        }
        // 如果目标是文件，则源路径必须也是文件
        if (isFile(target) && isDirectory(source)) {
            throw new IoRuntimeException("The Path[target] is a file,but the Path[source] is a directory");
        }
        CopyOption[] copyOptions = isOverride ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        try {
            java.nio.file.Files.move(source, target, copyOptions);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 移动文件或目录
     *
     * @param source     源路径
     * @param target     目标路径
     * @param isOverride 是否覆盖已存在的文件
     */
    public static void move(Path source, Path target, boolean isOverride) {
        mv(source, target, isOverride);
    }

    // endregion


    // region 删除文件或目录

    /**
     * 删除文件或目录
     *
     * @param path 文件对象
     * @return 删除结果
     * @see #delete(Path)
     */
    public static boolean rm(Path path) {
        return delete(path);
    }

    /**
     * 删除文件或目录
     *
     * @param path 文件对象
     * @return 删除结果
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean delete(Path path) {
        if (notExists(path)) {
            return true;
        }
        try {
            if (isDir(path)) {
                java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                        if (e != null) {
                            throw e;
                        }
                        java.nio.file.Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                java.nio.file.Files.delete(path);
            }
            return true;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    // endregion

}
