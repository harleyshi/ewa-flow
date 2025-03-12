package com.ewa.engine.parser.definition;


import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.utils.AssertUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harley.shi
 * @date 2025/3/11
 */
@Getter
public class EnginesDefinition extends NodeDefinition {

    private EngineDefinition engine;

    private List<PipelineDefinition> pipelines = new ArrayList<>();

    private List<ParamsDefinition> configParams =  new ArrayList<>();

    private List<ScriptDefinition> scripts =  new ArrayList<>();

    public void engine(EngineDefinition engine) {
        this.engine = engine;
    }

    public void addPipeline(PipelineDefinition pipeline) {
        pipelines.add(pipeline);
    }

    public void addConfigParam(ParamsDefinition configParam) {
        configParams.add(configParam);
    }

    public void addScript(ScriptDefinition script) {
        scripts.add(script);
    }

    public void validate() {
        AssertUtil.notNull(engine, "[engine] cannot be blank");
        pipelines.forEach(PipelineDefinition::validate);
        configParams.forEach(ParamsDefinition::validate);
        scripts.forEach(ScriptDefinition::validate);
        engine.validate();
    }

    @Override
    public String name() {
        return engine.name();
    }

    @Override
    public NodeType nodeType() {
        return NodeType.ENGINES;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
