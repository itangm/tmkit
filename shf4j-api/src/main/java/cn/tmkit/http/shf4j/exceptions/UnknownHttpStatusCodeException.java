package cn.tmkit.http.shf4j.exceptions;

import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.http.shf4j.Request;

import java.nio.charset.Charset;

/**
 * Exception thrown when an unknown (or custom) HTTP status code is received.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class UnknownHttpStatusCodeException extends HttpClientResponseException {

    private static final long serialVersionUID = 1990L;

    public UnknownHttpStatusCodeException(int rawStatus, String reason, HttpHeaders responseHeaders,
                                          byte[] responseBody, Charset responseCharset, Request request) {
        super("Unknown status code [" + rawStatus + "]" + " " + reason, rawStatus, reason, responseHeaders,
                responseBody, responseCharset, request);
    }

}
