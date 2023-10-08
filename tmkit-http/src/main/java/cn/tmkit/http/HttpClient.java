package cn.tmkit.http;

import cn.tmkit.core.lang.Maps;
import cn.tmkit.http.shf4j.Client;
import cn.tmkit.http.shf4j.Factory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * HttpClient更高级的工具类入口
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class HttpClient {

    protected static final String DEFAULT_CLIENT_NAME = "shf4j-default";

    /**
     * 缓存的客户端
     */
    private static final Map<String, Client> CLIENTS = Maps.concurrentHashMap();

    static {
        CLIENTS.put(DEFAULT_CLIENT_NAME, Factory.get().build(null));
    }

    /**
     * 获取自定义的客户端
     *
     * @param clientName 客户端名
     * @return {@linkplain Client}的实现
     */
    public static Client getClient(@NotNull String clientName) {
        return CLIENTS.get(clientName);
    }

    /**
     * 配置自定义Client
     *
     * @param clientName 自定义的名称
     * @param client     自定义的client
     */
    public static void setClient(@NotNull String clientName, @NotNull Client client) {
        CLIENTS.put(clientName, client);
    }

    /**
     * 配置全局Client
     *
     * @param client 自定义的client
     */
    public static void globalClient(@NotNull Client client) {
        setClient(DEFAULT_CLIENT_NAME, client);
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String url) {
        return new GetRequest(url);
    }

    /**
     * Get请求
     *
     * @param url        请求地址
     * @param clientName 自定义的client
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String url, @NotNull String clientName) {
        return new GetRequest(url, clientName);
    }

//    /**
//     * Get请求
//     *
//     * @param urlPattern 请求地址
//     * @return {@link GetRequest}
//     */
//    public static GetRequest get(@NotNull String urlPattern, Object... args) {
//        return new GetRequest(Strings.format(urlPattern, args));
//    }

    /**
     * FORM/POST表单提交
     *
     * @param url 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(@NotNull String url) {
        return new PostRequest(url);
    }

    /**
     * FORM/POST表单提交
     *
     * @param url        提交地址
     * @param clientName 自定义的client
     * @return {@link PostRequest}
     */
    public static PostRequest post(@NotNull String url, @NotNull String clientName) {
        return new PostRequest(url, clientName);
    }

//    /**
//     * FORM/POST表单提交
//     *
//     * @param urlPattern 提交地址
//     * @return {@link PostRequest}
//     */
//    public static PostRequest post(String urlPattern, Object... args) {
//        return new PostRequest(Strings.format(urlPattern, args));
//    }

    /**
     * DELETE 请求
     *
     * @param url 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String url) {
        return new DeleteRequest(url);
    }

    /**
     * DELETE 请求
     *
     * @param url        请求地址
     * @param clientName 自定义的client
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String url, @NotNull String clientName) {
        return new DeleteRequest(url, clientName);
    }

//    /**
//     * DELETE 请求
//     *
//     * @param urlPattern 请求地址
//     * @return {@linkplain DeleteRequest}
//     */
//    public static DeleteRequest delete(@NotNull String urlPattern, Object... args) {
//        return new DeleteRequest(Strings.format(urlPattern, args));
//    }

    /**
     * HEAD 请求
     *
     * @param url 请求地址
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String url) {
        return new HeadRequest(url);
    }

    /**
     * HEAD 请求
     *
     * @param url        请求地址
     * @param clientName 自定义的client
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String url, @NotNull String clientName) {
        return new HeadRequest(url, clientName);
    }

//
//    /**
//     * HEAD 请求
//     *
//     * @param urlPattern 请求地址
//     * @return {@linkplain HeadRequest}
//     */
//    public static HeadRequest head(@NotNull String urlPattern, Object... args) {
//        return new HeadRequest(Strings.format(urlPattern, args));
//    }

    /**
     * PATCH 请求
     *
     * @param url 请求地址
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String url) {
        return new PatchRequest(url);
    }

    /**
     * PATCH 请求
     *
     * @param url        请求地址
     * @param clientName 自定义的client
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String url, @NotNull String clientName) {
        return new PatchRequest(url, clientName);
    }

//    /**
//     * PATCH 请求
//     *
//     * @param urlPattern 请求地址
//     * @return {@linkplain PatchRequest}
//     */
//    public static PatchRequest patch(@NotNull String urlPattern, Object... args) {
//        return new PatchRequest(Strings.format(urlPattern, args));
//    }

    /**
     * PUT 请求
     *
     * @param url 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String url) {
        return new PutRequest(url);
    }

    /**
     * PUT 请求
     *
     * @param url        请求地址
     * @param clientName 自定义的client
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String url, @NotNull String clientName) {
        return new PutRequest(url, clientName);
    }

//    /**
//     * PUT 请求
//     *
//     * @param urlPattern 请求地址
//     * @return {@linkplain PutRequest}
//     */
//    public static PutRequest put(@NotNull String urlPattern, Object... args) {
//        return new PutRequest(Strings.format(urlPattern, args));
//    }

}
