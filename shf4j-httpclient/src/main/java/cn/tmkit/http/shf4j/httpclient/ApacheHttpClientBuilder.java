package cn.tmkit.http.shf4j.httpclient;

import cn.tmkit.http.shf4j.Client;
import cn.tmkit.http.shf4j.ClientBuilder;
import cn.tmkit.http.shf4j.Options;

/**
 * HttpClient构造
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
public class ApacheHttpClientBuilder extends ClientBuilder {

    @Override
    public Client build(Options options) {
        return new HttpComponentsHttpClient(options);
    }

}
