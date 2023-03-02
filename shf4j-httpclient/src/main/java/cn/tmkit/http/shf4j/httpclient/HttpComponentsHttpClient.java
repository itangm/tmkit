package cn.tmkit.http.shf4j.httpclient;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.http.shf4j.*;
import cn.tmkit.http.shf4j.HeaderNames;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于{@code Apache HttpComponents}包装实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpComponentsHttpClient implements Client {

    private final CloseableHttpClient delegate;

    public HttpComponentsHttpClient() {
        this(HttpClientBuilder.create().build());
    }

    public HttpComponentsHttpClient(@NotNull CloseableHttpClient closeableHttpClient) {
        this.delegate = closeableHttpClient;
    }

    public HttpComponentsHttpClient(@Nullable Options options) {
        this(null, options);
    }

    public HttpComponentsHttpClient(@Nullable CloseableHttpClient closeableHttpClient, @Nullable Options options) {
        this.delegate = buildHttpClient(closeableHttpClient, options);
    }


    private RequestConfig buildRequestConfig(@Nullable CloseableHttpClient chc, @Nullable Options options) {
        Options opts = Objects.getIfNull(options, Options.DEFAULT_OPTIONS);
        ProxyInfo proxyInfo = opts.proxyInfo();
        RequestConfig requestConfig;
        if (chc instanceof Configurable) {
            requestConfig = ((Configurable) chc).getConfig();
        } else {
            requestConfig = RequestConfig.custom().build();
        }
        return RequestConfig.copy(requestConfig)
                .setConnectTimeout(opts.connectTimeoutMillis())
                .setSocketTimeout(opts.readTimeoutMillis())
                .setRedirectsEnabled(opts.followRedirects())
                .setProxy(proxyInfo == null ? null : new HttpHost(proxyInfo.host(), proxyInfo.port(), proxyInfo.type().name()))
                .build();
    }

    private CloseableHttpClient buildHttpClient(@Nullable CloseableHttpClient chc, @Nullable Options options) {
        HttpClientBuilder builder = HttpClientBuilder.create()
                .setDefaultRequestConfig(buildRequestConfig(chc, options));
        Options opts = Objects.getIfNull(options, Options.DEFAULT_OPTIONS);
        ProxyInfo proxyInfo = opts.proxyInfo();
        if (proxyInfo != null) {
            if (Strings.isAllNotBlank(proxyInfo.username(), proxyInfo.password())) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyInfo.username(), proxyInfo.password()));
                builder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }

        // 重试机制
        if (opts.retryCount() > 0) {
            builder.setRetryHandler(new DefaultHttpRequestRetryHandler(opts.retryCount()));
        }
        return builder.build();
    }

    @Override
    public Response execute(@NotNull Request request, Options options) throws IoRuntimeException {
        try {
            HttpUriRequest httpUriRequest = toHttpUriRequest(request);
            CloseableHttpClient chc = buildHttpClient(delegate, options);
            org.apache.http.HttpResponse httpResponse = chc.execute(httpUriRequest);
            return toApiResponse(httpResponse, request);
        } catch (URISyntaxException e) {
            throw new IoRuntimeException("URL '" + request.url() + "' couldn't be parsed into a URI", e);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            Utils.closeParts(request.body());
        }
    }

    private HttpUriRequest toHttpUriRequest(Request request) throws URISyntaxException {
        RequestBuilder requestBuilder = RequestBuilder.create(request.method().name());
        URIBuilder uriBuilder = new URIBuilder(request.url());
        request.queryParams().forEach((name, values) -> values.forEach(value -> uriBuilder.addParameter(name, value)));
        requestBuilder.setUri(uriBuilder.build());

        // request headers
        boolean hasAcceptHeader = false;
        String headerName;
        for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
            headerName = entry.getKey();
            if (headerName.equalsIgnoreCase(HeaderNames.ACCEPT.toString())) {
                hasAcceptHeader = true;
            }

            if (Strings.equalsIgnoreCase(headerName, HeaderNames.CONTENT_LENGTH.toString())) {
                // The 'Content-Length' header is always set by the Apache client
                // doesn't like us to set it as well.
                continue;
            }

            for (String value : entry.getValue()) {
                requestBuilder.addHeader(headerName, value);
            }
        }
        if (!hasAcceptHeader) {
            requestBuilder.addHeader(HeaderNames.ACCEPT.toString(), "*/*");
        }

        // request body
        if (request.body() != null) {
            ContentType contentType = getContentType(request);
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                List<NameValuePair> nvpList = formBody.getItems().stream()
                        .map(item -> new BasicNameValuePair(item.getName(), item.getValue()))
                        .collect(Collectors.toList());
                requestBuilder.setEntity(new UrlEncodedFormEntity(nvpList, formBody.getCharset()));
            } else if (request.body() instanceof MultipartBody) {
                MultipartBody multipartBody = (MultipartBody) request.body();
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                        .setBoundary(multipartBody.getBoundary())
                        .setCharset(multipartBody.getCharset())
                        .setContentType(contentType)
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                for (MultipartBody.Part part : multipartBody.getParts()) {
                    ContentType partContentType = ContentType.create(
                            part.getContentType().getMimeType(), part.getContentType().getCharset());
                    if (part.getFile() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getFile());
                    } else if (part.getIn() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getIn(), partContentType, part.getValue());
                    } else if (part.getBody() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getBody().getData(),
                                partContentType, part.getValue());
                    } else if (part.getValue() != null) {
                        multipartEntityBuilder.addTextBody(part.getName(), part.getValue(), partContentType);
                    }
                }
                requestBuilder.setEntity(multipartEntityBuilder.build());
            } else {
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(request.body().getData());
                if (request.body().contentType() != null) {
                    byteArrayEntity.setContentType(contentType.toString());
                }
                requestBuilder.setEntity(byteArrayEntity);
            }
        } else {
            requestBuilder.setEntity(new ByteArrayEntity(new byte[0]));
        }
        return requestBuilder.build();
    }

    /**
     * 解析Content-Type
     *
     * @param request 请求
     * @return {@linkplain ContentType}
     */
    @SuppressWarnings("ConstantConditions")
    private ContentType getContentType(Request request) {
        RequestBody body = request.body();
        if (body != null && body.contentType() != null) {
            return ContentType.create(body.contentType().getMimeType(), body.contentType().getCharset());
        }
        for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
            if (HeaderNames.CONTENT_TYPE.matchesIgnoreCase(entry.getKey())) {
                List<String> values = entry.getValue();
                if (Collections.isNotEmpty(values)) {
                    return ContentType.parse(values.get(0));
                }
            }
        }
        return ContentType.APPLICATION_OCTET_STREAM;
    }

    private Response toApiResponse(org.apache.http.HttpResponse httpResponse, Request request) {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String reason = statusLine.getReasonPhrase();

        HttpHeaders httpHeaders = new HttpHeaders();
        for (Header header : httpResponse.getAllHeaders()) {
            httpHeaders.append(header.getName(), header.getValue());
        }

        return Response.builder()
                .statusCode(statusCode)
                .reason(reason)
                .headers(httpHeaders)
                .request(request)
                .body(toBody(httpResponse))
                .build();
    }

    private ResponseBody toBody(org.apache.http.HttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        return new ResponseBody() {
            @Override
            public Integer length() {
                long contentLength = entity.getContentLength();
                return contentLength >= 0 && contentLength <= Integer.MAX_VALUE ? (int) contentLength : null;
            }

            @Override
            public boolean isRepeatable() {
                return entity.isRepeatable();
            }

            @Override
            public InputStream byteStream() throws IoRuntimeException {
                try {
                    return entity.getContent();
                } catch (IOException e) {
                    throw new IoRuntimeException(e);
                }
            }

            @Override
            public Reader asReader(Charset charset) throws IoRuntimeException {
                return new InputStreamReader(byteStream(), getCharset(charset));
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                try {
                    return IoUtil.read(asReader(charset));
                } finally {
                    try {
                        close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
            }

            @Override
            public void close() throws IOException {
                try {
                    EntityUtils.consume(entity);
                } finally {
                    if (httpResponse instanceof Cloneable) {
                        IoUtil.closeQuietly(((Closeable) httpResponse));
                    }
                }
            }

            @NotNull
            Charset getCharset(Charset charset) {
                Charset resultEncoding = charset;
                if (resultEncoding == null) {
                    ContentType contentType = ContentType.get(entity);
                    if (contentType != null) {
                        resultEncoding = contentType.getCharset();
                    }
                }
                if (resultEncoding == null) {
                    resultEncoding = Charsets.UTF_8;
                }
                return resultEncoding;
            }

        };
    }

}
