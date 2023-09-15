package cn.tmkit.core.bean;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 订单信息
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-09-15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class OrderWithMap extends OrderInfo {

    private Map<String, Object> attrs;

}
