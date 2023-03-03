package cn.tmkit.http.shf4j;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.*;
import cn.tmkit.http.shf4j.exceptions.HttpClientErrorException;
import cn.tmkit.http.shf4j.exceptions.HttpServerErrorException;
import cn.tmkit.http.shf4j.exceptions.UnknownHttpStatusCodeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 响应类定义
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class Response implements Closeable, AutoCloseable {

    /**
     * HTTP状态码
     */
    private final int statusCode;

    /**
     * HTTP状态消息
     */
    @Nullable
    private final String reason;

    /**
     * 响应头
     */
    private final HttpHeaders headers;

    /**
     * 响应内容
     */
    @Nullable
    private final ResponseBody body;

    /**
     * 原始HTTP请求
     */
    private final Request request;

    /**
     * HTTP Protocol Version
     */
    private final ProtocolVersion protocolVersion;

    private Response(@NotNull Builder builder) {
        this.statusCode = builder.statusCode;
        this.reason = builder.reason;
        this.headers = builder.headers;
        this.body = builder.body;
        this.request = builder.request;
        this.protocolVersion = builder.protocolVersion;
    }

    /**
     * 将{@linkplain Response}转为{@linkplain Builder}对象
     *
     * @return {@linkplain Builder}对象
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * {@linkplain Response}的构建对象
     *
     * @return {@linkplain Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * status code. ex {@code 200}.See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html" >rfc2616</a>
     */
    public int statusCode() {
        return statusCode;
    }

    /**
     * 返回HTTP包装过的状态码
     *
     * @return 状态码
     */
    public HttpStatus status() {
        return HttpStatus.valueOf(statusCode());
    }

    /**
     * Nullable and not set when using http/2. See https://github.com/http2/http2-spec/issues/202
     */
    public String reason() {
        return reason;
    }

    public HttpHeaders headers() {
        return headers;
    }

    public ResponseBody body() {
        return body;
    }

    public Request request() {
        return request;
    }

    /**
     * the HTTP protocol version
     *
     * @return HTTP protocol version or empty if a client does not provide it
     */
    public ProtocolVersion protocolVersion() {
        return protocolVersion;
    }

    /**
     * 解析HTTP响应内容的字符集，如果服务端没有返回字符集默认字符集为{@linkplain Charsets#UTF_8}
     *
     * @return 字符集
     */
    public Charset charset() {
        List<String> contentTypeStrs = headers.get(HeaderName.CONTENT_TYPE);
        if (Collections.isNotEmpty(contentTypeStrs)) {
            for (String contentTypeStr : contentTypeStrs) {
                ContentType contentType = ContentType.parse(contentTypeStr);
                if (contentType.getCharset() != null) {
                    return contentType.getCharset();
                }
            }
        }
        return Charsets.UTF_8;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(protocolVersion.toString())
                .append(Chars.SPACE).append(statusCode);
        if (reason != null) {
            builder.append(Chars.SPACE).append(reason);
        }
        builder.append(Chars.LF);

        if (Maps.isNotEmpty(headers)) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (Collections.isNotEmpty(entry.getValue())) {
                    for (String value : entry.getValue()) {
                        builder.append(entry.getKey()).append(Chars.COLON).append(Chars.SPACE).append(value).append(Chars.LF);
                    }
                }
            }
        }

        if (body != null) {
            builder.append(Chars.LF).append(body);
        }
        return builder.toString();
    }

    @Override
    public void close() {
        IoUtil.closeQuietly(body);
    }

    /**
     * 检查HTTP status code是否有错误
     *
     * @throws HttpClientErrorException       客户端异常
     * @throws HttpServerErrorException       服务端异常
     * @throws UnknownHttpStatusCodeException 未知状态码异常
     */
    public void checkStatus() {
        if (hasError()) {
            HttpStatus statusCode = status();
            byte[] responseBody = (body == null) ? null : IoUtil.readBytes(body.byteStream());
            switch (statusCode.series()) {
                case CLIENT_ERROR:
                    throw new HttpClientErrorException(statusCode, reason, headers, responseBody, null, request);
                case SERVER_ERROR:
                    throw new HttpServerErrorException(statusCode, reason, headers, responseBody, null, request);
                default:
                    throw new UnknownHttpStatusCodeException(statusCode.value(), reason, headers, responseBody, null, request);
            }
        }
    }

    /**
     * 判断Response是否有错误
     *
     * @return {@code true}/{@code false}
     */
    public boolean hasError() {
        return request.decodeStatusCodes().stream().noneMatch(item -> item == status());
    }

    /**
     * {@linkplain Response}的构造者
     *
     * @author miles.tang
     */
    public static class Builder {

        /**
         * HTTP状态码
         */
        int statusCode;

        /**
         * HTTP状态消息
         */
        String reason;

        /**
         * 响应头
         */
        HttpHeaders headers;

        /**
         * 响应内容
         */
        ResponseBody body;

        /**
         * 原始HTTP请求
         */
        Request request;

        /**
         * HTTP Protocol Version
         */
        private ProtocolVersion protocolVersion = ProtocolVersion.HTTP_1_1;

        Builder() {
        }

        Builder(@NotNull Response source) {
            this.statusCode = source.statusCode;
            this.reason = source.reason;
            this.headers = source.headers;
            this.body = source.body;
            this.request = source.request;
            this.protocolVersion = source.protocolVersion;
        }

        /**
         * @see Response#statusCode
         */
        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * @see Response#reason
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * @see Response#headers
         */
        public Builder headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(InputStream inputStream, int length) {
            if (inputStream == null) {
                this.body = null;
            } else {
                this.body = InputStreamBody.create(inputStream, length);
            }
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(byte[] data) {
            this.body = ByteArrayBody.create(data);
            return this;
        }

        /**
         * @see Response#body
         */
        public Builder body(String text, Charset charset) {
            this.body = ByteArrayBody.create(text, charset);
            return this;
        }

        /**
         * @see Response#request
         */
        public Builder request(@NotNull Request request) {
            this.request = request;
            return this;
        }

        /**
         * HTTP protocol version
         */
        public Builder protocolVersion(ProtocolVersion protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        /**
         * 构建{@linkplain Response}对象
         *
         * @return {@linkplain Response}对象
         */
        public Response build() {
            return new Response(this);
        }

    }

    static class ByteArrayBody implements ResponseBody {

        private final byte[] data;

        private ByteArrayBody(byte @NotNull [] data) {
            this.data = data;
        }

        @Override
        public Integer length() {
            return data.length;
        }

        @Override
        public boolean isRepeatable() {
            return true;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public Reader asReader(Charset charset) throws IoRuntimeException {
            return new BufferedReader(new InputStreamReader(byteStream(), charset));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            return new String(data, charset);
        }

        @Override
        public void close() {
            // NOP
        }

        public static ResponseBody create(byte[] data) {
            return new ByteArrayBody(Objects.requireNonNull(data));
        }

        public static ResponseBody create(@NotNull String text, @Nullable Charset charset) {
            return create(Asserts.notEmpty(text).getBytes(Charsets.getCharset(charset)));
        }
    }

    static class InputStreamBody implements ResponseBody {

        private final InputStream in;

        private final int length;

        private InputStreamBody(@NotNull InputStream in, int length) {
            this.in = in;
            this.length = length;
        }


        /**
         * 字节的长度，可能为{@code null}
         *
         * @return 字节长度
         */
        @Override
        public Integer length() {
            return length;
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return in;
        }

        @Override
        public Reader asReader(Charset charset) throws IoRuntimeException {
            Charset encoding = Charsets.getCharset(charset, Charsets.UTF_8);
            return new BufferedReader(new InputStreamReader(in, encoding));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            return IoUtil.read(asReader(charset));
        }

        @Override
        public void close() throws IOException {
            in.close();
        }

        public static ResponseBody create(@NotNull InputStream in, int length) {
            return new InputStreamBody(Objects.requireNonNull(in), length);
        }

    }

}
