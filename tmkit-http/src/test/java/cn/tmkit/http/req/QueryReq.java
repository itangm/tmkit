package cn.tmkit.http.req;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueryReq implements Serializable {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 兴趣爱好
     */
    private List<String> hobbies;

}
