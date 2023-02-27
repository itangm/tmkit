package cn.tmkit.core.exception;

import java.text.ParseException;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 * 日期异常类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-24
 */
public class DateRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * Date Exception Constructor
     *
     * @param message 异常消息
     */
    public DateRuntimeException(String message) {
        super(message);
    }

    /**
     * Date Exception Constructor
     *
     * @param cause 不支持的类型异常
     */
    public DateRuntimeException(UnsupportedTemporalTypeException cause) {
        super(cause);
    }

    /**
     * Date Exception Constructor
     *
     * @param cause 解析异常
     */
    public DateRuntimeException(ParseException cause) {
        super(cause);
    }

}
