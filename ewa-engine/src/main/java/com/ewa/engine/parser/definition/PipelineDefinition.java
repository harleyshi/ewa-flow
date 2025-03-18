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
public class PipelineDefinition extends NodeDefinition {

    /**
     * component name
     */
    private String name;

    /**
     * component description
     */
    private String desc;

    /**
     * is async
     */
    private boolean async;

    /**
     * timeout
     */
    private Integer timeout;

    /**
     * children nodes
     */
    private List<NodeDefinition> children = new ArrayList<>();

    public void addChild(NodeDefinition child) {
        children.add(child);
    }

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s pipeline [name] cannot be blank", name));
        children.forEach(NodeDefinition::validate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.PIPELINE;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
