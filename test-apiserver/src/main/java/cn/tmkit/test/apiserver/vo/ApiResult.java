package cn.tmkit.test.apiserver.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-06
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApiResult<E> implements Serializable {

    private int code;
    private String message;

    private E data;

    public ApiResult() {
        this(FAIL_CODE, null);
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <E> ApiResult<E> fail(String message) {
        return new ApiResult<>(FAIL_CODE, message);
    }

    public static <E> ApiResult<E> success(E data) {
        return new ApiResult<>(SUCCESS_CODE, "success", data);
    }

    public static final int FAIL_CODE = 500;

    public static final int SUCCESS_CODE = 200;

}
