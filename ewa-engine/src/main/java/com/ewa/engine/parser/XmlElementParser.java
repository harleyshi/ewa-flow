package com.ewa.engine.parser;

import com.ewa.engine.parser.definition.*;
import com.ewa.operator.common.enums.NodeType;
import org.dom4j.Element;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ewa.operator.common.constants.NodeAttrConstants.*;

/**
 * xml流程解析器
 * @author harley.shi
 * @version 1.0
 */
public class XmlElementParser implements EngineParser {

    private final AtomicInteger idGenerator;

    public XmlElementParser() {
        idGenerator = new AtomicInteger(1);
    }

    @Override
    public NodeDefinition parse(Element element) {
        NodeType nodeType = NodeType.getByCode(element.getName());
        switch (Objects.requireNonNull(nodeType)) {
            case ENGINE:
                return parseEngine(element);
            case PIPELINE:
                return parsePipeline(element);
            case IF:
                return parseIf(element);
            case CHOOSE:
                return parseChoose(element);
            case COMPONENT:
                return parseComponent(element);
            case SCRIPT_COMPONENT:
                return parseScriptComponent(element);
            default:
                throw new IllegalArgumentException("Unsupported node type: " + nodeType);
        }
    }

    private NodeDefinition parseEngine(Element engineElement) {
        // parse engine
        String name = engineElement.attributeValue(NAME);
        String desc = engineElement.attributeValue(DESC);
        EngineDefinition engineDef = new EngineDefinition();
        engineDef.setName(name);
        engineDef.setDesc(desc);
        List<Element> children = engineElement.elements();
        for (Element child : children) {
            engineDef.addNode(parse(child));
        }
        return engineDef;
    }

    private NodeDefinition parseComponent(Element element) {
        ComponentDefinition component = new ComponentDefinition();
        component.setId(idGenerator.getAndIncrement());
        // 正常流程算子
        String operator = element.attributeValue(EXECUTE);
        // 正常算子参数
        String operatorParamsKey = element.attributeValue(PARAMS);
        // 用于回滚的算子信息
        String rollback = element.attributeValue(ROLLBACK);

        component.setOperator(operator);
        component.setParams(operatorParamsKey);
        component.setRollback(rollback);
        if (element.attributeValue(DESC) != null) {
            component.setDesc(element.attributeValue(DESC));
        }
        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
            component.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            component.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return component;
    }

    private NodeDefinition parseScriptComponent(Element element) {
        ScriptComponentDefinition component = new ScriptComponentDefinition();
        component.setId(idGenerator.getAndIncrement());
        String ref = element.attributeValue(REF);
        component.setRef(ref);
        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
            component.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            component.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return component;
    }

    private NodeDefinition parsePipeline(Element element) {
        PipelineDefinition pipeline = new PipelineDefinition();
        pipeline.setId(idGenerator.getAndIncrement());
        List<Element> children = element.elements();
        for (Element child : children) {
            pipeline.addChild(parse(child));
        }
        if (element.attributeValue(NAME) != null) {
            pipeline.setName(element.attributeValue(NAME));
        }
        if (element.attributeValue(ASYNC) != null) {
            pipeline.setAsync(Boolean.parseBoolean(element.attributeValue(ASYNC)));
        }
        if (element.attributeValue(DESC) != null) {
            pipeline.setDesc(element.attributeValue(DESC));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            pipeline.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return pipeline;
    }

    private NodeDefinition parseIf(Element element) {
        IfDefinition ifDefinition = new IfDefinition();
        ifDefinition.setId(idGenerator.getAndIncrement());
        String test = element.attributeValue(TEST);
        ifDefinition.setTest(test);

        // 处理默认分支
        Element thenElement = element.element(THEN);
        ifDefinition.setIfThen(parseElements(thenElement.elements()));

        // 处理else分支
        Element elseElement = element.element(ELSE);
        if(elseElement != null){
            ifDefinition.setIfElse(parseElements(elseElement.elements()));
        }
        return ifDefinition;
    }

    private NodeDefinition parseChoose(Element element) {
        ChooseDefinition choose = new ChooseDefinition();
        choose.setId(idGenerator.getAndIncrement());
        String test = element.attributeValue(TEST);
        choose.setTest(test);
        // 处理默认分支
        Element defaultElement = element.element(DEFAULT);
        if (defaultElement != null) {
            choose.setDefaultDef(parseElements(defaultElement.elements()));
        }
        // 处理条件分支
        Map<String, List<NodeDefinition>> caseMap = new HashMap<>();
        List<Element> cases = element.elements(CASE);
        for (Element caseElement : cases) {
            String caseStr = caseElement.attributeValue(WHEN);
            caseMap.put(caseStr, parseElements(caseElement.elements()));
        }
        choose.setCaseMap(caseMap);
        return choose;
    }

    private List<NodeDefinition> parseElements(List<Element> children) {
        if(children == null || children.isEmpty()){
            return null;
        }
        List<NodeDefinition> definitions = new ArrayList<>();
        for (Element child : children) {
            NodeDefinition childNode = parse(child);
            if (childNode != null) {
                definitions.add(childNode);
            }
        }
        return definitions;
    }
}
