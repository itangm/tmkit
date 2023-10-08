package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Deletre Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class DeleteRequest extends AbstractBaseRequest<DeleteRequest> {

    public DeleteRequest(String url) {
        this(url, HttpClient.DEFAULT_CLIENT_NAME);
    }

    public DeleteRequest(String url, String clientName) {
        this.url = url;
        this.method = HttpMethod.DELETE;
        this.clientName = clientName;
    }

}
