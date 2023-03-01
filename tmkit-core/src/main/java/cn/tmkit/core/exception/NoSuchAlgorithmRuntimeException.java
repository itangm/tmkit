package cn.tmkit.core.exception;

import java.security.NoSuchAlgorithmException;

/**
 * No Such Algorithm Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-02-23
 */
public class NoSuchAlgorithmRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Constructor
     *
     * @param cause 异常类
     */
    public NoSuchAlgorithmRuntimeException(NoSuchAlgorithmException cause) {
        super(cause);
    }

}
