package cn.tmkit.biz.idcard.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-22
 */
@Getter
@AllArgsConstructor
public enum Gender {

    /**
     * 男
     */
    MALE(1, "男"),

    /***
     * 女
     */
    FEMALE(2, "女"),
    ;

    private final int code;

    private final String desc;

}
