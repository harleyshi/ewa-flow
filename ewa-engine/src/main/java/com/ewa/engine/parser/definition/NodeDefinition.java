package com.ewa.engine.parser.definition;


import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
public abstract class NodeDefinition implements Validator {
    protected volatile boolean visitCalled = false;

    protected Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer id(){
        return this.id;
    }

    public abstract String name();

    public abstract NodeType nodeType();

    public void visit(DefinitionVisitor visitor) {
        if (this.visitCalled) {
            return;
        }
        this.visitCalled = true;
        this.doVisit(visitor);
    }

    public abstract void doVisit(DefinitionVisitor visitor);
}
