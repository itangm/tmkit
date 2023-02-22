package cn.tmkit.core.exception;

import java.net.URISyntaxException;

/**
 * URI Syntax Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-16
 */
public class UriSyntaxRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * URI Syntax Exception Constructor
     *
     * @param cause URI Syntax Exception
     */
    public UriSyntaxRuntimeException(URISyntaxException cause) {
        super(cause);
    }

}
