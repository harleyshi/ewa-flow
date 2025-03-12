package com.ewa.engine.parser.definition;

import com.ewa.engine.parser.DefinitionVisitor;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.utils.AssertUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ComponentDefinition extends NodeDefinition {

    /**
     * 组件描述
     */
    private String desc;

    /**
     * 算子
     */
    private String operator;

    /**
     * 算子参数
     */
    private String params;

    /**
     * 回滚算子
     */
    private String rollback;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否忽略异常
     */
    private boolean ignoreException = false;

    @Override
    public void validate() {
        AssertUtil.notBlank(operator, String.format("%s component [operator] cannot be null", operator));
    }

    @Override
    public String name() {
        return this.operator;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.COMPONENT;
    }

    @Override
    public void doVisit(DefinitionVisitor visitor) {
        visitor.visit(this);
    }
}
