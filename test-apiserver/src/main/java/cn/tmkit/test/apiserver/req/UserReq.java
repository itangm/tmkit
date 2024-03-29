package cn.tmkit.test.apiserver.req;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
public class UserReq {

    private String no;

    private String name;


}
