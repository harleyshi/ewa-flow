package com.ewa.operator;

import com.ewa.operator.common.enums.OpsType;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;
import com.ewa.operator.core.factory.OperatorFactory;
import lombok.Getter;

/**
 * operator meta data
 * @author harley.shi
 * @date 2025/1/23
 */
@Getter
public class OperatorMeta {

    private final String operatorName;

    private final OperatorFactory operatorFactory;

    private final OpsType opsType;

    private final Class<?> nodeParamType;

    public OperatorMeta(String className, OperatorFactory operatorFactory, Class<?> nodeParamType, OpsType opsType) {
        this.operatorName = className;
        this.operatorFactory = operatorFactory;
        this.nodeParamType = nodeParamType;
        this.opsType = opsType;
    }

    public <C extends FlowCtx> Operator<C, ?> builder() {
        return operatorFactory.create(operatorName);
    }

    public <C extends FlowCtx> Operator<C, ?> builder(String paramsKey, String paramsValue) {
        return operatorFactory.create(operatorName, nodeParamType, paramsKey, paramsValue);
    }
}
