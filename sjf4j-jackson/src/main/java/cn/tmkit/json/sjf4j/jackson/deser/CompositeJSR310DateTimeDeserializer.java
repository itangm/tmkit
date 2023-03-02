package cn.tmkit.json.sjf4j.jackson.deser;

import cn.tmkit.core.lang.Arrays;
import cn.tmkit.core.lang.Collections;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 组合反序列化模式
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
//@Slf4j
public class CompositeJSR310DateTimeDeserializer<T> extends JsonDeserializer<T> {

    private final List<JSR310DateTimeDeserializerBase<T>> deserializers = Collections.arrayList(16);

    public CompositeJSR310DateTimeDeserializer(JSR310DateTimeDeserializerBase<T>... serializers) {
        this.deserializers.addAll(Arrays.asList(serializers));
    }

    public CompositeJSR310DateTimeDeserializer(Collection<JSR310DateTimeDeserializerBase<T>> serializers) {
        if (Collections.isNotEmpty(serializers)) {
            this.deserializers.addAll(serializers);
        }
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        for (JSR310DateTimeDeserializerBase<T> deserializer : deserializers) {
            return deserializer.deserialize(p, ctxt);
        }
        return null;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException, JacksonException {
        return super.deserializeWithType(p, ctxt, typeDeserializer);
    }

}
