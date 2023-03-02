package cn.tmkit.json.sjf4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解JSON字段的
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@interface JsonField {

    String name() default "";

    String format() default "";

    /**
     * 是否支持序列化，如果值为{@code false}这不会序列化
     *
     * @return {@code true} or {@code false}
     */
    boolean serialize() default true;

    /**
     * 是否支持反序列化
     *
     * @return {@code true} or {@code false}
     */
    boolean deserialize() default true;

}
