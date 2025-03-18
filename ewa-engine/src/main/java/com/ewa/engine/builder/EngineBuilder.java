package com.ewa.engine.builder;


import com.ewa.engine.core.executor.EngineExecutor;
import com.ewa.engine.core.component.Component;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.core.context.FlowCtx;

import java.util.ArrayList;
import java.util.List;

/**
 * engine builder
 * @author harley.shi
 * @date 2024/7/1
 */
public class EngineBuilder<C extends FlowCtx> {

    /**
     * engine name
     */
    private String name;

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

    public EngineExecutor<C> build() {
        AssertUtil.notBlank(name, "name must not be null");
        AssertUtil.notEmpty(components, "pipeline must not be null");
        return new EngineExecutor<>(name, components);
    }
}
