package cn.tmkit.json.api;

import tech.fastool.core.exceptions.GenericRuntimeException;

/**
 * Json RuntimeException
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class JsonRuntimeException extends GenericRuntimeException {

    public JsonRuntimeException(Exception e) {
        super(e);
    }

}
