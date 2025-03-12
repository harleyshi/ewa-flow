package com.ewa.operator;

import com.ewa.operator.common.enums.OpsType;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;

/**
 * 算子元数据
 * @author harley.shi
 * @date 2025/1/23
 */
public class OperatorDef<C extends FlowCtx, O> {

    private final String name;

    private final OpsType type;

    private final Operator<C, O> operator;

    public OperatorDef(String name, OpsType type, Operator<C, O> operator) {
        this.name = name;
        this.type = type;
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public OpsType getType() {
        return type;
    }

    public Operator<C, O> getOperator() {
        return operator;
    }
}
