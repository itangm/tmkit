package cn.tmkit.json.sjf4j.jackson;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.json.sjf4j.BaseJsonHandler;
import cn.tmkit.json.sjf4j.JsonRuntimeException;
import cn.tmkit.json.sjf4j.annotation.JsonProviderName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Jackson Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
@JsonProviderName(value = "jackson", index = 30)
public class JacksonHandler extends BaseJsonHandler {

    private final ObjectMapper objectMapper;

    public JacksonHandler() {
        this(JacksonUtil.createObjectMapper());
    }

    public JacksonHandler(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper is null");
    }


    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@NotNull Object src, String[] ignorePropertyNames) throws JsonRuntimeException {
        if (Arrays.isNotEmpty(ignorePropertyNames)) {
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            sfp.addFilter("fieldFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignorePropertyNames));
            try {
                return objectMapper.copy().setFilterProvider(sfp).writeValueAsString(src);
            } catch (JsonProcessingException e) {
                throw new JsonRuntimeException(e);
            }
        } else {
            return serialize(src, src.getClass());
        }
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
    public String doSerialize(@NotNull Object src, Type typeOfT) throws JsonRuntimeException {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonRuntimeException(e);
        }
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T doDeserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        try {
            if (JacksonUtil.isJacksonJavaType(typeOfT)) {
                return objectMapper.readValue(json, JacksonUtil.toJavaType(typeOfT));
            }
            // is primitive ?

            if (JacksonUtil.isClass(typeOfT)) {
                return objectMapper.readValue(json, (Class<T>) typeOfT);
            }

            if (JacksonUtil.isParameterizedType(typeOfT)) {
                return objectMapper.readValue(json, new TypeReference<T>() {
                    @Override
                    public Type getType() {
                        return typeOfT;
                    }
                });
            }
            return null;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转为集合
     *
     * @param json  字符串，可以为空
     * @param clazz 集合元素的类型
     * @return 集合对象
     */
    @Override
    public <T> List<T> deserializeList(String json, Class<T> clazz) {
        // 底层有缓存，所以其性能很高了
        return doDeserialize(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    /**
     * 将JSON字符串反序列化为{@linkplain Map}对象
     *
     * @param json   字符串，可以为空
     * @param kClass {@linkplain Map}的键类型
     * @param vClass {@linkplain Map}的值类型
     * @return {@linkplain Map}对象
     */
    @Override
    public <K, V> Map<K, V> deserializeMap(String json, Class<K> kClass, Class<V> vClass) {
        return doDeserialize(json, objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
    }

}
