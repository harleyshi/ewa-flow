package com.ewa.engine.parser.definition;

import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.utils.AssertUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class EngineDefinition extends NodeDefinition {

    /**
     * engine name
     */
    private String name;

    /**
     * engine description
     */
    private String desc;

    /**
     * pipelines
     */
    private List<NodeDefinition> nodes = new ArrayList<>();

    @Override
    public void validate() {
        AssertUtil.notNull(name, String.format("%s engine [name] cannot be empty", name));
        AssertUtil.notEmpty(nodes, String.format("%s engine [nodes] cannot be empty", nodes));
    }

    public void addNode(NodeDefinition node) {
        nodes.add(node);
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
