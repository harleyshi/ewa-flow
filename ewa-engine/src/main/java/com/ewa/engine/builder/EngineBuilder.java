package com.ewa.engine.builder;


import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.core.component.Component;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.ctx.FlowCtx;

import java.util.ArrayList;
import java.util.List;

/**
 * 引擎构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class EngineBuilder<C extends FlowCtx> {

    /**
     * engine name
     */
    private String name;

    /**
     * engine description
     */
    private String desc;

    /**
     * engine components
     */
    private List<Component<C>> components = new ArrayList<>();

    public EngineBuilder<C> component(Component<C> component) {
        AssertUtil.notNull(component, "component must not be null");
        this.components.add(component);
        return this;
    }

    public EngineBuilder<C> name(String name) {
        AssertUtil.notBlank(name, "name must not be blank");
        this.name = name;
        return this;
    }

    public EngineBuilder<C> desc(String desc) {
        AssertUtil.notBlank(desc, "desc must not be blank");
        this.desc = desc;
        return this;
    }

    public EngineExecutor<C> build() {
        AssertUtil.notBlank(name, "name must not be null");
        AssertUtil.notEmpty(components, "pipeline must not be null");
        return new EngineExecutor<>(name, components);
    }
}
