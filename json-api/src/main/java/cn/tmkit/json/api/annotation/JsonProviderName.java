package cn.tmkit.json.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON的实现类的名，用于区分不同框架的实现别名
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JsonProviderName {

    /**
     * JSON 厂商名
     *
     * @return 厂商名
     */
    String value();

    /**
     * 加载的顺序，值越小越先加载
     *
     * @return 加载顺序值
     */
    int index() default Integer.MAX_VALUE;

}
