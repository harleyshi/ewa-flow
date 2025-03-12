package com.ewa.engine.parser;

import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.parser.definition.*;
import com.ewa.operator.ctx.FlowCtx;

/**
 * @author harley.shi
 * @date 2025/3/10
 */
public interface DefinitionVisitor {
    default void visit(NodeDefinition definition) {
        definition.visit(this);
    }

    void visit(EnginesDefinition definition);

    void visit(EngineDefinition definition);

    void visit(ChooseDefinition definition);

    void visit(IfDefinition definition);

    void visit(ComponentDefinition definition);

    void visit(PipelineDefinition definition);

    void visit(ParamsDefinition definition);

    void visit(ScriptDefinition definition);

    <C extends FlowCtx> EngineExecutor<C> buildEngineExecutor();
}
