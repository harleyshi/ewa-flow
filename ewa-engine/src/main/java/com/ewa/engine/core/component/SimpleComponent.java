package com.ewa.engine.core.component;

import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * simple component
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class SimpleComponent<C extends FlowCtx> extends Component<C> {
    /**
     * component description
     */
    private String desc;

    /**
     * component operator
     */
    private Operator<C, ?> operator;

    /**
     * ignore exception
     */
    private boolean ignoreException = false;

    public SimpleComponent(String name) {
        super(name);
    }

    public SimpleComponent(String name, Operator<C, ?> operator) {
        super(name);
        this.operator = operator;
    }

    @Override
    public void doExecute(C ctx) {
        try {
            operator.execute(ctx);
        } catch (Throwable ex) {
            // 忽略异常：直接返回空结果
            if(isIgnoreException()){
                // 如果节点设置忽略异常的话，当节点发送异常时直接忽略
                log.error("[{}] operator execute error, but ignore it. error message: {}", getName(), ex.getMessage());
                return;
            }
            ctx.setHasException(true);
            throw ex;
        }
    }
}
