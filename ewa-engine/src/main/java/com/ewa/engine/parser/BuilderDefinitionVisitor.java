package com.ewa.engine.parser;

import cn.hutool.core.collection.CollectionUtil;
import com.ewa.engine.builder.*;
import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.core.component.*;
import com.ewa.engine.parser.definition.*;
import com.ewa.engine.script.ScriptExecutor;
import com.ewa.operator.OperatorsRegister;
import com.ewa.operator.ctx.FlowCtx;
import com.ewa.operator.node.Operator;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author harley.shi
 * @date 2024/11/20
 */
public class BuilderDefinitionVisitor<C extends FlowCtx> implements DefinitionVisitor {
    private EngineBuilder<C> engineBuilder;

    private final Map<String, String> paramsMap = new HashMap<>();

    private final Map<String, Component<C>> namedComponents = new HashMap<>();

    private final Map<String, PipelineComponent<C>> namedPipelines = new HashMap<>();

    private final Map<String, ScriptExecutor<?, C>> scriptExecutors = new HashMap<>();

    private final OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();

    @Override
    public void visit(EnginesDefinition definition) {
        List<PipelineDefinition> pipelinesDef = definition.getPipelines();
        List<ScriptDefinition> scripts = definition.getScripts();
        List<ParamsDefinition> configParams = definition.getConfigParams();
        EngineDefinition engineDefinition = definition.getEngine();


//        scripts.forEach(def -> def.visit(this));
        configParams.forEach(def -> def.visit(this));
        pipelinesDef.forEach(def -> def.visit(this));
        engineDefinition.visit(this);
    }

    @Override
    public void visit(EngineDefinition definition) {
        String engineName = definition.getName();
        PipelineComponent<C> pipelineComponent = namedPipelines.get(definition.getPipeline());

        engineBuilder = Builders.engine(engineName);
        engineBuilder.pipeline(pipelineComponent);
    }

    @Override
    public void visit(ChooseDefinition definition) {
        String test = definition.getTest();
        Operator<C, ?> condition = operatorsRegister.getOperator(test);
        ChooseBuilder<C> chooseBuilder = Builders.newChoose(test);
        chooseBuilder.test(condition);

        // 处理switch分支
        Map<String, List<NodeDefinition>> caseMap = definition.getCaseMap();
        caseMap.forEach((when, subList) -> {
            CaseBuilder<C> caseBuilder = chooseBuilder.when(when);
            caseBuilder.then(processChildren(subList));
        });

        // 处理默认分支
        if(CollectionUtil.isNotEmpty(definition.getDefaultDef())){
            processChildren(definition.getDefaultDef()).forEach(chooseBuilder::defaultBranch);
        }
        ChooseComponent<C> chooseComponent =  chooseBuilder.build();
        chooseComponent.setTimeout(definition.getTimeout());
        chooseComponent.setIgnoreException(definition.isIgnoreException());
        namedComponents.put(definition.name(), chooseComponent);
    }

    @Override
    public void visit(IfDefinition definition) {
        String test = definition.getTest();
        Operator<C, ?> condition = operatorsRegister.getOperator(test);
        IfBuilder<C> ifBuilder = Builders.newIf(definition.name());
        ifBuilder.test(condition);

        // 处理if分支
        processChildren(definition.getIfThen()).forEach(ifBuilder::then);

        // 处理else分支
        if(CollectionUtil.isNotEmpty(definition.getIfElse())){
            processChildren(definition.getIfElse()).forEach(ifBuilder::otherwise);
        }
        IfComponent<C> ifComponent = ifBuilder.build();
        ifComponent.setTimeout(definition.getTimeout());
        ifComponent.setIgnoreException(definition.isIgnoreException());
        namedComponents.put(definition.name(), ifComponent);
    }

    @Override
    public void visit(ComponentDefinition definition) {
        // 正常流程算子
        String opName = definition.getOperator();
        String paramsKey = definition.getParams();
        String paramsValue = null;
        if(StringUtils.isNotBlank(paramsKey)){
            paramsValue = paramsMap.get(paramsKey);
        }
        Operator<C, ?> operator = operatorsRegister.getOperator(opName);

        SimpleComponent<C> component = new SimpleComponent<>(opName);
        component.setOperator(operator);
        component.setDesc(definition.getDesc());
        component.setTimeout(definition.getTimeout());
        component.setIgnoreException(definition.isIgnoreException());
        namedComponents.put(definition.name(), component);
    }

    @Override
    public void visit(PipelineDefinition definition) {
        PipelineBuilder<C> pipelineBuilder = Builders.pipeline(definition.name());
        pipelineBuilder.async(definition.isAsync())
                .desc(definition.getDesc())
                .ignoreException(definition.isIgnoreException())
                .timeout(definition.getTimeout());

        processChildren(definition.getChildren()).forEach(pipelineBuilder::next);

        PipelineComponent<C> build = pipelineBuilder.build();
        namedPipelines.put(definition.name(), build);
    }

    @Override
    public void visit(ParamsDefinition definition) {
        paramsMap.put(definition.getName(), definition.getParams());
    }

    @Override
    public void visit(ScriptDefinition definition) {
        ScriptBuilder<?, C> scriptBuilder = Builders.script();
        ScriptExecutor<?, C> scriptExecutor = scriptBuilder.name(definition.getName())
                .script(definition.getScript())
                .type(definition.getType())
                .build();
        scriptExecutors.put(definition.getName(), scriptExecutor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public EngineExecutor<C> buildEngineExecutor() {
        return engineBuilder.build();
    }

    /**
     * 处理 NodeDefinition 列表，先 visit 再转换为 Component
     */
    private List<Component<C>> processChildren(List<NodeDefinition> children) {
        return children.stream()
                .peek(def -> def.visit(this))
                .map(this::getComponent)
                .collect(Collectors.toList());
    }

    /**
     * 根据 NodeDefinition 类型返回对应的 Component
     */
    private Component<C> getComponent(NodeDefinition node) {
        if (node instanceof PipelineDefinition) {
            return namedPipelines.get(node.name());
        }else {
            return namedComponents.get(node.name());
        }
    }
}
