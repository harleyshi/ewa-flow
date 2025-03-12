package com.ewa.engine.builder;


import com.ewa.operator.ctx.FlowCtx;
import com.ewa.engine.core.component.Component;

/**
 * 构造器接口，用于构建组件
 * @author harley.shi
 * @date 2024/7/1
 */
public interface Builder< C extends FlowCtx> {

   Component<C> build();

}
