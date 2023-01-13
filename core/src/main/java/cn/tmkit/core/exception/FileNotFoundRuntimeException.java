package cn.tmkit.core.exception;

/**
 * File Not Found Exception
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class FileNotFoundRuntimeException extends IoRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * File Not Found Exception Constructor
     *
     * @param message 异常消息
     */
    public FileNotFoundRuntimeException(String message) {
        super(message);
    }

}
