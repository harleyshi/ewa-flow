package com.ewa.engine.builder;


import com.ewa.operator.core.context.FlowCtx;

public final class Builders {

    public static <C extends FlowCtx> ChooseBuilder<C> newChoose(String name) {
        return new ChooseBuilder<>(name);
    }

    public static <C extends FlowCtx> IfBuilder<C> newIf(String name) {
        return new IfBuilder<>(name);
    }

    public static <C extends FlowCtx> PipelineBuilder<C> pipeline(String name) {
        return new PipelineBuilder<>(name);
    }

    public static <C extends FlowCtx, O> ScriptBuilder<C, O> script() {
        return new ScriptBuilder<>();
    }

    public static <C extends FlowCtx> EngineBuilder<C> engine(String name) {
        EngineBuilder<C> engineBuilder = new EngineBuilder<>();
        engineBuilder.name(name);
        return engineBuilder;
    }
}
