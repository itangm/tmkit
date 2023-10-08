package cn.tmkit.http;

import cn.tmkit.core.convert.Converts;
import cn.tmkit.core.io.Files;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.*;
import cn.tmkit.core.lang.regex.PatternPool;
import cn.tmkit.http.shf4j.*;
import cn.tmkit.http.shf4j.exceptions.HttpClientException;
import cn.tmkit.json.sjf4j.BaseTypeRef;
import cn.tmkit.json.sjf4j.util.JSONs;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Abstract Base Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseRequest<Req extends BaseRequest<Req>> implements BaseRequest<Req> {

    /**
     * 请求客户端名
     */
    protected String clientName;

    /**
     * 请求方式
     */
    protected HttpMethod method;

    /**
     * 请求地址
     */
    protected String url;

    /**
     * 请求参数
     */
    protected Map<String, String> queryParams;

    /**
     * HTTP请求头
     */
    protected HttpHeaders headers;

    /**
     * 选项配置
     */
    protected Options.Builder optionsBuilder;

    /**
     * 解析HTTP状态码
     */
    private boolean decodeStatusCodeAll = true;

    private static final List<HttpStatus> DECODE_ALL_STATUS_CODES = Collections.arrayList(HttpStatus.values());

    /**
     * 解码哪些HTTP状态码，默认解码所有
     */
    private final List<HttpStatus> decodeStatusCodes;

    public AbstractBaseRequest() {
        this.queryParams = new LinkedHashMap<>();
        this.headers = new HttpHeaders();
        this.decodeStatusCodes = new ArrayList<>();
        this.decodeStatusCodes.add(HttpStatus.OK);
    }

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req url(String url) {
        this.url = url;
        return (Req) this;
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryParam(String name, Number value) {
        if (Strings.hasLength(name) && value != null) {
            this.queryParams.put(name, value.toString());
        }
        return (Req) this;
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryParam(String name, String value) {
        if (Strings.isAllNotBlank(name) && value != null) {
            this.queryParams.put(name, value);
        }
        return (Req) this;
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param parameters 参数对
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryParam(Map<String, ?> parameters) {
        if (Maps.isNotEmpty(parameters)) {
            parameters.forEach((BiConsumer<String, Object>) (key, value) -> {
                if (Strings.hasLength(key) && value != null) {
                    queryParams.put(key, Converts.toStr(value));
                }
            });
        }
        return (Req) this;
    }

    /**
     * 添加请求头信息
     *
     * @param key   请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req header(String key, String value) {
        if (Strings.hasLength(key) && value != null) {
            this.headers.append(key, value);
        }
        return (Req) this;
    }

    /**
     * 添加请求头信息
     *
     * @param key   请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req header(HeaderName key, String value) {
        if (key != null && value != null) {
            this.headers.append(key, value);
        }
        return (Req) this;
    }

    /**
     * 添加请求头信息
     *
     * @param headers 请求头
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req header(HttpHeaders headers) {
        if (Maps.isNotEmpty(headers)) {
            this.headers.putAll(headers);
        }
        return (Req) this;
    }

    /**
     * 天机请求头信息
     *
     * @param headers 请求头
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req header(Map<String, String> headers) {
        if (Maps.isNotEmpty(headers)) {
            headers.forEach((key, value) -> this.headers.append(key, value));
        }
        return (Req) this;
    }

    /**
     * 从请求头中移除键值
     *
     * @param key 请求头键名
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req removeHeader(String key) {
        if (Strings.hasLength(key)) {
            this.headers.remove(key);
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独连接超时时间。
     *
     * @param connectTimeout 连接超时时间
     * @param timeUnit       超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req connectTimeout(Integer connectTimeout, TimeUnit timeUnit) {
        if (connectTimeout != null && timeUnit != null) {
            connectTimeoutMillis((int) timeUnit.toMillis(connectTimeout));
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独连接超时时间。
     *
     * @param connectTimeoutMillis 连接超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req connectTimeoutMillis(Integer connectTimeoutMillis) {
        if (connectTimeoutMillis != null) {
            Asserts.isTrue(connectTimeoutMillis >= 0, "connectTimeoutMillis >= 0.");
            optionsBuilder().connectTimeoutMillis(connectTimeoutMillis);
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间
     * @param timeUnit    超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req readTimeout(Integer readTimeout, TimeUnit timeUnit) {
        if (readTimeout != null && timeUnit != null) {
            this.readTimeoutMillis((int) timeUnit.toMillis(readTimeout));
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独读取流超时。
     *
     * @param readTimeoutMillis 流读取超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req readTimeoutMillis(Integer readTimeoutMillis) {
        if (readTimeoutMillis != null) {
            Asserts.isTrue(readTimeoutMillis >= 0, "readTimeoutMillis >= 0.");
            optionsBuilder().readTimeoutMillis(readTimeoutMillis);
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间
     * @param timeUnit     超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req writeTimeout(Integer writeTimeout, TimeUnit timeUnit) {
        if (writeTimeout != null && timeUnit != null) {
            this.writeTimeoutMillis((int) timeUnit.toMillis(writeTimeout));
        }
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain Req}设置单独写入流超时。
     *
     * @param writeTimeoutMillis 流写入超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req writeTimeoutMillis(Integer writeTimeoutMillis) {
        if (writeTimeoutMillis != null) {
            Asserts.isTrue(writeTimeoutMillis >= 0, "writeTimeoutMillis >= 0.");
            optionsBuilder().writeTimeoutMillis(writeTimeoutMillis);
        }
        return (Req) this;
    }

    /**
     * 定制解码HTTP状态码
     *
     * @param httpStatuses HTTP状态码
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req decodeStatusCodes(List<HttpStatus> httpStatuses) {
        if (Collections.isNotEmpty(httpStatuses)) {
            this.decodeStatusCodes.addAll(httpStatuses);
            this.decodeStatusCodeAll = false;
        }
        return (Req) this;
    }

    /**
     * 解析{@code http status code 2xx}HTTP状态码
     *
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req decodeStatusCode2xx() {
        List<HttpStatus> httpStatuses = Arrays.stream(HttpStatus.values())
                .filter(element -> element.series() == HttpStatus.Series.SUCCESSFUL)
                .collect(Collectors.toList());
        return decodeStatusCodes(httpStatuses);
    }

    public Req decodeStatusCode2xxAnd4xx() {
        List<HttpStatus> httpStatuses = Arrays.stream(HttpStatus.values())
                .filter(element -> element.series() == HttpStatus.Series.CLIENT_ERROR ||
                        element.series() == HttpStatus.Series.SUCCESSFUL)
                .collect(Collectors.toList());
        return decodeStatusCodes(httpStatuses);
    }

    /**
     * 为构建本次{@linkplain Req}设置HTTP的配置。
     *
     * @param options 选项配置
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req options(Options.Builder options) {
        this.optionsBuilder = options;
        return (Req) this;
    }

    /**
     * 同步执行并处理响应内容转为字符串
     *
     * @return 响应结果字符串
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    @Override
    public String string() throws HttpClientException {
        Response httpResponse = null;
        try {
            httpResponse = this.execute();
            httpResponse.checkStatus();
            ResponseBody responseBody = httpResponse.body();
            return (responseBody == null) ? null : responseBody.string(null);
        } finally {
            IoUtil.closeQuietly(httpResponse);
        }
    }

    /**
     * 将响应结果转为JavaBean对象
     *
     * @param targetClass 目标类型
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    @Override
    public <T> T bean(Class<T> targetClass) throws HttpClientException {
        String jsonStr = this.string();
        return JSONs.fromJson(jsonStr, targetClass);
    }

    /**
     * 将响应结果转为JavaBean对象
     * <p>
     * 用法如下：Map&lt;String,String&gt; data = BaseRequest.bean(new TypeRef&lt;Map&lt;String,String&gt;&gt;);
     * </p>
     *
     * @param typeRef 带有泛型类的封装类
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    @Override
    public <T> T bean(BaseTypeRef<T> typeRef) throws HttpClientException {
        String jsonStr = this.string();
        return JSONs.fromJson(jsonStr, typeRef);
    }

    /**
     * 将响应结果转为字节数组
     *
     * @return 字节数组
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    @Override
    public byte[] bytes() throws HttpClientException {
        Response httpResponse = null;
        try {
            httpResponse = this.execute();
            httpResponse.checkStatus();
            InputStream in = httpResponse.body().byteStream();
            return IoUtil.readBytes(in);
        } finally {
            IoUtil.closeQuietly(httpResponse);
        }
    }

    /**
     * 将响应结果输出到文件中
     *
     * @param saveFile 目标保存文件,非空
     */
    @Override
    public void file(File saveFile) throws HttpClientException {
//        boolean replace = false;
        Response httpResponse = null;
        try {
            httpResponse = this.execute();
            httpResponse.checkStatus();
            ResponseBody responseBody = httpResponse.body();

            File target;
            if (saveFile.isDirectory()) {
                String filename = contentDisposition(httpResponse.headers());
                if (Strings.isEmpty(filename)) {
                    filename = "Shf4j-Download";
                }
                target = new File(saveFile, filename);
            } else {
                target = saveFile;
            }
//            if (replace) {
//                Files.delete(target);
//            } else {
//                if (target.exists()) {
//                    target = getNewFilename(target, 1);
//                }
//            }
            if (target.exists()) {
                target = getNewFilename(target, 1);
            }
            Files.copy(responseBody.byteStream(), target);
        } finally {
            IoUtil.closeQuietly(httpResponse);
        }
    }

    /**
     * 将响应结果输出到输出流,并不会主动关闭输出流{@code out}
     *
     * @param out 输出流,非空
     */
    @Override
    public void outputStream(OutputStream out) throws HttpClientException {
        Response httpResponse = null;
        try {
            httpResponse = this.execute();
            httpResponse.checkStatus();
            IoUtil.copy(httpResponse.body().byteStream(), out);
        } finally {
            IoUtil.closeQuietly(httpResponse);
        }
    }

    /**
     * 同步执行HTTP请求并返回原始响应对象
     *
     * @return {@linkplain Response}
     */
    @Override
    public Response execute() {
        return HttpClient.getClient(clientName).execute(generateRequest(), optionsBuilder == null ? null : optionsBuilder.build());
    }

    private Options.Builder optionsBuilder() {
        return optionsBuilder == null ? Options.DEFAULT_OPTIONS.newBuilder() : optionsBuilder;
    }

    /**
     * 构建{@linkplain RequestBody}，根据不同的请求类型
     *
     * @return {@linkplain RequestBody}
     */
    protected RequestBody generateRequestBody() {
        return null;
    }

    private Request generateRequest() {
        return Request.builder()
                .url(this.url)
                .method(this.method)
                .headers(this.headers)
                .queryParams(this.queryParams)
                .body(generateRequestBody())
                .decodeStatusCodes(decodeStatusCodeAll ? DECODE_ALL_STATUS_CODES : decodeStatusCodes)
                .build();
    }

    private File getNewFilename(File file, int index) {
        String mainName = Files.mainName(file);
        String fileExt = Files.getFileExt(file);
        if (fileExt == null) {
            fileExt = Strings.EMPTY_STRING;
        } else {
            fileExt += Strings.DOT;
        }
        File target = new File(file.getParentFile(), mainName + "(" + index + ")" + fileExt);
        if (target.exists()) {
            return getNewFilename(file, index + 1);
        }
        return target;
    }

    public static String header(Map<String, List<String>> headers, String headerName, boolean ignoreCase) {
        if (Objects.isAnyEmpty(headers, headerName)) {
            return null;
        }
        List<String> values = null;
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (Strings.equals(entry.getKey(), headerName, ignoreCase)) {
                values = entry.getValue();
            }
        }
        if (Collections.isEmpty(values)) {
            return null;
        }
        return values.get(0);
    }

    public static String header(Map<String, List<String>> headers, HeaderName headerName, boolean ignoreCase) {
        Asserts.notNull(headerName, "headerName == null");
        return header(headers, headerName.toString(), ignoreCase);
    }


    public static String contentDisposition(HttpHeaders headers) {
        String contentDisposition = header(headers, HeaderName.CONTENT_DISPOSITION, true);
        if (contentDisposition == null) {
            return null;
        }

        // 判断是不是ISO-8859-1
        if (contentDisposition.equals(new String(contentDisposition.getBytes(Charsets.ISO_8859_1), Charsets.ISO_8859_1))) {
            contentDisposition = new String(contentDisposition.getBytes(Charsets.ISO_8859_1));
        }

        Pattern pattern = PatternPool.get("filename=\"?(.+)\"?", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(contentDisposition);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
