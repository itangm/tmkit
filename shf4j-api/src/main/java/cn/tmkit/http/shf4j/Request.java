package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.*;
import cn.tmkit.core.map.LinkedMultiValueMap;
import cn.tmkit.core.map.MultiValueMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 请求类定义
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public final class Request {

    /**
     * 请求方法
     */
    private final HttpMethod method;

    /**
     * 请求地址和请求参数
     */
    private final String url;

    /**
     * 请求消息头
     */
    private final HttpHeaders headers;

    /**
     * URL参数
     */
    private final MultiValueMap<String, String> queryParams;

    /**
     * 请求内容
     */
    private final RequestBody body;

    /**
     * 解码的HTTP状态码
     */
    private final List<HttpStatus> decodeStatusCodes;

    Request(Builder builder) {
        this.method = builder.method;
        this.headers = new HttpHeaders(builder.headerMap);
        this.queryParams = new LinkedMultiValueMap<>(builder.queryParams);
        int index = builder.url.indexOf('?');
        if (index > 0) {
            String paramStr = builder.url.substring(index + 1);
            this.queryParams.addAll(Urls.parseByUrlQueryStr(paramStr));
            this.url = builder.url.substring(0, index);
        } else {
            this.url = builder.url;
        }
        this.body = builder.body;
        this.decodeStatusCodes = new ArrayList<>(builder.decodeStatusCodes);
    }

    /**
     * 返回请求URL
     *
     * @return URL url
     */
    @NotNull
    public String url() {
        return url;
    }

    /**
     * HttpRequest Headers.
     *
     * @return the request headers.
     */
    public Map<String, List<String>> headers() {
        return headers;
    }

    public MultiValueMap<String, String> queryParams() {
        return queryParams;
    }

    public HttpMethod method() {
        return method;
    }

    @Nullable
    public RequestBody body() {
        return body;
    }

    @NotNull
    public List<HttpStatus> decodeStatusCodes() {
        return decodeStatusCodes;
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 参数构建
     *
     * @author miles.tang
     */
    public static class Builder {

        /**
         * 请求方法
         */
        private HttpMethod method;

        /**
         * 请求地址和请求参数
         */
        private String url;

        /**
         * 请求消息头
         */
        private final MultiValueMap<String, String> headerMap;

        private final MultiValueMap<String, String> queryParams;

        /**
         * 请求内容
         */
        private RequestBody body;

        /**
         * 解码的HTTP状态码
         */
        private Set<HttpStatus> decodeStatusCodes;

        public Builder() {
            this.method = HttpMethod.GET;
            this.headerMap = new LinkedMultiValueMap<>();
            this.queryParams = new LinkedMultiValueMap<>();
            this.decodeStatusCodes = new HashSet<>();
            this.decodeStatusCodes.add(HttpStatus.OK);
        }

        public Builder(Request request) {
            this.method = request.method;
            this.url = request.url;
            this.headerMap = new LinkedMultiValueMap<>(request.headers);
            this.body = request.body;
            this.queryParams = request.queryParams;
        }

        public Builder method(@NotNull HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(@NotNull String url) {
            this.url = Asserts.notEmpty(url);
            return this;
        }

        public Builder replaceHeader(@NotNull String name, @NotNull String value) {
            Asserts.notEmpty(name, "name is null or empty");

            List<String> values = new ArrayList<>();
            values.add(value);
            headerMap.put(name, values);
            return this;
        }

        public Builder addHeader(@NotNull String name, @NotNull String value) {
            Asserts.notEmpty(name, "name is null or empty");

            List<String> values = headerMap.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public Builder removeHeader(String name) {
            headerMap.remove(name);
            return this;
        }

        public Builder headers(HttpHeaders headers) {
            if (Maps.isNotEmpty(headers)) {
                headers.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (Objects.isAnyNull(name, values)) {
                        return;
                    }
                    List<String> oValues = headerMap.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }

            return this;
        }

        public Builder queryParams(Map<String, ?> queryParamMap) {
            if (Maps.isNotEmpty(queryParamMap)) {
                queryParamMap.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (Objects.isAnyNull(name, values)) {
                        return;
                    }
                    List<String> oValues = queryParams.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }
            return this;
        }

        public Builder body( RequestBody body) {
            return method(this.method, body);
        }

        public Builder method(HttpMethod method,  RequestBody body) {
            method(method);
            if (method == HttpMethod.GET || method == HttpMethod.HEAD) {
                // 不能有body
                if (body != null) {
                    throw new IllegalArgumentException("method " + method.name() + " must not have a request body");
                }
            } else if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH) {
                if (body == null) {
                    throw new IllegalArgumentException("method " + method.name() + " must have a request body");
                }
            }
            this.body = body;
            return this;
        }

        public Builder decodeStatusCodes(List<HttpStatus> decodeStatusCodes) {
            if (Collections.isNotEmpty(decodeStatusCodes)) {
                this.decodeStatusCodes.addAll(decodeStatusCodes);
            }
            return this;
        }


        public Request build() {
            return new Request(this);
        }
    }

}
