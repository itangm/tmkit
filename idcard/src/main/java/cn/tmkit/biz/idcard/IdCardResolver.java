package cn.tmkit.biz.idcard;

import cn.tmkit.biz.idcard.constants.Gender;
import cn.tmkit.biz.idcard.exception.IdCardInvalidException;
import cn.tmkit.core.io.IoUtil;
import cn.tmkit.core.lang.ClassLoaderUtil;
import cn.tmkit.core.lang.Maps;
import cn.tmkit.core.lang.Strings;
import cn.tmkit.core.support.IdCardUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 身份证解析类
 * 验证对比工具: https://www.haoshudi.com/shenfenzheng/
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class IdCardResolver {

    private static Map<String, InternalAdObj> allAds;

    /**
     * 载入行政区
     */
    public static synchronized void loadAds() {
        if (allAds == null) {
            allAds = Maps.newHashMap(3600);
            IoUtil.readLines(ClassLoaderUtil.getInputStream("admin-districts.min.csv")).forEach(line -> {
                if (Strings.isNotBlank(line)) {
                    List<String> items = Strings.split(line);
                    allAds.put(items.get(0), new InternalAdObj(items.get(0), items.get(1)));
                }
            });
        }
    }

    /**
     * 清空缓存，是否内存
     */
    public static void clear() {
        allAds.clear();
        allAds = null;
    }

    /**
     * 解析身份证号码
     *
     * @param str 身份证号码
     * @return 身份证对象
     */
    @NotNull
    public static IdCard resolve(@NotNull String str) {
        // 载入行政区
        loadAds();
        if (!IdCardUtil.isValid(str)) {
            throw new IdCardInvalidException();
        }
        // 解析行政区
        String code = str.substring(0, 2);
        InternalAdObj provinceInternalAdObj = allAds.get(code);
        if (provinceInternalAdObj == null) {
            provinceInternalAdObj = new InternalAdObj(code, "");
        }
        code = str.substring(2, 4);
        InternalAdObj cityInternalAdObj = allAds.get(code);
        if (cityInternalAdObj == null) {
            cityInternalAdObj = new InternalAdObj(code, "");
        }
        code = str.substring(2, 4);
        InternalAdObj areaInternalAdObj = allAds.get(code);
        if (areaInternalAdObj == null) {
            areaInternalAdObj = new InternalAdObj(code, "");
        }

        // 解析出生日期
        String birthday = str.substring(6, 14);

        // 性别
        char ch = str.charAt(16);
        Gender gender = (ch % 2 != 0) ? Gender.MALE : Gender.FEMALE;
        return new IdCard(str,
                new AdminDistrict(provinceInternalAdObj.getC(), provinceInternalAdObj.getN()),
                new AdminDistrict(cityInternalAdObj.getC(), cityInternalAdObj.getN()),
                new AdminDistrict(areaInternalAdObj.getC(), areaInternalAdObj.getN()),
                gender,
                birthday);
    }

}
