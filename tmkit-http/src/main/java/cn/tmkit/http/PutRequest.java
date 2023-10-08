package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Put Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class PutRequest extends BaseBodyRequest<PutRequest> {

    public PutRequest(String url) {
        this(url, HttpClient.DEFAULT_CLIENT_NAME);
    }

    public PutRequest(String url, String clientName) {
        this.url = url;
        this.method = HttpMethod.PUT;
        this.clientName = clientName;
    }

}
