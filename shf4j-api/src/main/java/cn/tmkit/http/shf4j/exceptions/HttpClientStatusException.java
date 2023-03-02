package cn.tmkit.http.shf4j.exceptions;

import cn.tmkit.core.lang.Charsets;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.http.shf4j.HttpStatus;
import cn.tmkit.http.shf4j.Request;

import java.nio.charset.Charset;

/**
 * Http Client 异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpClientStatusException extends HttpClientResponseException {

    private static final long serialVersionUID = 1990L;

    private final HttpStatus statusCode;


    public HttpClientStatusException(HttpStatus statusCode, Request request) {
        this(statusCode, statusCode.name(), request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, Request request) {
        this(statusCode, reason, null, null, request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, byte[] responseBody, Charset responseCharset,
                                     Request request) {
        this(statusCode, reason, null, responseBody, responseCharset, request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, HttpHeaders responseHeaders,
                                     byte[] responseBody, Charset responseCharset, Request request) {
        super(statusCode.value() + " " + (Strings.hasLength(reason) ? reason : new String(responseBody, Charsets.getCharset(responseCharset))),
                statusCode.value(), reason, responseHeaders, responseBody, responseCharset, request);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

}
