package com.ewa.operator.core;

import com.ewa.operator.core.context.FlowCtx;

/**
 * operator interface. class must annotate with @ComponentFn
 * @see com.ewa.operator.ComponentFn
 * @author harley.shi
 * @date 2024/7/1
 */
public interface Operator<C extends FlowCtx, O> {

     /**
      * 执行 OP 算子的核心逻辑
      */
     O execute(C ctx);
}
