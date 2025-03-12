package com.ewa.engine.builder;


import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.ctx.FlowCtx;

/**
 * 引擎构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class EngineBuilder<C extends FlowCtx> {

    /**
     * 引擎名称
     */
    private String name;

    /**
     * 流程组件
     */
    private PipelineComponent<C> pipelineComponent;

    public EngineBuilder<C> pipeline(PipelineComponent<C> pipeline) {
        AssertUtil.notNull(pipeline, "pipeline must not be null");
        this.pipelineComponent = pipeline;
        return this;
    }

    public EngineBuilder<C> name(String name) {
        AssertUtil.notBlank(name, "name must not be null");
        this.name = name;
        return this;
    }

    public EngineExecutor<C> build() {
        AssertUtil.notBlank(name, "name must not be null");
        AssertUtil.notNull(pipelineComponent, "pipeline must not be null");
        return new EngineExecutor<>(name, pipelineComponent);
    }
}
