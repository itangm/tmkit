package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Post Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class PostRequest extends BaseBodyRequest<PostRequest> {

    public PostRequest(String url) {
        this(url, HttpClient.DEFAULT_CLIENT_NAME);
    }

    public PostRequest(String url, String clientName) {
        this.url = url;
        this.method = HttpMethod.POST;
        this.clientName = clientName;
    }

}
