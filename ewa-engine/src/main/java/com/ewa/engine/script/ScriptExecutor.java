package com.ewa.engine.script;

import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.common.enums.ScriptLang;
import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
@Data
public abstract class ScriptExecutor<C extends FlowCtx, O> implements Describable {
    /**
     * 脚本名称
     */
    protected String name;

    /**
     * 执行脚本
     */
    protected String script;

    /**
     * 获取脚本语言
     *
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
