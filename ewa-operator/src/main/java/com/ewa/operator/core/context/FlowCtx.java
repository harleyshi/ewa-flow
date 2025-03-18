package com.ewa.operator.core.context;

import com.ewa.operator.core.Operator;

import java.util.Stack;

/**
 * @author harley.shi
 * @date 2025/1/13
 */
public interface FlowCtx {

    /**
     * 是否有异常
     */
    boolean hasException();

    /**
     * 设置是否有异常
     */
    void setHasException(boolean hasException);

    /**
     * 获取异常算子栈
     */
    Stack<Operator<FlowCtx, ?>> rollbackStacks();

    /**
     * 添加回滚算子
     */
    void addRollback(Operator<FlowCtx, ?> rollback);
}
