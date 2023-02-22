package cn.tmkit.core.exception;

/**
 * 克隆异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-12
 */
public class CloneRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 20223;

    /**
     * Clone Exception Constructor
     *
     * @param cause 异常
     */
    public CloneRuntimeException(CloneNotSupportedException cause) {
        super(cause);
    }

}
