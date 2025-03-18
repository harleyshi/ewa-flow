package com.ewa.operator;

import com.ewa.operator.core.factory.OperatorFactory;
import com.ewa.operator.core.factory.generate.SpringOperatorGenerate;

import java.lang.annotation.*;

/**
 * This annotation is used to mark a class as a component function in the system.
 * It allows specifying the type of parameters the component node accepts and the
 * builder class responsible for creating the operator for this component.
 *
 * This annotation is retained at runtime and can be applied to types classes
 * @author harley.shi
 * @date 2024/7/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentFn {

    /**
     * Specifies the type of parameters that the component node accepts.
     * Defaults to {@link Object} if no specific type is provided.
     *
     * @return the class type of the node parameters
     */
    Class<?> nodeParamsType() default Object.class;

    /**
     * Specifies the class responsible for building the operator for this component.
     * Defaults to {@link SpringOperatorGenerate} if no specific builder is provided.
     *
     * @return the class type of the operator factory
     */
    Class<? extends OperatorFactory> builder() default SpringOperatorGenerate.class;
}
