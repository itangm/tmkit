package cn.tmkit.core.bean;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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
public class OrderInfo {

    private Long id;

    private String name;

    private int weight;

    private LocalDate orderDate;

    private OrderTypeEnum orderType;

    @Getter
    @AllArgsConstructor
    public enum OrderTypeEnum {

        PAY(1, "支付"),

        REFUND(2, "退款"),

        ;

        private final int code;

        private final String msg;

    }

}
