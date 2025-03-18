package com.ewa.operator;

import cn.hutool.extra.spring.SpringUtil;
import com.ewa.operator.common.enums.OpsType;
import com.ewa.operator.core.factory.generate.DefaultOperatorGenerate;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.core.ConditionOperator;
import com.ewa.operator.core.Operator;
import com.ewa.operator.core.factory.OperatorFactory;
import com.ewa.operator.core.factory.generate.SpringOperatorGenerate;
import com.ewa.operator.utils.AssertUtil;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author harley.shi
 * @date 2025/3/17
 */
public class OperatorsScanner {
    private final OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();

    /**
     * scan all operators and register to OperatorsRegister
     */
    @SuppressWarnings("unchecked")
    public void scannerOperators(String... packagePaths) {
        Collection<URL> urls = new HashSet<>();
        for (String packagePath : packagePaths) {
            urls.addAll(ClasspathHelper.forPackage(packagePath));
        }
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(Scanners.TypesAnnotated)
                .filterInputsBy(new FilterBuilder() {
                    {
                        for (String packagePath : packagePaths) {
                            includePackage(packagePath);
                        }
                    }
                }));
        Set<Class<?>> clazzSet = reflections.getTypesAnnotatedWith(ComponentFn.class);
        for (Class<?> clazz : clazzSet) {
            try {
                if (!Operator.class.isAssignableFrom(clazz)) {
                    continue;
                }
                if (!clazz.isAnnotationPresent(ComponentFn.class)) {
                    continue;
                }
                Class<Operator<?, ?>> targetClass = (Class<Operator<?, ?>>) clazz;
                String operatorName = targetClass.getName();
                if(operatorsRegister.containsKey(operatorName)){
                    throw new EwaFlowException(String.format("Operator with name %s already exists", operatorName));
                }
                ComponentFn componentFn = clazz.getAnnotation(ComponentFn.class);
                OperatorFactory operatorFactory;
                if(SpringOperatorGenerate.class.isAssignableFrom(componentFn.builder())){
                    operatorFactory = SpringUtil.getBean(SpringOperatorGenerate.class);
                }else{
                    operatorFactory = DefaultOperatorGenerate.getInstance();
                }
                Class<?> nodeParam = componentFn.paramType();
                OpsType operatorType = ConditionOperator.class.isAssignableFrom(targetClass) ? OpsType.CONDITION : OpsType.STANDARD;
                operatorsRegister.register(new OperatorMeta(targetClass.getName(), operatorFactory, nodeParam, operatorType));
            } catch (Exception e) {
                throw new EwaFlowException(String.format("[%s] operator scanner error", clazz.getName()), e);
            }
        }
    }
}
