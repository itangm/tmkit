package cn.tmkit.core.bean;

import cn.tmkit.core.exception.BeanException;
import cn.tmkit.core.lang.reflect.Singletons;
import cn.tmkit.core.support.SimpleCache;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bean内省缓存
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class BeanIntrospectCache {

    /**
     * 缓存已经内省过的Class
     */
    private final SimpleCache<Class<?>, List<PropertyDescriptor>> descriptorsCache = new SimpleCache<>(1024);

    private BeanIntrospectCache() {

    }

    /**
     * 获取Javabean的属性描述列表
     *
     * @param beanClass 对象内容
     * @return {@code PropertyDescriptor}数组
     */
    public List<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) {
        Objects.requireNonNull(beanClass, "BeanClass == null");
        List<PropertyDescriptor> propertyDescriptorList;
        synchronized (descriptorsCache) {
            propertyDescriptorList = descriptorsCache.get(beanClass);
        }
        if (propertyDescriptorList == null) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                propertyDescriptorList = new ArrayList<>(propertyDescriptors.length);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if (!"class".equals(propertyDescriptor.getName())) {
                        propertyDescriptorList.add(propertyDescriptor);
                    }
                }
                synchronized (descriptorsCache) {
                    descriptorsCache.put(beanClass, propertyDescriptorList);
                }
            } catch (IntrospectionException e) {
                throw new BeanException(String.format("Failed to obtain BeanInfo for class [%s]", beanClass.getName()), e);
            }
        }
        return propertyDescriptorList;
    }

    /**
     * 根据属性名称获取对应属性对象
     *
     * @param beanClass    类型
     * @param propertyName 属性名
     * @return {@linkplain PropertyDescriptor}
     */
    public PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
        List<PropertyDescriptor> propertyDescriptors = this.getPropertyDescriptors(beanClass);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals(propertyName)) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    /**
     * 清理缓存
     */
    public void clearDescriptors() {
        descriptorsCache.clear();
    }

    /**
     * 返回{@linkplain BeanIntrospectCache}单例
     *
     * @return {@linkplain BeanIntrospectCache} Singleton
     */
    public static BeanIntrospectCache getInstance() {
        return Singletons.get(BeanIntrospectCache.class);
    }

}
