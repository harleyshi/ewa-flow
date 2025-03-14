package com.ewa.operator;

import com.ewa.operator.common.enums.OpsType;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import lombok.Getter;

/**
 * 算子元数据
 * @author harley.shi
 * @date 2025/1/23
 */
@Getter
public class OperatorDef<C extends FlowCtx, O> {

    private final String name;

    private final OpsType type;

    private final Class<?> nodeParam;

    private final Operator<C, O> operator;

    public OperatorDef(String name, Class<?> nodeParam, OpsType type, Operator<C, O> operator) {
        this.name = name;
        this.type = type;
        this.nodeParam = nodeParam;
        this.operator = operator;
    }
}
