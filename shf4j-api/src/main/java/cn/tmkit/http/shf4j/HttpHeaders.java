package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Maps;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.map.LinkedMultiValueMap;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * HTTP头
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
@SuppressWarnings("ConstantConditions")
public class HttpHeaders extends LinkedMultiValueMap<String, String> {

    public HttpHeaders() {
        super();
    }

    public HttpHeaders(@Nullable Map<String, ?> headerMap) {
        super();
        if (Maps.isNotEmpty(headerMap)) {
            headerMap.forEach((BiConsumer<String, Object>) this::append);
        }
    }

    public HttpHeaders(@Nullable HttpHeaders headers) {
        super();
        if (Maps.isNotEmpty(headers)) {
            headers.forEach((BiConsumer<String, Object>) this::append);
        }
    }

    public HttpHeaders append(@Nullable String key, @Nullable Object value) {
        if (Objects.isAllNotNull(key, value)) {
            add(key, value.toString());
        }
        return this;
    }

    public HttpHeaders append(@Nullable HeaderNames key, Object value) {
        if (Objects.isAllNotNull(key, value)) {
            add(key.toString(), value.toString());
        }

        return this;
    }

    public List<String> get(HeaderNames headerName) {
        if (Objects.isNull(headerName)) {
            return Collections.emptyList();
        }
        return get(headerName.getValue());
    }

}
