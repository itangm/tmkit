package cn.tmkit.http.shf4j.okhttp;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.*;
import cn.tmkit.http.shf4j.FormBody;
import cn.tmkit.http.shf4j.MultipartBody;
import cn.tmkit.http.shf4j.Request;
import cn.tmkit.http.shf4j.Response;
import cn.tmkit.http.shf4j.ResponseBody;
import cn.tmkit.http.shf4j.*;
import okhttp3.RequestBody;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于{@code okhttp}提供的{@code Client}
 *
 * @author miles.tang
 */
public class OkClient implements Client {

    private final OkHttpClient delegate;

    public OkClient() {
        this(new OkHttpClient());
    }

    public OkClient(@NotNull OkHttpClient delegate) {
        this.delegate = delegate;
    }

    public OkClient(Options options) {
        this.delegate = build(new OkHttpClient(), Objects.getIfNull(options, Options.DEFAULT_OPTIONS));
    }

    /**
     * 执行HTTP请求
     *
     * @param request 请求对象
     * @param options 请求选项
     * @return 执行结果
     * @throws IoRuntimeException HTTP请求异常¬
     */
    @Override
    public Response execute(@NotNull Request request, Options options) throws IoRuntimeException {
        OkHttpClient okHttpClientScoped = this.build(this.delegate, options);
        okhttp3.Request okRequest = toOkHttpRequest(request);
        try {
            okhttp3.Response okResponse = okHttpClientScoped.newCall(okRequest).execute();
            return toHttpResponse(okResponse, request).toBuilder().request(request).build();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            Utils.closeParts(request.body());
        }
    }

    /**
     * 构建{@linkplain OkHttpClient}对象
     *
     * @param okHttpClient 原{@linkplain OkHttpClient}对象
     * @param options      选项配置
     * @return 构建后的{@code OkHttpClient}对象
     */
    private OkHttpClient build(OkHttpClient okHttpClient, Options options) {
        if (options != null) {
            OkHttpClient.Builder builder = okHttpClient.newBuilder();
            if (okHttpClient.connectTimeoutMillis() != options.connectTimeoutMillis() ||
                    okHttpClient.readTimeoutMillis() != options.readTimeoutMillis() ||
                    okHttpClient.writeTimeoutMillis() != options.writeTimeoutMillis() ||
                    okHttpClient.followRedirects() != options.followRedirects()) {
                builder.connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .writeTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .followRedirects(options.followRedirects())
                        .build();
            }
            ProxyInfo proxyInfo = options.proxyInfo();
            if (proxyInfo != null) {
                builder.proxy(proxyInfo.toJdkProxy());
                if (Strings.isAllNotBlank(proxyInfo.username(), proxyInfo.password())) {
                    builder.proxyAuthenticator((route, response) -> response.request().newBuilder()
                            .header("Proxy-Authorization", Credentials.basic(proxyInfo.username(), proxyInfo.password()))
                            .build());
                }
            }
            if (options.retryCount() > 0) {
                builder.addNetworkInterceptor(new Retry(options.retryCount()));
            }
            return builder.build();
        }
        return okHttpClient;
    }

