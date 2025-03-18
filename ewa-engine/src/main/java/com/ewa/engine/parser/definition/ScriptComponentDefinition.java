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
public class ScriptComponentDefinition extends NodeDefinition {

    /**
     * ref script
     */
    private String ref;

    /**
     * timeout
     */
    private Integer timeout;

    /**
     * is ignore exception
     */
    private boolean ignoreException = false;

    @Override
    public void validate() {
        AssertUtil.notBlank(ref, String.format("%s scriptComponent [ref] cannot be null", ref));
    }

    @Override
    public String name() {
        return this.ref;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.SCRIPT_COMPONENT;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
