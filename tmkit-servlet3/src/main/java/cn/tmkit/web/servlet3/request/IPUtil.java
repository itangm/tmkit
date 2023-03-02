package cn.tmkit.web.servlet3.request;

import cn.tmkit.core.lang.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * IP Utilities
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class IPUtil extends cn.tmkit.core.lang.IPUtil {

    public static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    /**
     * 获取用户的真正IP地址
     *
     * @param request request对象
     * @return 返回用户的IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = null;
        for (String header : HEADERS_TO_TRY) {
            ip = request.getHeader(header);
            if (Strings.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        // 多次反向代理后会有多个ip值，第一个ip才是真实ip
        // https://help.aliyun.com/document_detail/54007.html
        if (Strings.hasText(ip)) {
            int index = ip.indexOf(Strings.COMMA);
            if (index > 0) {
                return ip.substring(0, index);
            }
        }
        return ip;
    }

}
