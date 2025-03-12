package com.ewa.operator.common.enums;

import lombok.Getter;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Getter
public enum OpsType {

    STANDARD("standard", "标准算子"),

    CONDITION("condition", "条件算子")

    ;

    private final String code;

    private final String desc;

    OpsType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OpsType getByCode(String code) {
        for (OpsType nodeType : OpsType.values()) {
            if (nodeType.getCode().equals(code)) {
                return nodeType;
            }
        }
        return null;
    }
}
