package cn.tmkit.json.sjf4j.cfg;

/**
 * 序列化配置
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public enum SerializationFeature {

    /**
     * 默认序列化所有属性
     */
    ALWAYS,

    /**
     * 序列化非{@code null}的属性
     */
    NON_NULL,

    /**
     * 序列化非空的属性,非空标准如下：
     * 对象为{@code null}
     * 字符串为空
     * 集合{@linkplain java.util.Collection}和{@linkplain java.util.Map}为空
     */
    NON_EMPTY,


}
