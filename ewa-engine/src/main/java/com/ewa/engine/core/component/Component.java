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

    /**
     * ignore exception
     */
    private boolean ignoreException = false;

    public Component(String name) {
        this.name = name;
    }

    public void execute(C context){
        // 若出现异常，后续的节点不再执行
        if(context.hasException()){
            return;
        }
        try {
            doExecute(context);
        } catch (Throwable ex) {
            // 忽略异常：直接返回空结果
            if(isIgnoreException()){
                // 如果节点设置忽略异常的话，当节点发送异常时直接忽略
                log.error("[{}] operator execute error, but ignore it. error message: {}", getName(), ex.getMessage());
                return;
            }
            context.setHasException(true);
            throw ex;
        }
    }

    /**
     * 组件执行方法
     * @param context 上下文
     * @return 是否成功
     */
    public abstract void doExecute(C context);
}
