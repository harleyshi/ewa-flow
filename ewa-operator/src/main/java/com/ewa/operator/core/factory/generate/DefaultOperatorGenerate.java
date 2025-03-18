package com.ewa.operator.core.factory.generate;

import com.alibaba.fastjson2.JSONObject;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.core.factory.OperatorFactory;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.utils.AuxiliaryUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harley.shi
 * @date 2025/3/18
 */
@SuppressWarnings("unchecked")
public class DefaultOperatorGenerate implements OperatorFactory {

    private static final OperatorFactory INSTANCE = new DefaultOperatorGenerate();

    private final Map<Class<?>, Constructor<?>> cachedConstructors = new ConcurrentHashMap<>();

    public static OperatorFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T create(String className) {
        AssertUtil.notBlank(className, "className must not be blank");
        Class<T> clazz = (Class<T>) AuxiliaryUtils.asClass(className);
        AssertUtil.notNull(clazz, String.format("cannot load class %s", className));
        try {
            Constructor<T> noargConstructor = getCachedNoArgConstructor(clazz);
            AssertUtil.notNull(noargConstructor, "cannot find no-arg constructor for type " + className);
            return noargConstructor.newInstance();
        } catch (Exception e) {
            throw new EwaFlowException(clazz.getName(), e);
        }
    }

    @Override
    public <T> T create(String className, Class<?> nodeParamType, String nodeParams) {
        AssertUtil.notBlank(className, "className must not be blank");
        Class<T> clazz = (Class<T>) AuxiliaryUtils.asClass(className);
        AssertUtil.notNull(clazz, String.format("cannot load class %s", className));
        try {
            Constructor<T> constructor = getCachedNoArgConstructor(clazz);
            AssertUtil.notNull(constructor, "cannot find no-arg constructor for class: " + className);
            T created = constructor.newInstance();
            if(StringUtils.isNotBlank(nodeParams)){
                Object nodeParam = JSONObject.parseObject(nodeParams, nodeParamType);
                Field field = clazz.getSuperclass().getDeclaredField("nodeParams");
                field.setAccessible(true);
                field.set(created, nodeParam);
            }
            return created;
        } catch (Exception e) {
            throw new EwaFlowException(String.format("[%s]create operator error", clazz.getSimpleName()), e);
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
