package com.ewa.engine.core.executor;

import com.ewa.engine.core.component.Component;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.utils.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author harley.shi
 * @date 2025/3/11
 */
@Slf4j
public class EngineExecutor<C extends FlowCtx> {

    @Getter
    private final String name;

    private final List<Component<C>> components;

    public EngineExecutor(String name, List<Component<C>> components) {
        this.name = name;
        this.components = components;
    }

    public void execute(C context){
        AssertUtil.notNull(context, "context must not be null!");
        // 执行正常流程
        for (Component<C> component : components) {
            component.execute(context);
        }
    }
}