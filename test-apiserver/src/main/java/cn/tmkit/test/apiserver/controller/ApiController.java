package cn.tmkit.test.apiserver.controller;

import cn.tmkit.core.codec.Base64Util;
import cn.tmkit.core.io.FileUtil;
import cn.tmkit.core.lang.*;
import cn.tmkit.http.HttpClient;
import cn.tmkit.http.shf4j.HeaderName;
import cn.tmkit.http.shf4j.HttpHeaders;
import cn.tmkit.test.apiserver.properties.AppConfigProperties;
import cn.tmkit.test.apiserver.req.QueryReq;
import cn.tmkit.test.apiserver.req.UserReq;
import cn.tmkit.test.apiserver.vo.*;
import cn.tmkit.web.servlet3.request.IPUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-06
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ApiController {

    private String serverUrl = "http://localhost:8080";

    private final AppConfigProperties appConfigProperties;

    /**
     * 接收IP信息分析请求
     *
     * @param ip      ip地址，如果为空获取当前的请求地址
     * @param request HTTP请求对象
     * @return 接口处理结果
     */
    @GetMapping("/ip-info")
    public ApiResult<IpApiInfo> getIpInfo(String ip, HttpServletRequest request) {
        log.info(" <=== 接收IP信息分析请求，请求参数->ip={}", ip);
        ApiResult<IpApiInfo> apiResult;
        if (StringUtils.isEmpty(ip)) {
            ip = IPUtil.getClientIp(request);
        }
        if (StringUtils.isEmpty(ip)) {
            apiResult = ApiResult.fail("自动获取客户端IP失败");
            log.error(" ===> ip is empty {}", apiResult);
            return apiResult;
        }
        if (!IPUtil.isIPv4(ip)) {
            apiResult = ApiResult.fail("IP格式不正确");
            log.error(" ===> invalid ip format {}", apiResult);
            return apiResult;
        }
        if ("127.0.0.1".equals(ip)) {
            IpApiInfo ipApiInfo = IpApiInfo.builder()
                    .query(ip)
                    .status(IpApiInfo.SUCCESS)
                    .build();
            apiResult = ApiResult.success(ipApiInfo);
            log.info(" ip is local address {}", apiResult);
            return apiResult;
        }
        String url = Strings.format(appConfigProperties.getIpApi(), ip);
        IpApiInfo ipApiInfo = HttpClient.get(url).bean(IpApiInfo.class);
        log.info("Ip query result url = {} ,ip = {}, ipApiInfo = {}", url, ip, ipApiInfo);
        if (!ipApiInfo.success()) {
            apiResult = ApiResult.fail(ipApiInfo.getMessage());
            log.error(" ===> 接收IP信息分析请求处理完毕->{}", apiResult);
            return apiResult;
        }
        apiResult = ApiResult.success(ipApiInfo);
        log.info(" ===> 接收IP信息分析请求处理完毕->{}", apiResult);
        return apiResult;
    }

    /**
     * GET请求的，单个，多个参数
     *
     * @param req 请求参数
     * @return 原样返回
     */
    @GetMapping("/query")
    public ApiResult<QueryReq> query(@Validated QueryReq req) {
        // 兴趣爱好应该是一个数组
        log.info("Query request {}", req);
        return ApiResult.success(req);
    }

    /**
     * 普通的表单提交
     *
     * @param request HTTP请求对象
     * @return 处理结果
     */
    @PostMapping("/post/simple")
    public ApiResult<SimplePostVO> simplePost(HttpServletRequest request) {
        log.info(" <=== 接收普通的表单提交请求");
        SimplePostVO simplePostVO = new SimplePostVO();

        simplePostVO.setQueryString(request.getQueryString());
        simplePostVO.setParameterMap(handleRequestParams(request));
        simplePostVO.setHttpHeaders(handleRequestHeaders(request));

        log.info(" ===> Simple Post Parse success {}", simplePostVO);
        return ApiResult.success(simplePostVO);
    }

    /**
     * 含文件的表单提交
     *
     * @param request HTTP请求对象
     * @return 处理结果
     */
    @PostMapping("/post/form")
    @SuppressWarnings("ConstantConditions")
    public ApiResult<FormPostVO> formPost(HttpServletRequest request) throws IOException {
        log.info(" <=== 接收含文件的表单提交请求");
        FormPostVO formPostVO = new FormPostVO();

        formPostVO.setQueryString(request.getQueryString());
        formPostVO.setParameterMap(handleRequestParams(request));
        formPostVO.setHttpHeaders(handleRequestHeaders(request));


        if (request instanceof MultipartHttpServletRequest) {
            Map<String, List<FileData>> fileDataMap = new LinkedHashMap<>();
            String realPath = request.getServletContext().getRealPath("");
            File parentFile = new File(realPath + "/public/images");
            FileUtil.mkdir(parentFile);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            for (Map.Entry<String, List<MultipartFile>> entry : multipartRequest.getMultiFileMap().entrySet()) {
                for (MultipartFile multipartFile : entry.getValue()) {
                    FileData fileData = new FileData();
                    fileData.setOldFilename(multipartFile.getOriginalFilename());
                    fileData.setFileExt(FileUtil.getFileExt(fileData.getOldFilename()));
                    fileData.setNewFilename(System.nanoTime() + "." + fileData.getFileExt());
                    fileData.setFileSize((int) multipartFile.getSize());
                    fileData.setShowUrl(serverUrl + "/public/images/" + fileData.getNewFilename());
                    FileUtil.copy(multipartFile.getInputStream(), new File(parentFile, fileData.getNewFilename()));

                    List<FileData> list = MapUtil.computeIfAbsent(fileDataMap, multipartFile.getName(), s -> CollectionUtils.arrayList());
                    list.add(fileData);
                }
            }
            formPostVO.setFiles(fileDataMap);
        }

        log.info(" ===> 接收含文件的表单提交请求处理完毕->{}", formPostVO);
        return ApiResult.success(formPostVO);
    }

    @PostMapping(value = "/post/json")
    public ApiResult<UserReq> postJson(@RequestBody UserReq userReq) {
        log.info(" <=== userReq = {}", userReq);
        ApiResult<UserReq> apiResult = ApiResult.success(userReq);
        log.info(" ===> 接收请求体(POST Text Body)请求处理完毕->{}", apiResult);
        return apiResult;
    }

    @PostMapping("/post/body/binary")
    public ApiResult<String> binaryBodyPost(@RequestBody byte[] data) {
        log.info(" <=== 接收提交请求体(POST Binary Body)请求");
        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setData(Base64Util.encode(data));

        log.info(" ===> 接收请求体(POST Binary Body)请求处理完毕->{}", apiResult);
        return apiResult;
    }

    private Map<String, List<String>> handleRequestParams(HttpServletRequest request) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            result.put(entry.getKey(), Collections.of(entry.getValue()));
        }
        return result;
    }

    private HttpHeaders handleRequestHeaders(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();

        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerKey = enumeration.nextElement();
            httpHeaders.append(HeaderName.parse(headerKey), request.getHeaders(headerKey));
        }
        return httpHeaders;
    }

}
