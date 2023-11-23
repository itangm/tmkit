package cn.tmkit.web.servlet3.request;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;

/**
 * 基础的Filter
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-11-23
 */
public interface BaseFilter extends Filter {

    /**
     * 判断是否是异步分发
     *
     * @param request the current request
     */
    default boolean isAsyncDispatch(ServletRequest request) {
        return DispatcherType.ASYNC.equals(request.getDispatcherType());
    }


}
