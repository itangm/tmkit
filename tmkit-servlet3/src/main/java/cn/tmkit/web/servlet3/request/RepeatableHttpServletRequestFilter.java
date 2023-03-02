package cn.tmkit.web.servlet3.request;

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
public class RepeatableHttpServletRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String method = request.getMethod().toUpperCase();
            if (!"GET".equals(method)) {
                request = new RepeatableHttpServletRequestWrapper(request);
            }
            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
