package com.ewa.operator.core.factory;

/**
 * @author harley.shi
 * @date 2025/3/17
 */
public interface OperatorFactory {

    <T> T create(String className);

    <T> T create(String className, Class<?> nodeParamType, String paramsKey, String paramsValue);

}
