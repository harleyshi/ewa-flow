package com.ewa.engine.core.component;

import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * choose component
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class ChooseComponent<C extends FlowCtx> extends Component<C> {

    private Operator<C, ?> condition;

    private Map<String, List<Component<C>>> branches;

    private List<Component<C>> defaultBranch;

    public ChooseComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C ctx) {
        Object branch = condition.execute(ctx);
        if(branch == null){
            Invoker.invoke(ctx, defaultBranch);
            return;
        }
        List<Component<C>> components = null;
        String branchStr = branch.toString();
        if (branches.containsKey(branchStr)) {
            components = branches.get(branchStr);
        } else if (defaultBranch != null) {
            components = defaultBranch;
        }
        if (components != null) {
            Invoker.invoke(ctx, components);
        }
    }
}
