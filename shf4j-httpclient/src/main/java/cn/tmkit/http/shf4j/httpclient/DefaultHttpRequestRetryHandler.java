package cn.tmkit.http.shf4j.httpclient;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * 重试机制
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler {

    private final int retryCount;

    public DefaultHttpRequestRetryHandler(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        // 如果已经重试了n次，就放弃
        if (executionCount >= retryCount) {
            return false;
        }
        // 如果服务器丢掉了连接，那么就重试
        if (exception instanceof NoHttpResponseException) {
            return true;
        }
        // 不重试SSL握手异常
        if (exception instanceof SSLHandshakeException) {
            return false;
        }
        // 中断
        if (exception instanceof InterruptedIOException) {
            return false;
        }
        // 目标服务器不可达
        if (exception instanceof UnknownHostException) {
            return true;
        }
        // SSL握手异常
        if (exception instanceof SSLException) {
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        // 如果请求是幂等的，就再次尝试
        if (!(request instanceof HttpEntityEnclosingRequest)) {
            return true;
        }

        return false;
    }

}
