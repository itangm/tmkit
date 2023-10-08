package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Patch Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class PatchRequest extends BaseBodyRequest<PatchRequest> {

    public PatchRequest(String url) {
        this(url, HttpClient.DEFAULT_CLIENT_NAME);
    }

    public PatchRequest(String url, String clientName) {
        this.url = url;
        this.method = HttpMethod.PATCH;
        this.clientName = clientName;
    }

}
