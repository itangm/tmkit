package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Maps;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.map.LinkedMultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * HTTP头
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpHeaders extends LinkedMultiValueMap<String, String> {

    public HttpHeaders() {
        super();
    }

    public HttpHeaders(Map<String, ?> headerMap) {
        super();
        Maps.forEach(headerMap, this::append);
    }

    public HttpHeaders(HttpHeaders headers) {
        super();
        Maps.forEach(headers, this::append);
    }

    public HttpHeaders append(String key, Object value) {
        if (Objects.isAllNotNull(key, value)) {
            if (value instanceof Collection) {
                for (Object obj : (Collection<?>) value) {
                    add(key, obj.toString());
                }
            } else {
                add(key, value.toString());
            }
        }
        return this;
    }

    public HttpHeaders append(HeaderName key, Object value) {
        if (Objects.isAllNotNull(key, value)) {
            append(key.toString(), value);
        }

        return this;
    }

    public List<String> get(HeaderName headerName) {
        if (Objects.isNull(headerName)) {
            return Collections.emptyList();
        }
        return get(headerName.getValue());
    }

}
