package com.ewa.engine.core;

import com.ewa.engine.core.EngineExecutor;
import com.ewa.operator.ctx.FlowCtx;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public interface EngineManager {

    void load();

    EngineExecutor<FlowCtx> getExecutor(String name);
}
