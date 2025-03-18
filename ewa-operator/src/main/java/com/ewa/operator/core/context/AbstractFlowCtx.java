package com.ewa.operator.core.context;

/**
 * 后续在扩展
 * @author harley.shi
 * @date 2025/3/7
 */
public abstract class AbstractFlowCtx implements FlowCtx {

    /**
     * 流程是否有异常：true-是，false-否
     */
    private volatile boolean hasException;

    @Override
    public boolean hasException() {
        return hasException;
    }

    @Override
    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }
}