package com.ewa.operator.core;

import com.ewa.operator.core.context.FlowCtx;

/**
 * condition operator
 * @author harley.shi
 * @date 2025/1/13
 */
public abstract class ConditionOperator<C extends FlowCtx, O> implements Operator<C, O> {

    @Override
    public O execute(C ctx) {
        return doExecute(ctx);
    }

    /**
     * 执行节点
     */
    public abstract O doExecute(C ctx);

}
