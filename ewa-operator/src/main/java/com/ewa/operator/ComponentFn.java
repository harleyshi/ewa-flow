package com.ewa.operator;

import com.ewa.operator.core.factory.OperatorFactory;
import com.ewa.operator.core.factory.generate.SpringOperatorGenerate;

import java.lang.annotation.*;

/**
 * operator component annotation
 * @author harley.shi
 * @date 2024/7/1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentFn {

    Class<?> paramType() default Object.class;

    Class<? extends OperatorFactory> builder() default SpringOperatorGenerate.class;
}
