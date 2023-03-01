package cn.tmkit.core.bean;

import cn.tmkit.core.lang.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean复制的配置
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class CopyOption implements java.io.Serializable {

    private static final long serialVersionUID = 2021L;

    /**
     * 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     */
    private boolean ignoreNullValue = false;

    /**
     * 忽略空字符串
     */
    private boolean ignoreEmptyString = false;

    /**
     * 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    private List<String> ignoreProperties = new ArrayList<>();

    public boolean isIgnoreNullValue() {
        return ignoreNullValue;
    }

    public boolean isIgnoreEmptyString() {
        return ignoreEmptyString;
    }

    public List<String> getIgnoreProperties() {
        return ignoreProperties;
    }

    /**
     * 默认构造器
     */
    public CopyOption() {

    }

    /**
     * 是否忽略空值的构造器
     *
     * @param ignoreNullValue 是否忽略空值
     */
    public CopyOption(boolean ignoreNullValue) {
        this();
        this.ignoreNullValue = ignoreNullValue;
    }

    /**
     * 需要忽略的属性名列表的构造器
     *
     * @param ignoreProperties 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    public CopyOption(String... ignoreProperties) {
        this();
        if (Arrays.isNotEmpty(ignoreProperties)) {
            this.ignoreProperties.addAll(java.util.Arrays.asList(ignoreProperties));
        }
    }

    /**
     * 构造器
     *
     * @param ignoreNullValue  是否忽略空值
     * @param ignoreProperties 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    public CopyOption(boolean ignoreNullValue, String... ignoreProperties) {
        this(ignoreProperties);
        this.ignoreNullValue = ignoreNullValue;
    }

    public CopyOption setIgnoreNullValue(boolean ignoreNullValue) {
        this.ignoreNullValue = ignoreNullValue;
        return this;
    }

    public CopyOption setIgnoreEmptyString(boolean ignoreEmptyString) {
        this.ignoreEmptyString = ignoreEmptyString;
        return this;
    }

    public CopyOption setIgnoreProperties(List<String> ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
        return this;
    }

}
