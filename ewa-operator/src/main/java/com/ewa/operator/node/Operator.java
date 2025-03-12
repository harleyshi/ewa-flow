package com.ewa.operator.node;

import com.ewa.operator.ctx.FlowCtx;

/**
 * 算子函数接口
 * @see com.ewa.operator.ComponentFn 注解的方法必须返回Operator接口的实例
 * @author harley.shi
 * @date 2024/7/1
 */
public interface Operator<C extends FlowCtx, O> {
     /**
      * 执行 OP 算子的核心逻辑
      */
     O execute(C ctx);
}
