package cn.tmkit.http.shf4j.exceptions;

import cn.tmkit.core.lang.Charsets;
import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.http.shf4j.Request;

import java.nio.charset.Charset;

/**
 * Common base class for exceptions that contain actual HTTP response data.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class HttpClientResponseException extends HttpClientException {

    private static final long serialVersionUID = 2023L;

    private final int rawStatus;

    protected final String reason;

    private final byte[] responseBody;

    private final HttpHeaders responseHeaders;

    private final Charset responseCharset;

    private final Request request;

    public HttpClientResponseException(String message, int rawStatus, String reason, HttpHeaders responseHeaders,
                                       byte[] responseBody, Charset responseCharset, Request request) {
        super(message);
        this.rawStatus = rawStatus;
        this.reason = reason;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.responseCharset = Charsets.getCharset(responseCharset, Charsets.UTF_8);
        this.request = request;
    }

    public int getRawStatus() {
        return rawStatus;
    }

    public String getReason() {
        return reason;
    }

    public byte[] getResponseBodyAsByteArray() {
        return responseBody;
    }

    public String getResponseBodyAsString() {
        return new String(this.responseBody, this.responseCharset);
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public Request getRequest() {
        return request;
    }

}
