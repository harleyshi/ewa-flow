package com.ewa.engine.core;

import com.ewa.engine.parser.*;
import com.ewa.operator.core.context.FlowCtx;

import java.util.Objects;

/**
 * @author harley.shi
 * @date 2025/3/11
 */
public abstract class AbstractEngineManager implements EngineManager {
    protected final EngineRegister engineRegister;

    protected final DSLParser dslParser;

    public AbstractEngineManager() {
        this.engineRegister = EngineRegister.getInstance();
        this.dslParser = new DSLParser();
    }

    @Override
    public EngineExecutor<FlowCtx> getExecutor(String name) {
        return Objects.requireNonNull(engineRegister.get(name), String.format("engine %s not found", name));
    }

    protected EngineExecutor<FlowCtx> parseEngine(String xmlContent) throws Exception {
        return dslParser.parseEngine(xmlContent);
    }
}
