package cn.tmkit.biz.idcard;

import cn.tmkit.biz.idcard.constants.Gender;
import cn.tmkit.core.lang.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 身份证信息
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
@Getter
@RequiredArgsConstructor
public class IdCard {

    /**
     * 居民身份证号码
     */
    private final String idCard;

    /**
     * 省份代码
     */
    private final AdminDistrict province;

    /**
     * 城市代码
     */
    private final AdminDistrict city;

    /**
     * 区县代码
     */
    private final AdminDistrict district;

    /**
     * 性别
     */
    private final Gender gender;

    /**
     * 出生日期
     */
    private final String birthday;

    /**
     * 获取年龄
     *
     * @return 年龄
     */
    public int getAge() {
        return getAge(null);
    }

    /**
     * 获取年龄
     *
     * @param comparedDate 待比较的日期
     * @return 年龄
     */
    public int getAge(LocalDate comparedDate) {
        LocalDate endDate = Objects.getIfNull(comparedDate, LocalDate.now());
        LocalDate birth = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (birth.isAfter(endDate)) {
            throw new IllegalArgumentException("Birthday is after comparedDate");
        }
        return birth.until(endDate).getYears();
    }

    /**
     * 出生日期
     *
     * @return 出生日期
     */
    public LocalDate birthday() {
        return LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
