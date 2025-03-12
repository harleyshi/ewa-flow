package com.ewa.engine.core;


import com.ewa.engine.domain.EwaEngineDO;
import com.ewa.engine.common.exception.EwaFlowException;
import com.ewa.engine.loader.EngineLoader;
import com.ewa.operator.ctx.FlowCtx;

import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public class MySQLEngineManager extends AbstractEngineManager {
    private final EngineLoader engineLoader;

    public MySQLEngineManager(EngineLoader engineLoader) {
        this.engineLoader = engineLoader;
    }

    @Override
    public void load() {
        List<EwaEngineDO> engines = engineLoader.loadPublishedEngines();
        for (EwaEngineDO engine : engines) {
            try {
                EngineExecutor<FlowCtx> executor = parseEngine(engine.getContent());
                engineRegister.register(executor);
            }catch (Exception e){
                throw new EwaFlowException(String.format("[%s]engine load error", engine.getName()), e);
            }
        }
    }
}