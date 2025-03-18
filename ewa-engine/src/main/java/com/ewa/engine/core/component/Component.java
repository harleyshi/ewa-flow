package com.ewa.engine.core.component;

import com.ewa.operator.core.context.FlowCtx;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * component base class
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public abstract class Component<C extends FlowCtx> {

    /**
     * component name
     */
    private String name;

    /**
     * timeout
     */
    private Integer timeout;

    public Component(String name) {
        this.name = name;
    }

    public void execute(C context){
        // 若出现异常，后续的节点不再执行
        if(context.hasException()){
            return;
        }
        doExecute(context);
    }

    /**
     * 组件执行方法
     * @param context 上下文
     */
    public abstract void doExecute(C context);
}
