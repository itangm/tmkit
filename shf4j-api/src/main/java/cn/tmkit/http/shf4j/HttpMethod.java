package cn.tmkit.http.shf4j;

import cn.tmkit.core.lang.StringUtil;

/**
 * HTTP请求方式
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public enum HttpMethod {

    /**
     * CONNECT
     */
    CONNECT,

    /**
     * DELETE
     */
    DELETE,

    /**
     * GET
     */
    GET,

    /**
     * HEAD
     */
    HEAD,

    /**
     * OPTIONS
     */
    OPTIONS,

    /**
     * PATCH
     */
    PATCH,

    /**
     * POST
     */
    POST,

    /**
     * PUT
     */
    PUT,

    /**
     * TRACE
     */
    TRACE,

    ;

    /**
     * 判断是否匹配该枚举值
     *
     * @param method 方法名
     * @return 是否匹配
     */
    public boolean match(String method) {
        return StringUtil.equalsIgnoreCase(this.name(), method);
    }

}
