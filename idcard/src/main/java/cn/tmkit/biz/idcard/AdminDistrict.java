package cn.tmkit.biz.idcard;

import cn.tmkit.core.lang.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 行政区代码
 * <p>根据<a href="https://xiangyuecn.gitee.io/areacity-jsspider-statsgov/">开源的行政区数据</a>做了一定裁剪，在此非常感谢<a href="https://gitee.com/xiangyuecn/AreaCity-JsSpider-StatsGov">开源项目</a></p>
 * <p>对比了JSON和CSV存放的大小</p>
 * <p>对比了JSON、KV和CSV内存占比和查询性能</p>
 * <p>以上代码在src/test/java/cn/tmkit/biz/idcard/IdCardResolverTest.java
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDistrict implements Serializable {

    /**
     * 代码
     */
    protected String code;

    /**
     * 名称
     */
    protected String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDistrict that = (AdminDistrict) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\", \"name\":\"" + name + "\"}";
    }

}
