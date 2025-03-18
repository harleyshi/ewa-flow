package com.ewa.operator;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class OperatorsScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(OperatorsScan.class.getName());
        if(annotationAttributes == null){
            return;
        }
        String[] packagePaths = (String[]) annotationAttributes.get("basePackages");
        if (packagePaths == null || packagePaths.length == 0) {
            String basePackage = getBasePackage(importingClassMetadata);
            packagePaths = new String[]{basePackage};
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(OperatorsScanner.class);
        builder.addPropertyValue("packagePaths", packagePaths);
        registry.registerBeanDefinition("operatorsScanner", builder.getBeanDefinition());
    }

    /**
     * get application base package
     */
    private String getBasePackage(AnnotationMetadata importingClassMetadata) {
        String className = importingClassMetadata.getClassName();
        return className.substring(0, className.lastIndexOf('.'));
    }
}