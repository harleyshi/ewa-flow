package com.ewa.engine.core.component;

import cn.hutool.core.collection.CollectionUtil;
import com.ewa.operator.core.context.FlowCtx;

import java.util.List;

/**
 * @author harley.shi
 * @date 2024/7/1
 */
public class Invoker {

    public static <C extends FlowCtx> void invoke(C context, Component<C> component){
        component.execute(context);
    }

    public static <C extends FlowCtx> void invoke(C context, List<Component<C>> components) {
        if(CollectionUtil.isEmpty(components)){
            return;
        }
        for (Component<C> component : components) {
            invoke(context, component);
        }
    }
}
