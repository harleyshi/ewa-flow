package com.ewa.engine.core.component;

import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;
import com.ewa.operator.exception.OperatorExecutionException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class IfComponent<C extends FlowCtx> extends Component<C> {

    private Operator<C, ?> condition;

    private List<Component<C>> thenComponent;

    private List<Component<C>> elseComponent;

    public IfComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C ctx) {
        Object test = condition.execute(ctx);
        if(test == null){
            Invoker.invoke(ctx, elseComponent);
            return;
        }
        if(!(test instanceof Boolean)){
            throw new OperatorExecutionException(String.format("operator [%s] must return boolean, but returned [%s].", getName(), test.getClass().getName()));
        }
        if(test == Boolean.TRUE){
            Invoker.invoke(ctx, thenComponent);
        }else{
            Invoker.invoke(ctx, elseComponent);
        }
    }
}
