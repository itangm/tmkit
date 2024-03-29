package cn.tmkit.core.lang.regex;

/**
 * 额外的正则表达式常量
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-17
 */
public interface ExtraPatternConstant {

    /**
     * 标准日期时间正则，每个字段支持单个数字或2个数字，包括：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd HH:mm
     *     yyyy-MM-dd
     * </pre>
     */
    String REGEX_NORM = "\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?(.\\d{1,3})?";

    /**
     * 火车车次
     */
    String TRAIN_CODE = ("^[GCDZTSPKXLY1-9]\\d{1,4}$");

    /**
     * 车牌号(新能源+非新能源)
     */
    String PLATE_CODE = ("^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|" +
            "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|" +
            "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");

    /**
     * 社会统一信用代码
     * <pre>
     * 第一部分：登记管理部门代码1位 (数字或大写英文字母)
     * 第二部分：机构类别代码1位 (数字或大写英文字母)
     * 第三部分：登记管理机关行政区划码6位 (数字)
     * 第四部分：主体标识码（组织机构代码）9位 (数字或大写英文字母)
     * 第五部分：校验码1位 (数字或大写英文字母)
     * </pre>
     */
    String CREDIT_CODE = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$";

    /**
     * mac地址
     */
    String MAC_ADDR = "^((([a-f0-9]{2}:){5})|(([a-f0-9]{2}-){5}))[a-f0-9]{2}$";

    /**
     * 中国的邮政编码
     */
    String ZIPCODE = ("^(0[1-7]|1[0-356]|2[0-7]|3[0-6]|4[0-7]|5[1-7]|6[1-7]|7[0-5]|8[013-6])\\d{4}$");

    /**
     * 手机号(mobile phone)中国(严谨)，<a href="https://zh.wikipedia.org/zh-hans/%E4%B8%AD%E5%9B%BD%E5%A4%A7%E9%99%86%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E9%80%9A%E4%BF%A1%E5%8F%B7%E7%A0%81">参考维基百科</a>
     */
    String MOBILE_PHONE_STRICT_STR = ("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[0-35-9]))\\d{8}$");

}
