package com.ewa.engine.core.component;

import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 标准组件构造器
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class SimpleComponent<C extends FlowCtx> extends Component<C> {
    /**
     * 组件描述
     */
    private String desc;

    /**
     * 算子
     */
    private Operator<C, ?> operator;

    /**
     * 回滚组件
     */
    private Operator<C, ?> rollback;

    public SimpleComponent(String name) {
        super(name);
    }

    public SimpleComponent(String name, Operator<C, ?> operator) {
        super(name);
        this.operator = operator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doExecute(C ctx) {
        try {
            operator.execute(ctx);
        }finally {
            if(rollback != null){
                ctx.addRollback((Operator<FlowCtx, ?>) rollback);
            }
        }
    }
}
