package com.ewa.operator;


import java.util.HashMap;
import java.util.Map;

/**
 * operator metadata register
 * @author harley.shi
 * @date 2024/6/29
 */
public class OperatorsRegister {

    private static final OperatorsRegister INSTANCE = new OperatorsRegister();

    private final Map<String, OperatorMeta> operatorMetaMap = new HashMap<>();

    private OperatorsRegister() {

    }

    public static OperatorsRegister getInstance() {
        return INSTANCE;
    }

    public void register(OperatorMeta operatorDef) {
        this.operatorMetaMap.put(operatorDef.getOperatorName(), operatorDef);
    }

    public OperatorMeta getOperatorMeta(String name) {
        return this.operatorMetaMap.get(name);
    }

    public boolean containsKey(String name) {
        return this.operatorMetaMap.containsKey(name);
    }
}
