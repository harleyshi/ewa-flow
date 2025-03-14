package com.ewa.operator;

import com.ewa.operator.common.enums.OpsType;
import com.ewa.operator.node.ConditionOperator;
import com.ewa.operator.node.Operator;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author harley.shi
 * @date 2025/1/21
 */
public class OperatorBeanPostProcessor implements BeanPostProcessor {

    private final OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        if (!Operator.class.isAssignableFrom(targetClass)) {
            return bean;
        }
        if (!targetClass.isAnnotationPresent(ComponentFn.class)) {
            return bean;
        }
        ComponentFn componentFn = targetClass.getAnnotation(ComponentFn.class);
        Object targetBean = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean;
        if (!(targetBean instanceof Operator<?, ?> operator)) {
            throw new RuntimeException("target bean is not an instance of Operator");
        }
        String operatorName = targetClass.getName();
        if(operatorsRegister.containsKey(operatorName)){
            throw new RuntimeException(String.format("Operator with name %s already exists", operatorName));
        }
        OpsType operatorType;
        if(operator instanceof ConditionOperator<?,?>){
            operatorType = OpsType.CONDITION;
        }else{
            operatorType = OpsType.STANDARD;
        }
        Class<?> nodeParam = componentFn.paramType();
        operatorsRegister.register(new OperatorDef<>(operatorName, nodeParam, operatorType, operator));
        return bean;
    }
}
