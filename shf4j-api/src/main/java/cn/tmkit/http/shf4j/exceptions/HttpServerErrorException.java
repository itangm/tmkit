package cn.tmkit.http.shf4j.exceptions;

import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.http.shf4j.HttpStatus;
import cn.tmkit.http.shf4j.Request;

import java.nio.charset.Charset;

/**
 * Exception thrown when an HTTP 5xx is received.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpServerErrorException extends HttpClientStatusException {

    private static final long serialVersionUID = 1990L;

    public HttpServerErrorException(HttpStatus statusCode, Request request) {
        super(statusCode, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, Request request) {
        super(statusCode, reason, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, byte[] responseBody,
                                    Charset responseCharset, Request request) {
        super(statusCode, reason, responseBody, responseCharset, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, HttpHeaders responseHeaders,
                                    byte[] responseBody, Charset responseCharset, Request request) {
        super(statusCode, reason, responseHeaders, responseBody, responseCharset, request);
    }


}
