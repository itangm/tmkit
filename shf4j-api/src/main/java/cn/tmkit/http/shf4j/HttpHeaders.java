package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Maps;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.map.LinkedMultiValueMap;

import java.util.Collection;
import java.util.Enumeration;
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

    /**
     * 配置http header
     *
     * @param key   header key
     * @param value header value
     * @return {@linkplain HttpHeaders}
     */
    @SuppressWarnings("unchecked")
    public HttpHeaders append(String key, Object value) {
        if (Objects.isAllNotNull(key, value)) {
            if (value instanceof Collection) {
                for (Object obj : (Collection<?>) value) {
                    add(key, obj.toString());
                }
            } else if (value instanceof Enumeration) {
                Enumeration<String> values = (Enumeration<String>) value;
                while (values.hasMoreElements()) {
                    add(key, values.nextElement());
                }
            } else {
                add(key, value.toString());
            }
        }
        return this;
    }

    /**
     * 配置http header
     *
     * @param key   header key
     * @param value header value
     * @return {@linkplain HttpHeaders}
     * @see #append(String, Object)
     */
    public HttpHeaders append(HeaderName key, Object value) {
        if (Objects.isAllNotNull(key, value)) {
            append(key.toString(), value);
        }

        return this;
    }

    /**
     * 根据header key获取header value
     *
     * @param headerName header name
     * @return header value
     */
    public List<String> get(HeaderName headerName) {
        if (Objects.isNull(headerName)) {
            return Collections.emptyList();
        }
        return get(headerName.getValue());
    }

    /**
     * 根据header key获取第一个值
     *
     * @param headerName header name
     * @return the first header value
     */
    public String getFirstValue(HeaderName headerName) {
        return headerName == null ? null : getFirstValue(headerName.toString());
    }

    /**
     * 根据header key获取第一个值
     *
     * @param key header name
     * @return the first header value
     */
    public String getFirstValue(String key) {
        if (Strings.isBlank(key)) {
            return null;
        }
        List<String> values = get(key);
        return Collections.isEmpty(values) ? null : values.get(0);
    }

    @Override
    public String toString() {
        return super.storeMap.toString();
    }

}
