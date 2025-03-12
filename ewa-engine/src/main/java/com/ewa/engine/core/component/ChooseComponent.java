package com.ewa.engine.core.component;

import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * choose组件
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class ChooseComponent<C extends FlowCtx> extends Component<C> {

    private Operator<C, ?> condition;

    private Map<Object, List<Component<C>>> branches;

    private List<Component<C>> defaultBranch;

    public ChooseComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C ctx) {
        Object branch = condition.execute(ctx);
        List<Component<C>> components = null;
        if (branch != null && branches.containsKey(branch)) {
            components = branches.get(branch);
        } else if (defaultBranch != null) {
            components = defaultBranch;
        }
        if (components != null) {
            Invoker.invoke(ctx, components);
        }
    }
}
