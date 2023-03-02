package cn.tmkit.json.sjf4j;

import cn.tmkit.core.exception.GenericRuntimeException;

/**
 * Json RuntimeException
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
public class JsonRuntimeException extends GenericRuntimeException {

    public JsonRuntimeException(Exception e) {
        super(e);
    }

}
