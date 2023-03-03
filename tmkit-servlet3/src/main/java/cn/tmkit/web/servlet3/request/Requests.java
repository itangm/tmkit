package cn.tmkit.web.servlet3.request;

import cn.tmkit.core.exception.IoRuntimeException;
import cn.tmkit.core.io.FastByteArrayOutputStream;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.json.sjf4j.BaseTypeRef;
import cn.tmkit.json.sjf4j.util.JSONs;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * Request工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class Requests {

    /**
     * 获取用户的真正IP地址
     *
     * @param request request对象
     * @return 返回用户的IP地址
     * @see IPUtil#getClientIp(HttpServletRequest)
     */
    public static String getClientIp(HttpServletRequest request) {
        return IPUtil.getClientIp(request);
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param clazz   目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(HttpServletRequest request, Class<T> clazz) {
        String body = read2Bean(request);
        if (Strings.isBlank(body)) {
            return null;
        }
        return (Strings.isEmpty(body) ? null : JSONs.fromJson(body,
                Objects.requireNonNull(clazz, "clazz == null")));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param typeRef 目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(HttpServletRequest request, BaseTypeRef<T> typeRef) {
        String body = read2Bean(request);
        return (Strings.isEmpty(body) ? null : JSONs.fromJson(body, typeRef.getType()));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容
     *
     * @param request HTTP请求对象
     * @return body的内容
     */
    public static String read2Bean(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return null;
        }
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream(contentLength);
        try {
            IoUtil.copy(request.getInputStream(), outputStream);
            return outputStream.toString(Charsets.forName(request.getCharacterEncoding()));
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
