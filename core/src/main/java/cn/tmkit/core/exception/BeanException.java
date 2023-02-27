package cn.tmkit.core.exception;

/**
 * Bean Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-27
 */
public class BeanException extends GenericRuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * Bean Exception Constructor
     *
     * @param message 异常消息
     */
    public BeanException(String message) {
        super(message);
    }

    /**
     * Bean Exception Constructor
     *
     * @param message 异常消息
     * @param cause   异常
     */
    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Bean Exception Constructor
     *
     * @param cause 异常
     */
    public BeanException(Throwable cause) {
        super(cause);
    }

}
