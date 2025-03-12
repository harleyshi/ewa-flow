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
public class ParamsDefinition extends NodeDefinition {
    private String name;

    private String desc;

    private String params;

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s params [name] cannot be blank", name));
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.CONFIG_PARAMS;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
