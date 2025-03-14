package com.ewa.operator;

import java.lang.annotation.*;

/**
 * 算子节点注解
 * @author harley.shi
 * @date 2024/7/1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentFn {

    Class<?> paramType() default Object.class;
}
