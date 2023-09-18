package cn.tmkit.core.bean;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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
public class OrderWithCoupon extends OrderWithMap {

    private Coupon coupon;

    /**
     *
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @SuperBuilder
    public static class Coupon {

        private Long id;

        private String name;

        private double price;

        private BigDecimal salePrice;

    }

}
