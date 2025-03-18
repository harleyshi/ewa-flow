package com.ewa.engine.parser.definition;

import cn.hutool.core.collection.CollectionUtil;
import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.utils.AssertUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ChooseDefinition extends ConditionDefinition {

    /**
     * default branch
     */
    private List<NodeDefinition> defaultDef;

    /**
     * cash branch map
     */
    private Map<String, List<NodeDefinition>> caseMap;

    @Override
    public void validate() {
        AssertUtil.notBlank(test, String.format("%s choose [test] cannot be blank", test));
        AssertUtil.notBlank(test, String.format("%s choose [caseMap] cannot be empty", test));
        this.caseMap.values().forEach(value -> value.forEach(Validator::validate));
        if(CollectionUtil.isNotEmpty(defaultDef)){
            this.defaultDef.forEach(Validator::validate);
        }
    }

    @Override
    public String name() {
        return test;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.CHOOSE;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
