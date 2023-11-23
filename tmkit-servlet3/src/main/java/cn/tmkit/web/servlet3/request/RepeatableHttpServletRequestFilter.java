package cn.tmkit.web.servlet3.request;

import cn.tmkit.http.shf4j.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 基于Filter构建可重复流的HTTP Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class RepeatableHttpServletRequestFilter implements BaseFilter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest requestToUse = (HttpServletRequest) servletRequest;
        if (!isAsyncDispatch(servletRequest) && shouldCache(requestToUse) && !(servletRequest instanceof RepeatableHttpServletRequestWrapper)) {
            requestToUse = new RepeatableHttpServletRequestWrapper(requestToUse);
        }
        filterChain.doFilter(requestToUse, servletResponse);
    }

    @Override
    public void destroy() {

    }

    protected boolean shouldCache(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        return !HttpMethod.GET.match(method);
    }


}
