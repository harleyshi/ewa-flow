package com.ewa.engine.core;

import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.core.context.FlowCtx;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author harley.shi
 * @date 2025/3/11
 */
public class ClassPathXmlEngineManager extends AbstractEngineManager {

    private final String locationPath;

    public ClassPathXmlEngineManager(String locationPath) {
        this.locationPath = locationPath;
    }

    @Override
    public void load() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(locationPath);
        } catch (Exception e) {
            throw new EwaFlowException("resource load failed", e);
        }
        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()){
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                EngineExecutor<FlowCtx> executor = parseEngine(new String(bytes, StandardCharsets.UTF_8));
                if(engineRegister.contains(executor.getName())){
                    throw new EwaFlowException(String.format("already contains engine [%s]", executor.getName()));
                }
                engineRegister.register(executor);
            }catch (Exception e){
                throw new EwaFlowException(String.format("[%s] file load failed", resource.getFilename()), e);
            }
        }
    }
}