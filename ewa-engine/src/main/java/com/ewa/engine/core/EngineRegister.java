package com.ewa.engine.core;

import com.ewa.operator.ctx.FlowCtx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
public class EngineRegister {
    private static final EngineRegister REGISTER = new EngineRegister();

    private final Map<String, EngineExecutor<FlowCtx>> engineExecutorMap = new ConcurrentHashMap<>();

    private EngineRegister(){}

    public static EngineRegister getInstance() {
        return REGISTER;
    }

    public void register(EngineExecutor<FlowCtx> flowEngine) {
        engineExecutorMap.put(flowEngine.getName(), flowEngine);
    }

    public EngineExecutor<FlowCtx> get(String name) {
        return engineExecutorMap.get(name);
    }

}
