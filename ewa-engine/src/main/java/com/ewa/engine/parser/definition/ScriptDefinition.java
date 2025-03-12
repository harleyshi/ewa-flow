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
public class ScriptDefinition extends NodeDefinition {
    private String name;

    private String type;

    private String script;

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s pipeline [name] cannot be blank", name));
        AssertUtil.notBlank(type, String.format("%s type [name] cannot be blank", name));
        AssertUtil.notBlank(script, String.format("%s script [script] cannot be blank", name));
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.SCRIPTS;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
