package com.ewa.engine.script;

import com.ewa.operator.exception.OperatorExecutionException;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;

/**
 * @author harley.shi
 * @date 2025/3/18
 */
public class ScriptOperator<C extends FlowCtx, O> implements Operator<C, O> {

    private final ScriptExecutor<C, O> scriptExecutor;

    public ScriptOperator(ScriptExecutor<C, O> scriptExecutor) {
        this.scriptExecutor = scriptExecutor;
    }

    @Override
    public O execute(C ctx) {
        try {
            return scriptExecutor.execute(ctx);
        }catch(Exception e){
            throw new OperatorExecutionException(String.format("failed to execute script operator: [%s]", scriptExecutor.describe()), e);
        }
    }
}
