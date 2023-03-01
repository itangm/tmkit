package cn.tmkit.core.exception;

/**
 * 转换器异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class ConverterRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * Converter Exception Constructor
     *
     * @param message 异常消息
     */
    public ConverterRuntimeException(String message) {
        super(message);
    }

}
