package cn.tmkit.http;

import cn.tmkit.http.shf4j.*;
import cn.tmkit.http.shf4j.exceptions.HttpClientException;
import cn.tmkit.json.sjf4j.BaseTypeRef;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public interface BaseRequest<Req extends BaseRequest<Req>> {

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req url(String url);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryParam(String name, Number value);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryParam(String name, String value);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param parameters 参数对
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryParam(Map<String, ?> parameters);

    /**
     * 添加请求头信息
     *
     * @param key   请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req header(String key, String value);

    /**
     * 添加请求头信息
     *
     * @param key   请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req header(HeaderName key, String value);

    /**
     * 添加请求头信息
     *
     * @param headers 请求头
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req header(HttpHeaders headers);

    /**
     * 天机请求头信息
     *
     * @param headers 请求头
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req header(Map<String, String> headers);

    /**
     * 从请求头中移除键值
     *
     * @param key 请求头键名
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req removeHeader(String key);

    /**
     * 为构建本次{@linkplain Req}设置单独连接超时时间。
     *
     * @param connectTimeout 连接超时时间
     * @param timeUnit       超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req connectTimeout(Integer connectTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain Req}设置单独连接超时时间。
     *
     * @param connectTimeoutMilliseconds 连接超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req connectTimeoutMillis(Integer connectTimeoutMilliseconds);

    /**
     * 为构建本次{@linkplain Req}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间
     * @param timeUnit    超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req readTimeout(Integer readTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain Req}设置单独读取流超时。
     *
     * @param readTimeoutMilliseconds 流读取超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req readTimeoutMillis(Integer readTimeoutMilliseconds);

    /**
     * 为构建本次{@linkplain Req}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间
     * @param timeUnit     超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req writeTimeout(Integer writeTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain Req}设置单独写入流超时。
     *
     * @param writeTimeoutMilliseconds 流写入超时时间,单位毫秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req writeTimeoutMillis(Integer writeTimeoutMilliseconds);

    /**
     * 定制解码HTTP状态码
     *
     * @param httpStatuses HTTP状态码
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req decodeStatusCodes(List<HttpStatus> httpStatuses);

    /**
     * 解析{@code http status code 2xx}HTTP状态码
     *
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req decodeStatusCode2xx();


    Req decodeStatusCode2xxAnd4xx();

    /**
     * 为构建本次{@linkplain Req}设置HTTP的配置。
     *
     * @param options 选项配置
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req options(Options.Builder options);

    /**
     * 同步执行HTTP请求并返回原始响应对象
     *
     * @return {@linkplain Response}
     */
    Response execute();

    /**
     * 同步执行并处理响应内容转为字符串
     *
     * @return 响应结果字符串
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    String string() throws HttpClientException;

    /**
     * 将响应结果转为JavaBean对象
     *
     * @param targetClass 目标类型
     * @param <T>         泛型类型
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    <T> T bean(Class<T> targetClass) throws HttpClientException;

    /**
     * 将响应结果转为JavaBean对象
     * <p>
     * 用法如下：Map&lt;String,String&gt; data = BaseRequest.bean(new TypeRef&lt;Map&lt;String,String&gt;&gt;);
     * </p>
     *
     * @param typeRef 带有泛型类的封装类
     * @param <T>     泛型类型
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    <T> T bean(BaseTypeRef<T> typeRef) throws HttpClientException;

    /**
     * 将响应结果转为字节数组
     *
     * @return 字节数组
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    byte[] bytes() throws HttpClientException;

    /**
     * 将响应结果输出到文件中
     *
     * @param saveFile 目标保存文件,非空
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    void file(File saveFile) throws HttpClientException;

    /**
     * 将响应结果输出到输出流,并不会主动关闭输出流{@code out}
     *
     * @param out 输出流,非空
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    void outputStream(OutputStream out) throws HttpClientException;

}
