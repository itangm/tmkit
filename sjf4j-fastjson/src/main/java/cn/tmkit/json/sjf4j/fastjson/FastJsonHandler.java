package cn.tmkit.json.sjf4j.fastjson;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.json.sjf4j.BaseJsonHandler;
import cn.tmkit.json.sjf4j.JsonRuntimeException;
import cn.tmkit.json.sjf4j.annotation.JsonProviderName;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Type;

/**
 * 基于{@code Fastjson}的JSON处理器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "fastjson", index = 50)
public class FastJsonHandler extends BaseJsonHandler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@NotNull Object src, @Nullable String[] ignorePropertyNames) throws JsonRuntimeException {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (Arrays.isNotEmpty(ignorePropertyNames)) {
            for (String ignorePropertyName : ignorePropertyNames) {
                filter.getExcludes().add(ignorePropertyName);
            }
        }
        return JSON.toJSONString(src, filter);
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@Nullable @org.jetbrains.annotations.NotNull Object src, @Nullable Type typeOfT) throws JsonRuntimeException {
        return JSON.toJSONString(src);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T doDeserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        return JSON.parseObject(json, typeOfT);
    }

}
