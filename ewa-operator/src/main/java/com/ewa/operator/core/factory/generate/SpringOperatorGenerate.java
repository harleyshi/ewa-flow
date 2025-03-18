package com.ewa.operator.core.factory.generate;

import com.alibaba.fastjson2.JSONObject;
import com.ewa.operator.core.factory.OperatorFactory;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.utils.AuxiliaryUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harley.shi
 * @date 2025/3/17
 */
@SuppressWarnings("unchecked")
public class SpringOperatorGenerate implements OperatorFactory, ApplicationContextAware {

    private final Map<Class<?>, Constructor<?>> cachedConstructors = new ConcurrentHashMap<>();

    private static AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        beanFactory = ctx.getAutowireCapableBeanFactory();
    }

    @Override
    public <T> T create(String className) {
        AssertUtil.notBlank(className, "className must not be blank");
        Class<T> clazz = (Class<T>) AuxiliaryUtils.asClass(className);
        AssertUtil.notNull(clazz, String.format("cannot load class %s", className));
        try {
            Constructor<T> noargConstructor = getCachedNoArgConstructor(clazz);
            AssertUtil.notNull(noargConstructor, "cannot find no-arg constructor for className： " + className);
            T created = noargConstructor.newInstance();
            beanFactory.autowireBean(created);
            beanFactory.initializeBean(created, clazz.getSimpleName());
            return created;
        } catch (Exception e) {
            throw new EwaFlowException(String.format("[%s] create instance error", className), e);
        }
    }

    @Override
    public <T> T create(String className, Class<?> nodeParamType, String paramsKey, String paramsValue) {
        AssertUtil.notBlank(className, "className must not be blank");
        Class<T> clazz = (Class<T>) AuxiliaryUtils.asClass(className);
        AssertUtil.notNull(clazz, String.format("cannot load class %s", className));
        try {
            Constructor<T> constructor = getCachedNoArgConstructor(clazz);
            AssertUtil.notNull(constructor, "cannot find no-arg constructor for class: " + className);
            T created = constructor.newInstance();
            Object nodeParam = JSONObject.parseObject(paramsValue, nodeParamType);
            Field field = clazz.getDeclaredField(paramsKey);
            field.setAccessible(true);
            field.set(created, nodeParam);

            // give the bean the spring's features
            beanFactory.autowireBean(created);
            beanFactory.initializeBean(created, clazz.getSimpleName());
            return created;
        } catch (Exception e) {
            throw new EwaFlowException(String.format("[%s] create instance error", className), e);
        }
    }

    private <T> Constructor<T> getCachedNoArgConstructor(Class<T> clazz){
        Constructor<T> candidate = (Constructor<T>) cachedConstructors.get(clazz);
        if (candidate != null) {
            return candidate;
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            cachedConstructors.put(clazz, constructor);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new EwaFlowException("cannot find no-arg constructor for type " + clazz.getName(), e);
        }
    }
}
