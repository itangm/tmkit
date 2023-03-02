package cn.tmkit.http.shf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Client Provider
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClientProvider {

    String value();

}
