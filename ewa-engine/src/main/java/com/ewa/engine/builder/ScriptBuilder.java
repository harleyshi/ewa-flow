package com.ewa.engine.builder;


import com.ewa.engine.script.ScriptDetector;
import com.ewa.engine.script.ScriptExecutor;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.factory.generate.DefaultOperatorGenerate;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.utils.AssertUtil;
import com.ewa.operator.utils.AuxiliaryUtils;

/**
 * script builder
 * @author harley.shi
 * @date 2024/7/1
 */
public class ScriptBuilder<C extends FlowCtx, O>{

    /**
     * Script's name, required.
     */
    private String name;

    /**
     * Script's type, required.
     */
    private String type;

    /**
     * Script content, required.
     */
    private String script;

    public ScriptBuilder<C, O> name(String name) {
        AssertUtil.notBlank(name, "name must not be blank");
        this.name = name;
        return this;
    }

    public ScriptBuilder<C, O> type(String type) {
        AssertUtil.notBlank(type, "type must not be blank");
        this.type = type;
        return this;
    }

    public ScriptBuilder<C, O> script(String script) {
        AssertUtil.notBlank(script, "script must not be blank");
        this.script = script;
        return this;
    }

    public ScriptExecutor<C, O> build() {
        AssertUtil.notBlank(script, "script must not be blank");
        AssertUtil.notBlank(type, "type must not be blank");
        AssertUtil.notBlank(name, "name must not be blank");

        ScriptExecutor<C, O> executor = createExecutor(type);
        executor.setName(name);
        executor.setScript(script);
        return executor;
    }

    @SuppressWarnings("unchecked")
    private ScriptExecutor<C, O> createExecutor(String type) {
        Class<?> full = AuxiliaryUtils.asClass(type);
        if (full == null) {
            full = ScriptDetector.get().getJavaType(type);
        }
        String typename = full != null ? full.getName() : type;
        try {
            return DefaultOperatorGenerate.getInstance().create(typename);
        }catch (Exception e){
            throw new EwaFlowException(String.format("Failed to create script executor for typename %s", typename), e);
        }
    }
}
