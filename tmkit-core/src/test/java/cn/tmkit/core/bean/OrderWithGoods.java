package cn.tmkit.core.bean;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

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
public class OrderWithGoods extends OrderWithMap {

    private List<Goods> goods;

    /**
     *
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @SuperBuilder
    public static class Goods {

        private Long id;

        private String name;

        private double price;

        private BigDecimal salePrice;

    }

}
