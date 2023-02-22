package cn.tmkit.core.exception;

/**
 * 通用的运行时异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class GenericRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * Generic Runtime Exception Constructor
     */
    public GenericRuntimeException() {
        super();
    }

    /**
     * Generic Runtime Exception Constructor
     *
     * @param cause 异常
     */
    public GenericRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Generic Runtime Exception Constructor
     *
     * @param message 异常消息
     */
    public GenericRuntimeException(String message) {
        super(message);
    }

    /**
     * Generic Runtime Exception Constructor
     *
     * @param message 异常消息
     * @param cause   异常
     */
    public GenericRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Generic Runtime Exception Constructor
     *
     * @param message            异常消息
     * @param cause              异常
     * @param enableSuppression  whether or not suppression is enabled  or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public GenericRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
