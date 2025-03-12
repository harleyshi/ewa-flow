package com.ewa.operator.common.enums;

import lombok.Getter;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Getter
public enum NodeType {
    ENGINES("engines", "引擎根节点"),

    ENGINE("engine", "引擎节点"),

    PIPELINE("pipeline", "流程节点"),

    IF("if", "条件节点"),

    CHOOSE("choose", "多条件节点"),

    COMPONENT("component", "组件节点"),

    CONFIG_PARAMS("configParams", "配置参数节点"),

    SCRIPTS("scripts", "脚本节点")

    ;

    private final String code;

    private final String desc;

    NodeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static NodeType getByCode(String code) {
        for (NodeType nodeType : NodeType.values()) {
            if (nodeType.getCode().equals(code)) {
                return nodeType;
            }
        }
        return null;
    }
}
