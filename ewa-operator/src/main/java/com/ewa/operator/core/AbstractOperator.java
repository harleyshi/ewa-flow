package com.ewa.operator.core;

import com.ewa.operator.core.context.FlowCtx;

/**
 * @author harley.shi
 * @date 2025/1/13
 */
public abstract class AbstractOperator<C extends FlowCtx> implements Operator<C, Void> {

    @Override
    public Void execute(C ctx) {
        doExecute(ctx);
        return null;
    }

    /**
     * 执行节点
     */
    public abstract void doExecute(C ctx);

}
