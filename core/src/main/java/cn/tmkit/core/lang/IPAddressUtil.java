package cn.tmkit.core.lang;

/**
 * IP地址工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-28
 */
public class IPAddressUtil {

    /**
     * 判断是否为IPV4
     *
     * @param src 待检测的字符串
     * @return 是否为IPv4
     */
    public static boolean isIPv4(String src) {
        if (StrUtil.isBlank(src)) {
            return false;
        }
        // 做一层简单的正则校验，不再支持老旧的简化写法，比如1代表的是0.0.0.1
        String regex = "^\\d{1,3}\\.\\d{1,3}.\\d{1,3}.\\d{1,3}$";
        if (!RegexUtil.isMatch(regex, src)) {
            return false;
        }
        return sun.net.util.IPAddressUtil.isIPv4LiteralAddress(src);
    }

    /**
     * 判断是否为IPV6
     *
     * @param src 待检测的字符串
     * @return 是否为IPv6
     */
    public static boolean isIPv6(String src) {
        if (StrUtil.isBlank(src)) {
            return false;
        }
        return sun.net.util.IPAddressUtil.isIPv6LiteralAddress(src);
    }

}
