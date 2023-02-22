package cn.tmkit.core.exception;

/**
 * Class Not Found Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class ClassNotFoundRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2023L;

    /**
     * Class Not Found Exception Constructor
     *
     * @param cause 异常
     */
    public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
        super(cause);
    }

}
