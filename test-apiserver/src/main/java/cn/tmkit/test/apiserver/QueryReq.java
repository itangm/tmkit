package cn.tmkit.test.apiserver;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author ming.tang
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
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 兴趣爱好
     */
    @NotEmpty(message = "兴趣爱好不能为空")
    @Valid
    private List<@NotBlank(message = "兴趣爱好不能为空") String> hobbies;

}
