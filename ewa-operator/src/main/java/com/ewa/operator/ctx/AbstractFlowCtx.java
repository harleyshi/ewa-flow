package com.ewa.operator.ctx;

import com.ewa.operator.node.Operator;

import java.util.Stack;

/**
 * 后续在扩展
 * @author harley.shi
 * @date 2025/3/7
 */
public abstract class AbstractFlowCtx implements FlowCtx {

    /**
     * 回滚算子栈
     */
    private final Stack<Operator<FlowCtx, ?>> rollbackInvokers = new Stack<>();

    /**
     * 流程是否有异常：true-是，false-否
     */
    private volatile boolean hasException;

    @Override
    public void addRollback(Operator<FlowCtx, ?> invoker) {
        if (invoker == null) {
            return;
        }
        rollbackInvokers.push(invoker);
    }

    @Override
    public Stack<Operator<FlowCtx, ?>> rollbackStacks() {
        return this.rollbackInvokers;
    }

    @Override
    public boolean hasException() {
        return hasException;
    }

    @Override
    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }
}