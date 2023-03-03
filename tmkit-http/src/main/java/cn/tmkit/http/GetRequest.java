package cn.tmkit.http;

import cn.tmkit.http.shf4j.HttpMethod;

/**
 * Get Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-03
 */
public class GetRequest extends AbstractBaseRequest<GetRequest> {

    public GetRequest(String url) {
        this.url = url;
        this.method = HttpMethod.GET;
    }

}
