package cn.tmkit.biz.idcard;

import cn.tmkit.core.lang.Collections;
import cn.tmkit.core.lang.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 较小的行政区结构
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class InternalAdObj {

    /**
     * 代码
     */
    private String c;

    /**
     * 名称
     */
    private String n;

    private String p;

    /**
     * 子节点
     */
    private List<InternalAdObj> r;

    public InternalAdObj(String c, String n) {
        this(c, n, null);
    }

    public InternalAdObj(String c, String n, List<InternalAdObj> r) {
        this(c, n, null, r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalAdObj internalAdObj = (InternalAdObj) o;
        return c.equals(internalAdObj.c);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append('{');
        sb.append('"').append('c').append('"');
        sb.append(':');
        sb.append('"').append(c).append('"');
        sb.append(',');
        sb.append('"').append('n').append('"');
        sb.append(':');
        sb.append(n);
        if (Collections.isNotEmpty(r)) {
            sb.append(',');
            sb.append('"').append('r').append('"');
            sb.append(':');
            sb.append('[');
            for (int i = 0; i < r.size(); i++) {
                sb.append(r.get(i).toString());
                if (i + 1 < r.size()) {
                    sb.append(',');
                }
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }

}
