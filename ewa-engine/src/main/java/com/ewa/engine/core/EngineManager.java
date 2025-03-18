package com.ewa.engine.core;

import com.ewa.operator.core.context.FlowCtx;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public interface EngineManager {

    void load();

    EngineExecutor<FlowCtx> getExecutor(String name);
}
