package com.ewa.engine.builder;


import com.ewa.operator.ctx.FlowCtx;
import com.ewa.engine.core.component.Component;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * pipeline构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class PipelineBuilder<C extends FlowCtx> implements Builder<C> {
    private final String name;

    /**
     * 组件描述
     */
    private String desc;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否忽略异常
     */
    private boolean ignoreException = false;


    private final List<Component<C>> components = new ArrayList<>();

    public PipelineBuilder(String name) {
        this.name = name;
    }

    public PipelineBuilder<C> next(Component<C> component) {
        AssertUtil.notNull(component, "must not be null");
        this.components.add(component);
        return this;
    }

    public PipelineBuilder<C> desc(String desc) {
        AssertUtil.notNull(desc, "must not be null");
        this.desc = desc;
        return this;
    }

    public PipelineBuilder<C> async(boolean async) {
        this.async = async;
        return this;
    }

    public PipelineBuilder<C> timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public PipelineBuilder<C> ignoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
        return this;
    }

    public PipelineBuilder<C> next(PipelineComponent<C> component) {
        AssertUtil.notNull(component, "component must not be null");
        AssertUtil.notEmpty(component.getPipeline(), "component pipeline must not be null!");
        this.components.add(component);
        return this;
    }

    public PipelineComponent<C> build() {
        AssertUtil.notEmpty(components, "pipeline's components size must greater than zero");
        PipelineComponent<C> pipelineComponent = new PipelineComponent<>(name);
        pipelineComponent.setName(name);
        pipelineComponent.setAsync(async);
        pipelineComponent.setTimeout(timeout);
        pipelineComponent.setDesc(desc);
        pipelineComponent.setPipeline(components);
        return pipelineComponent;
    }

}
