package com.ewa.operator.core.context;

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
}
