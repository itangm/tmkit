package cn.tmkit.core.support;

/**
 * 分页工具类
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-08-11
 */
public class PageUtil {

    /**
     * 根据总数计算总页数
     *
     * @param totalCount 总数
     * @param pageSize   每页数
     * @return 总页数
     */
    public static int totalPage(int totalCount, int pageSize) {
        return totalPage((long) totalCount, pageSize);
    }

    /**
     * 根据总数计算总页数
     *
     * @param totalCount 总数
     * @param pageSize   每页数
     * @return 总页数
     */
    public static int totalPage(long totalCount, int pageSize) {
        if (pageSize == 0) {
            return 0;
        }
        return Math.toIntExact(((totalCount - 1) / pageSize) + 1);
    }


}
