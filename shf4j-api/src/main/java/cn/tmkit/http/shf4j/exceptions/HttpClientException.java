package cn.tmkit.http.shf4j.exceptions;

import cn.tmkit.core.exception.GenericRuntimeException;

/**
 * Base class for exceptions thrown by {@code HttpClient} whenever it encounters client-side HTTP errors.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpClientException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable ex) {
        super(message, ex);
    }

}
