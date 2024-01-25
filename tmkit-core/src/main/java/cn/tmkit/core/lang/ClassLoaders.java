package cn.tmkit.core.lang;

import cn.tmkit.core.exception.ClassNotFoundRuntimeException;
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
@SuppressWarnings("unchecked")
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

    /**
     * 根据资源地址获取{@linkplain InputStream}
     *
     * @param resourceName 资源地址
     * @return {@linkplain InputStream}
     */
    public static InputStream getInputStream(String resourceName) {
        if (Objects.isNull(resourceName)) {
            return null;
        }
        return getDefaultClassLoader().getResourceAsStream(resourceName);
    }

    /**
     * 判断类是否可用
     *
     * @param className 类名
     * @return 是否可用
     */
    public static boolean isPresent(String className) {
        return isPresent(className, null);
    }

    /**
     * 判断类是否可用
     *
     * @param className   类名
     * @param classLoader 类加载器
     * @return 是否可用
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        if (Objects.isNull(className)) {
            return false;
        }
        try {
            loadClass(className, false, classLoader);
            return true;
        } catch (ClassNotFoundRuntimeException e) {
            return false;
        }
    }

    // region 加载类

    /**
     * 加载类并初始化类（static模块和属性
     *
     * @param className 类名
     * @param <T>泛型类
     * @return 对应的类型
     * @see #loadClass(String, boolean, ClassLoader)
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 加载类
     *
     * @param className  类名
     * @param initialize 是否初始化类
     * @param <T>        类的泛型
     * @return 对应的类型
     * @see #loadClass(String, boolean, ClassLoader)
     */
    public static <T> Class<T> loadClass(String className, boolean initialize) {
        return loadClass(className, initialize, null);
    }

    /**
     * 加载类
     *
     * @param className   类名
     * @param initialize  是否初始化类
     * @param classLoader 类加载器
     * @param <T>         类的泛型
     * @return 对应的类型
     * @see Class#forName(String, boolean, ClassLoader)
     */
    private static <T> Class<T> loadClass(String className, boolean initialize, ClassLoader classLoader) {
        Asserts.notNull(className, "className == null");
        if (classLoader == null) {
            classLoader = getDefaultClassLoader();
        }
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) Class.forName(className, initialize, classLoader);
        } catch (ClassNotFoundException e) {
            // load inner class
            clazz = loadInnerClass(className, initialize, classLoader);
        }

        return clazz;
    }

    private static <T> Class<T> loadInnerClass(String className, boolean initialize, ClassLoader classLoader) {
        int lastDotIndex = className.lastIndexOf(Chars.DOT);
        if (lastDotIndex > 0) {
            String innerClassName = className.substring(0, lastDotIndex) + Chars.DOLLAR + className.substring(lastDotIndex + 1);
            try {
                return (Class<T>) Class.forName(innerClassName, initialize, classLoader);
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundRuntimeException(e);
            }
        }
        throw new ClassNotFoundRuntimeException("Cannot load class:" + className);
    }

    // endregion

}
