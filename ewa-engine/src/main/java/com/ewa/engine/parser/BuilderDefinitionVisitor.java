package com.ewa.engine.parser;

import cn.hutool.core.collection.CollectionUtil;
import com.ewa.engine.builder.*;
import com.ewa.engine.core.executor.EngineExecutor;
import com.ewa.engine.core.component.*;
import com.ewa.engine.parser.definition.*;
import com.ewa.engine.script.ScriptExecutor;
import com.ewa.engine.script.ScriptOperator;
import com.ewa.operator.OperatorMeta;
import com.ewa.operator.OperatorsRegister;
import com.ewa.operator.exception.EwaFlowException;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.operator.core.Operator;
import com.ewa.operator.utils.AuxiliaryUtils;
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

    private final Map<String, ScriptExecutor<C, ?>> scriptExecutors = new HashMap<>();

    private final OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();

    @Override
    public void visit(EnginesDefinition definition) {
        List<ScriptDefinition> scripts = definition.getScripts();
        List<ParamsDefinition> configParams = definition.getConfigParams();
        EngineDefinition engineDefinition = definition.getEngine();

        scripts.forEach(def -> def.visit(this));
        configParams.forEach(def -> def.visit(this));
        engineDefinition.visit(this);
    }

    @Override
    public void visit(EngineDefinition definition) {
        String engineName = definition.getName();
        engineBuilder = Builders.engine(engineName);
        processChildren(definition.getNodes()).forEach(engineBuilder::component);
    }

    @Override
    public void visit(ChooseDefinition definition) {
        Operator<C, ?> condition = getCondition(definition);
        ChooseBuilder<C> chooseBuilder = Builders.newChoose(definition.name());
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
        namedComponents.put(definition.name(), chooseComponent);
    }

    @Override
    public void visit(IfDefinition definition) {
        Operator<C, ?> condition = getCondition(definition);
        IfBuilder<C> ifBuilder = Builders.newIf(definition.name());
        ifBuilder.test(condition);

        // 处理if分支
        processChildren(definition.getIfThen()).forEach(ifBuilder::then);

        // 处理else分支
        if(CollectionUtil.isNotEmpty(definition.getIfElse())){
            processChildren(definition.getIfElse()).forEach(ifBuilder::otherwise);
        }
        IfComponent<C> ifComponent = ifBuilder.build();
        namedComponents.put(definition.name(), ifComponent);
    }

    /**
     * get condition operator
     * @param definition condition definition
     * @return condition operator
     */
    private Operator<C, ?> getCondition(ConditionDefinition definition) {
        String test = definition.getTest();
        Operator<C, ?> condition;
        if (AuxiliaryUtils.isType(test)) {
            condition = builderOperator(test);
        }else if(scriptExecutors.containsKey(test)){
            ScriptExecutor<C, ?> scriptExecutor = scriptExecutors.get(test);
            condition = new ScriptOperator<>(scriptExecutor);
        }else{
            throw new EwaFlowException("operator not found: " + test);
        }
        return condition;
    }

    @Override
    public void visit(ComponentDefinition definition) {
        String opName = definition.getOperator();
        String paramsKey = definition.getParams();
        Operator<C, ?> operator;
        if(StringUtils.isNotBlank(paramsKey)){
            String paramsValue = paramsMap.get(paramsKey);
            if(StringUtils.isBlank(paramsValue)){
                throw new EwaFlowException(String.format("params not found: %s", paramsKey));
            }
            operator = builderOperator(opName, paramsKey, paramsValue);
        }else{
            operator = builderOperator(opName);
        }
        SimpleComponent<C> component = new SimpleComponent<>(opName, operator);
        component.setDesc(definition.getDesc());
        component.setTimeout(definition.getTimeout());
        component.setIgnoreException(definition.isIgnoreException());

        namedComponents.put(definition.name(), component);
    }

    @Override
    public void visit(ScriptComponentDefinition definition) {
        String ref = definition.getRef();
        ScriptExecutor<C, ?> scriptExecutor = scriptExecutors.get(ref);
        if(scriptExecutor == null){
            throw new EwaFlowException("script not found: " + ref);
        }
        Operator<C, ?> operator = new ScriptOperator<>(scriptExecutor);
        SimpleComponent<C> component = new SimpleComponent<>(ref, operator);
        component.setOperator(operator);
        component.setTimeout(definition.getTimeout());
        component.setIgnoreException(definition.isIgnoreException());
        namedComponents.put(definition.name(), component);
    }

    @Override
    public void visit(PipelineDefinition definition) {
        PipelineBuilder<C> pipelineBuilder = Builders.pipeline(definition.name());
        pipelineBuilder.async(definition.isAsync())
                .desc(definition.getDesc())
                .timeout(definition.getTimeout());

        processChildren(definition.getChildren()).forEach(pipelineBuilder::next);

        PipelineComponent<C> build = pipelineBuilder.build();
        namedPipelines.put(definition.name(), build);
    }

    @Override
    public void visit(ParamsDefinition definition) {
        if(paramsMap.containsKey(definition.getName())){
            throw new EwaFlowException("params name already exists: " + definition.getName());
        }
        paramsMap.put(definition.getName(), definition.getParams());
    }

    @Override
    public void visit(ScriptDefinition definition) {
        ScriptBuilder<C, ?> scriptBuilder = Builders.script();
        ScriptExecutor<C, ?> scriptExecutor = scriptBuilder.name(definition.getName())
                .script(definition.getScript())
                .type(definition.getType())
                .build();
        if(scriptExecutors.containsKey(definition.getName())){
            throw new EwaFlowException("[%s]script name already exists: " + definition.getName());
        }
        scriptExecutors.put(definition.getName(), scriptExecutor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public EngineExecutor<C> buildEngineExecutor() {
        return engineBuilder.build();
    }

    /**
     * builder operator
     * @param opName operator name
     */
    public Operator<C, ?> builderOperator(String opName){
        OperatorMeta opMetadata = operatorsRegister.getOperatorMeta(opName);
        return opMetadata.builder();
    }

    public Operator<C, ?> builderOperator(String opName, String paramsKey, String paramsValue){
        OperatorMeta opMetadata = operatorsRegister.getOperatorMeta(opName);
        return opMetadata.builder(paramsKey, paramsValue);
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
