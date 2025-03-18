package com.ewa.engine.parser.definition;

import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public abstract class ConditionDefinition extends NodeDefinition {

    /**
     * condition test
     */
    protected String test;
}
