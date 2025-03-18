package com.ewa.engine.builder;


import com.ewa.operator.core.context.FlowCtx;
import com.ewa.engine.core.component.Component;

/**
 * builder interface for creating component
 * @author harley.shi
 * @date 2024/7/1
 */
public interface Builder< C extends FlowCtx> {

   Component<C> build();

}
