package cn.tmkit.core.bean;

import cn.tmkit.core.lang.MapUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tester for {@linkplain BeanUtil}
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-09-15
 */
public class BeanUtilTest {

    @Test
    public void copyProperties() {
        OrderInfo source = OrderInfo.builder().id(1L)
                .name("吃饭够")
                .weight(20)
                .orderDate(LocalDate.now())
                .orderType(OrderInfo.OrderTypeEnum.PAY)
                .build();
        OrderInfo target = BeanUtil.copyProperties(source, OrderInfo.class);
        assertNotNull(target);
    }

    @Test
    public void testCopyProperties() {
        OrderWithMap source = OrderWithMap.builder().id(1L)
                .name("吃饭够")
                .weight(20)
                .orderDate(LocalDate.now())
                .orderType(OrderInfo.OrderTypeEnum.PAY)
                .attrs(MapUtil.of("specialFlag", true, "fee", 100))
                .build();
        OrderWithMap target = BeanUtil.copyProperties(source, OrderWithMap.class);
        assertNotNull(target);
    }

    @Test
    public void testCopyProperties1() {
    }

    @Test
    void testCopyProperties2() {
    }

    @Test
    void testCopyProperties3() {
    }

    @Test
    void testCopyProperties4() {
    }

    @Test
    void testCopyProperties5() {
    }

    @Test
    void testCopyProperties6() {
    }

    @Test
    void testCopyProperties7() {
    }

    @Test
    void testCopyProperties8() {
    }
}
