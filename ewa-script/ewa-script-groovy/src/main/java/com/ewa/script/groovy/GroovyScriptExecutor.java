package com.ewa.script.groovy;

import com.ewa.engine.script.ScriptExecutor;
import com.ewa.operator.common.enums.ScriptLang;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.core.context.FlowCtx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * groovy script executor
 * @author harley.shi
 * @date 2024/10/29
 */
public class GroovyScriptExecutor<C extends FlowCtx, O> extends ScriptExecutor<C, O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptExecutor.class);

    private final ScriptEngine engine;

    public GroovyScriptExecutor(){
        ScriptEngineManager engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName(getScriptLang().getCode());
    }

    @Override
    public ScriptLang getScriptLang() {
        return ScriptLang.GROOVY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public O execute(C ctx) {
        try {
            Bindings data = engine.createBindings();
            data.put("ctx", ctx);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("script parameters: {}", ctx);
            }
            Object value = engine.eval(script, data);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[GroovyScriptExecutor] evaluate script [{}] finished", script);
                LOGGER.debug("[GroovyScriptExecutor] evaluate value {}", value);
            }
            return (O) value;
        } catch (Exception e) {
            throw new EwaFlowException(String.format("[%s] %s evaluate failed, script : %s", getScriptLang(), name, script), e);
        }
    }
}
