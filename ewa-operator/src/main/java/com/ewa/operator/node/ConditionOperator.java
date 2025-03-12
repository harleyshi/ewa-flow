package com.ewa.operator.node;

import com.ewa.operator.ctx.FlowCtx;

/**
 * 条件算子
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
