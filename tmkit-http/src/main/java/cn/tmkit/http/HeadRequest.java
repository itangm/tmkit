package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Head Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class HeadRequest extends AbstractBaseRequest<HeadRequest> {

    public HeadRequest(String url) {
        this(url, HttpClient.DEFAULT_CLIENT_NAME);
    }

    public HeadRequest(String url, String clientName) {
        this.url = url;
        this.method = HttpMethod.HEAD;
        this.clientName = clientName;
    }

}
