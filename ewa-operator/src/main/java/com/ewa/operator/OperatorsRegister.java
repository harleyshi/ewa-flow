package com.ewa.operator;


import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import com.ewa.operator.utils.AssertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 算子定义注册器
 * @author harley.shi
 * @date 2024/6/29
 */
public class OperatorsRegister {

    private static final OperatorsRegister INSTANCE = new OperatorsRegister();

    private final Map<String, OperatorDef<?, ?>> operatorMap = new HashMap<>();

    private OperatorsRegister() {

    }

    public static OperatorsRegister getInstance() {
        return INSTANCE;
    }

    public <C extends FlowCtx, O> void register(OperatorDef<C, O> operatorDef) {
        this.operatorMap.put(operatorDef.getName(), operatorDef);
    }

    @SuppressWarnings("unchecked")
    public <C extends FlowCtx, O> Operator<C, O> getOperator(String name) {
        OperatorDef<C, O> operatorDef = (OperatorDef<C, O>) this.operatorMap.get(name);
        AssertUtil.notNull(operatorDef, String.format("[%s] 算子不存在或未定义", name));
        return operatorDef.getOperator();
    }

    public boolean containsKey(String name) {
        return this.operatorMap.containsKey(name);
    }
}
