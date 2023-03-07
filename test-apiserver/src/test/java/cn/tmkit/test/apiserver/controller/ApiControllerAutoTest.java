package cn.tmkit.test.apiserver.controller;

import cn.tmkit.core.lang.CollectionUtils;
import cn.tmkit.core.lang.MapUtil;
import cn.tmkit.core.support.Console;
import cn.tmkit.http.HttpClient;
import cn.tmkit.json.sjf4j.BaseTypeRef;
import cn.tmkit.test.apiserver.QueryReq;
import cn.tmkit.test.apiserver.vo.ApiResult;
import cn.tmkit.test.apiserver.vo.IpApiInfo;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
public class ApiControllerAutoTest {

    private static final String serviceUrl = "http://localhost:8080";

    @Test
    public void getIpInfo() {
        String url = serviceUrl + "/ip-info";
        ApiResult<IpApiInfo> apiResult = HttpClient.get(url).bean(new BaseTypeRef<ApiResult<IpApiInfo>>() {
        });
        assertNotNull(apiResult);
        assertEquals(ApiResult.SUCCESS_CODE, apiResult.getCode());
        IpApiInfo ipApiInfo = apiResult.getData();
        assertNotNull(ipApiInfo);
        assertTrue(ipApiInfo.success());
        Console.log(ipApiInfo);
    }

    @Test
    public void getIpInfoForSpecialIp() {
        String url = serviceUrl + "/ip-info";
        ApiResult<IpApiInfo> apiResult = HttpClient.get(url).queryParam("ip", "117.136.12.79").bean(new BaseTypeRef<ApiResult<IpApiInfo>>() {
        });
        assertNotNull(apiResult);
        assertEquals(ApiResult.SUCCESS_CODE, apiResult.getCode());
        IpApiInfo ipApiInfo = apiResult.getData();
        assertNotNull(ipApiInfo);
        assertTrue(ipApiInfo.success());
        Console.log(ipApiInfo);
    }

    @Test
    public void query() {
        String url = serviceUrl + "/query";
        Map<String, Object> queryParams = MapUtil.hashMap(4);
        queryParams.put("hobbies", CollectionUtils.of("睡觉", "还是睡觉"));
        ApiResult<QueryReq> apiResult = HttpClient.get(url).queryParam("nickname", "三月")
                .queryParam(queryParams)
                .bean(new BaseTypeRef<ApiResult<QueryReq>>() {
                });
        assertNotNull(apiResult);
        assertEquals(ApiResult.SUCCESS_CODE, apiResult.getCode());
        QueryReq queryReq = apiResult.getData();
        assertNotNull(queryReq);
        Console.log(queryReq);
    }

    @Test
    public void simplePost() {
    }

    @Test
    public void formPost() {
    }

    @Test
    public void rawBodyPost() {
    }

    @Test
    public void binaryBodyPost() {
    }
}
