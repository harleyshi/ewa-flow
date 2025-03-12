package com.ewa.engine.builder;

import com.ewa.engine.core.component.Component;
import com.ewa.engine.core.component.IfComponent;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import com.ewa.operator.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * if条件构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class IfBuilder<C extends FlowCtx> implements Builder<C> {

    private final String name;
    private Operator<C, ?> condition;
    private List<Component<C>> then = new ArrayList<>();
    private List<Component<C>> otherwise = new ArrayList<>();

    public IfBuilder(String name) {
        this.name = name;
    }

    public IfBuilder<C> test(Operator<C, ?> condition) {
        AssertUtil.notNull(condition, "condition must not be null!");
        this.condition = condition;
        return this;
    }

    public IfBuilder<C> then(Component<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.then.add(component);
        return this;
    }

    public IfBuilder<C> then(PipelineComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notEmpty(component.getPipeline(), "component pipeline must not be null!");
        this.then.add(component);
        return this;
    }

    public IfBuilder<C> otherwise(Component<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.otherwise.add(component);
        return this;
    }

    public IfBuilder<C> otherwise(PipelineComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.otherwise.add(component);
        return this;
    }

    @Override
    public IfComponent<C> build() {
        AssertUtil.notNull(condition, "condition must not be null");
        AssertUtil.notNull(then, "then branch must not be null");

        IfComponent<C> ifComponent = new IfComponent<>(name);
        ifComponent.setCondition(condition);
        ifComponent.setThenComponent(then);
        ifComponent.setElseComponent(otherwise);
        return ifComponent;
    }
}