    @SuppressWarnings("ConstantConditions")
    private static okhttp3.Request toOkHttpRequest(Request input) {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(input.url()).newBuilder();
        Maps.wrapper(input.queryParams())
                .forEach((name, values) -> values.forEach(value -> httpUrlBuilder.addQueryParameter(name, value)));

        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        requestBuilder.url(httpUrlBuilder.build());

        ContentType contentType = null;
        if (input.body() != null) {
            contentType = input.body().contentType();
        }

        // header
        boolean hasAcceptHeader = input.headers().keySet().stream().anyMatch(HeaderName.ACCEPT::matchesIgnoreCase);
        input.headers().forEach((name, values) -> values.forEach(value -> requestBuilder.addHeader(name, value)));
        for (Map.Entry<String, List<String>> entry : input.headers().entrySet()) {
            if (HeaderName.CONTENT_TYPE.matchesIgnoreCase(entry.getKey())) {
                for (String value : entry.getValue()) {
                    contentType = ContentType.parse(value);
                    break;
                }
                break;
            }
        }
        // Some servers choke on the default accept string.
        if (!hasAcceptHeader) {
            requestBuilder.addHeader(HeaderName.ACCEPT.getValue(), "*/*");
        }

        MediaType okhttp3MediaType = contentType == null ? null : MediaType.parse(contentType.toString());

        // process body
        boolean isMethodWithBody = HttpMethod.POST == input.method() || HttpMethod.PUT == input.method() ||
                HttpMethod.PATCH == input.method();
        if (isMethodWithBody) {
            requestBuilder.removeHeader(HeaderName.CONTENT_TYPE.getValue());
        }
        if (input.body() == null) {
            if (isMethodWithBody) {
                requestBuilder.method(input.method().name(), RequestBody.create(okhttp3MediaType, Arrays.EMPTY_BYTE_ARRAY));
            } else {
                requestBuilder.method(input.method().name(), null);
            }
        } else if (input.body() instanceof FormBody) {
            FormBody formBody = (FormBody) input.body();
            okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder(formBody.getCharset());
            for (FormBody.NameValuePair nameValuePair : formBody.getItems()) {
                builder.add(nameValuePair.getName(), nameValuePair.getValue());
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else if (input.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) input.body();
            okhttp3.MultipartBody.Builder builder;
            if (Strings.hasLength(multipartBody.getBoundary())) {
                builder = new okhttp3.MultipartBody.Builder(multipartBody.getBoundary());
            } else {
                builder = new okhttp3.MultipartBody.Builder();
            }
            builder.setType(okhttp3MediaType);

            if (Collections.isEmpty(multipartBody.getParts())) {
                builder.addPart(okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, Arrays.EMPTY_BYTE_ARRAY));
            } else {
                for (FormPart formPart : multipartBody.getParts()) {
                    MediaType partMediaType = MediaType.parse(formPart.getContentType().toString());
                    builder.addFormDataPart(formPart.getName(), formPart.getFilename(),
                            okhttp3.RequestBody.create(partMediaType, IoUtil.readBytes(formPart.getIn())));
                }
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else {
            requestBuilder.method(input.method().name(), RequestBody.create(okhttp3MediaType, input.body().getData()));
        }
        return requestBuilder.build();
    }

    private static Response toHttpResponse(okhttp3.Response okResponse, Request input) {
        return Response.builder()
                .statusCode(okResponse.code())
                .reason(okResponse.message())
                .request(input)
                .headers(new HttpHeaders(okResponse.headers().toMultimap()))
                .body(toBody(okResponse))
                .build();
    }

    private static ResponseBody toBody(final okhttp3.Response okResponse) {
        okhttp3.ResponseBody okBody = okResponse.body();
        if (okBody == null) {
            return null;
        }
        long contentLength = okBody.contentLength();
        if (contentLength == 0) {
            okBody.close();
            return null;
        }
        final Integer length = contentLength > Integer.MAX_VALUE ? null : (int) contentLength;
        return new ResponseBody() {
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
                return okBody.byteStream();
            }

            @Override
            public Reader asReader(Charset charset) throws IoRuntimeException {
                return okBody.charStream();
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                if (okResponse.code() == HttpStatus.NOT_FOUND.value() ||
                        okResponse.code() == HttpStatus.NO_CONTENT.value()) {
                    return null;
                }

                MediaType okMediaType = okBody.contentType();
                Charset encoding = null;
                if (okMediaType != null) {
                    encoding = okMediaType.charset(charset);
                }
                if (encoding == null) {
                    encoding = Charsets.UTF_8;
                }

                Reader reader = null;
                try {
                    reader = asReader(encoding);
                    return IoUtil.read(reader);
                } finally {
                    IoUtil.closeQuietly(reader);
                }
            }

            @Override
            public void close() {
                okBody.close();
            }
        };

    }

}
