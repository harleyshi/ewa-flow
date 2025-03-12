package com.ewa.engine.parser.definition;

import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.utils.AssertUtil;
import lombok.Data;


/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class EngineDefinition extends NodeDefinition {

    /**
     * 组件描述
     */
    private String name;

    /**
     * 算子
     */
    private String pipeline;

    @Override
    public void validate() {
        AssertUtil.notBlank(pipeline, String.format("%s engine [pipeline] cannot be null", pipeline));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.ENGINE;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
