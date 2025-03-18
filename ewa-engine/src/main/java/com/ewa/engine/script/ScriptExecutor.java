package com.ewa.engine.script;

import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.common.enums.ScriptLang;
import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
@Data
public abstract class ScriptExecutor<C extends FlowCtx, O> implements Describable {
    /**
     * script name.
     */
    protected String name;

    /**
     * script content.
     */
    protected String script;

    /**
     * get script language.
     * @return type string
     */
    public abstract ScriptLang getScriptLang();

    /**
     * Execute script.
     * @param ctx ctx.
     * @return    result.
     */
    public abstract O execute(C ctx);

    @Override
    public String describe() {
        return name + "@" + getScriptLang().getCode() + "-script";
    }
}
