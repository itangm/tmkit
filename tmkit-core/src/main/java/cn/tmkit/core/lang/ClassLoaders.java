package cn.tmkit.core.lang;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.exception.UriSyntaxRuntimeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 类加载器工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-29
 */
public class ClassLoaders {

    /**
     * 返回一个默认的类加载器：首先会从当前线程获取类加载，如果获取失败则获取当前类的类加载器。
     *
     * @return 返回类类加载器
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = getContextClassLoader();
        } catch (Throwable e) {
            //无法从当前线程获取到类加载器
        }
        if (classLoader == null) {
            //从当前类的加载器
            classLoader = ClassLoaders.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程类加载器
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据资源地址获取路径地址
     *
     * @param resourceName 资源地址
     * @return 路径地址，如果不存在则返回{@code null}
     */
    public static String getFilePath(String resourceName) {
        File file = getFile(resourceName);
        try {
            return file == null ? null : file.getCanonicalPath();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 根据资源地址获取路径地址
     *
     * @param resourceName 资源地址
     * @return 路径地址，如果不存在则返回{@code null}
     */
    public static File getFile(String resourceName) {
        if (Objects.isNull(resourceName)) {
            return null;
        }
        URL resource = getDefaultClassLoader().getResource(resourceName);
        if (resource == null) {
            return null;
        }
        try {
            File file = new File(resource.toURI());
            return file.getCanonicalFile();
        } catch (URISyntaxException e) {
            throw new UriSyntaxRuntimeException(e);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 根据资源地址获取{@linkplain URL}
     *
     * @param resourceName 资源地址
     * @return {@linkplain URL}
     */
    public static URL getUrl(String resourceName) {
        return getDefaultClassLoader().getResource(resourceName);
    }

    public static InputStream getInputStream(String resourceName) {
        if (Objects.isNull(resourceName)) {
            return null;
        }
        return getDefaultClassLoader().getResourceAsStream(resourceName);
    }

}
