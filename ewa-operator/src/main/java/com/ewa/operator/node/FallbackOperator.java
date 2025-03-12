package com.ewa.operator.node;

import com.ewa.operator.ctx.FlowCtx;

/**
 * @author harley.shi
 * @date 2025/1/13
 */
public abstract class FallbackOperator<C extends FlowCtx> implements Operator<C, Void> {

    @Override
    public Void execute(C ctx) {
        try {
            doExecute(ctx);
        }catch (Exception e){
            doFallback(ctx);
        }
        return null;
    }

    /**
     * 执行节点
     */
    public abstract void doExecute(C ctx);

    /**
     * 处理失败
     */
    public abstract void doFallback(C ctx);

}
