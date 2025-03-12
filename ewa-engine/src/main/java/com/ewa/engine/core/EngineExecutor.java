package com.ewa.engine.core;

import com.ewa.operator.ctx.FlowCtx;
import com.ewa.engine.core.component.PipelineComponent;
import com.ewa.operator.node.Operator;
import com.ewa.operator.utils.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;


/**
 * @author harley.shi
 * @date 2025/3/11
 */
@Slf4j
public class EngineExecutor<C extends FlowCtx> {

    @Getter
    private final String name;

    private final PipelineComponent<C> pipelineComponent;

    public EngineExecutor(String name, PipelineComponent<C> pipelineComponent) {
        this.name = name;
        this.pipelineComponent = pipelineComponent;
    }

    public void execute(C context){
        AssertUtil.notNull(context, "context must not be null!");
        try{
            // 执行正常流程
            pipelineComponent.execute(context);
        }catch (Exception e){
            // 有异常：执行回滚逻辑
            if(context.hasException()){
                executeRollback(context);
            }
            throw e;
        }
    }

    public void executeRollback(C ctx) {
        Stack<Operator<FlowCtx, ?>> rollbackStack = ctx.rollbackStacks();
        while (!rollbackStack.isEmpty()){
            Operator<FlowCtx, ?> operator = rollbackStack.pop();
            if(operator == null){
                break;
            }
            try{
                operator.execute(ctx);
            }catch (Exception e){
                // TODO 回滚失败，做特殊处理
                log.error("[{}] executeRollback error", operator.getClass(), e);
            }
        }
    }
}