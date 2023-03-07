package cn.tmkit.test.apiserver.vo;

import cn.tmkit.http.shf4j.HttpHeaders;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 简单的表单提交
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SimplePostVO implements Serializable {

    /**
     * 参数
     */
    private Map<String, List<String>> parameterMap;

    private String queryString;

    private HttpHeaders httpHeaders;

}
