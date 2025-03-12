package com.ewa.engine.builder;

import com.ewa.engine.core.component.Component;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.utils.AssertUtil;

import java.util.List;

/**
 * case构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class CaseBuilder<C extends FlowCtx> {

    /**
     * Case condition.
     */
    private final Object when;

    private final ChooseBuilder<C> chooseBuilder;

    CaseBuilder(ChooseBuilder<C> chooseBuilder, Object when) {
        this.when = when;
        this.chooseBuilder = chooseBuilder;
    }

    public ChooseBuilder<C> then(Component<C> branch) {
        AssertUtil.notNull(branch, "branch must not be null");
        chooseBuilder.addBranch(when, branch);
        return chooseBuilder;
    }

    public ChooseBuilder<C> then(List<Component<C>> branchList) {
        AssertUtil.notEmpty(branchList, "branchList must not be empty");
        for (Component<C> component : branchList) {
            chooseBuilder.addBranch(when, component);
        }
        return chooseBuilder;
    }

    public ChooseBuilder<C> then(PipelineComponent<C> branch) {
        AssertUtil.notNull(branch, "branch must not be null");
        AssertUtil.notEmpty(branch.getPipeline(), "branch pipeline must not be empty");
        chooseBuilder.addBranch(when, branch);
        return chooseBuilder;
    }
}
