package com.ewa.engine.builder;


import com.ewa.engine.core.component.Component;
import com.ewa.engine.core.component.ChooseComponent;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import com.ewa.operator.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * choose构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class ChooseBuilder<C extends FlowCtx> implements Builder<C> {

    private final String name;
    private Operator<C, ?> condition;
    private final Map<String, List<Component<C>>> branches = new ConcurrentHashMap<>();
    private List<Component<C>> defaultBranch = new ArrayList<>();

    public ChooseBuilder(String name) {
        this.name = name;
    }

    void addBranch(String when, Component<C> branch) {
        List<Component<C>> brancheList = branches.computeIfAbsent(when, k -> new ArrayList<>());
        brancheList.add(branch);
    }

    public ChooseBuilder<C> test(Operator<C, ?> condition) {
        AssertUtil.notNull(condition, "condition must not be null!");
        this.condition = condition;
        return this;
    }

    /**
     * Create a new branch in choose.
     */
    public CaseBuilder<C> when(String branch) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notNull(branch, "branch must not be null");
        AssertUtil.isFalse(branches.containsKey(branch), "duplicated branch");
        return new CaseBuilder<>(this, branch);
    }

    public ChooseBuilder<C> defaultBranch(Component<C> branch) {
        AssertUtil.notNull(branch, "You must invoke test method first!");
        this.defaultBranch.add(branch);
        return this;
    }

    @Override
    public ChooseComponent<C> build() {
        AssertUtil.notNull(condition, "condition must not be null!");
        AssertUtil.isFalse(branches.isEmpty(), "choose builder has empty branch!");

        ChooseComponent<C> chooseComponent = new ChooseComponent<>(name);
        chooseComponent.setCondition(condition);
        chooseComponent.setBranches(branches);
        chooseComponent.setDefaultBranch(defaultBranch);
        return chooseComponent;
    }
}
