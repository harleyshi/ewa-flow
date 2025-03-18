package com.ewa.engine.parser;

import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.parser.definition.*;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.common.enums.ScriptLang;
import com.ewa.operator.core.context.FlowCtx;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ewa.operator.common.constants.NodeAttrConstants.*;

/**
 * @author harley.shi
 * @date 2025/3/11
 */
public class DSLParser {

    public EngineExecutor<FlowCtx> parseEngine(String xmlContent) throws Exception {
        SAXReader saxReader = new SAXReader();
        StringReader reader = new StringReader(xmlContent);
        Document document = saxReader.read(reader);
        Element rootElement = document.getRootElement();
        EngineParser xmlParser = new XmlElementParser();

        // parse pipeline
        EnginesDefinition enginesDef = new EnginesDefinition();
        Element engineElement = rootElement.element(NodeType.ENGINE.getCode());
        EngineDefinition engineDef = (EngineDefinition) xmlParser.parse(engineElement);
        enginesDef.engine(engineDef);

        // parse config params
        Element parmasElement = rootElement.element(NodeType.CONFIG_PARAMS.getCode());
        List<ParamsDefinition> paramDefinitions = parseParams(parmasElement);
        paramDefinitions.forEach(enginesDef::addConfigParam);

        // parse script
        Element scriptElement = rootElement.element(NodeType.SCRIPTS.getCode());
        List<ScriptDefinition> scriptDefinitions = parseScript(scriptElement);
        scriptDefinitions.forEach(enginesDef::addScript);

        // build engine executor
        enginesDef.validate();
        DefinitionVisitor visitor = new BuilderDefinitionVisitor<>();
        visitor.visit(enginesDef);
        return visitor.buildEngineExecutor();
    }

    /**
     * parse params
     */
    private List<ParamsDefinition> parseParams(Element element) {
        if(element == null){
            return Collections.emptyList();
        }
        List<Element> children = element.elements();
        List<ParamsDefinition> paramsList = new ArrayList<>();
        for (Element child : children) {
            String name = child.attributeValue(NAME);
            String desc = child.attributeValue(DESC);
            String params = child.getStringValue();
            ParamsDefinition paramsDefinition = new ParamsDefinition();
            paramsDefinition.setName(name);
            paramsDefinition.setDesc(desc);
            paramsDefinition.setParams(params);
            paramsList.add(paramsDefinition);
        }
        return paramsList;
    }

    /**
     * parse script
     */
    private List<ScriptDefinition> parseScript(Element element) {
        if(element == null){
            return Collections.emptyList();
        }
        List<Element> children = element.elements();
        List<ScriptDefinition> scriptList = new ArrayList<>();
        for (Element child : children) {
            String name = child.attributeValue(NAME);
            String type = child.attributeValue(SCRIPT_TYPE);
            String scriptValue = child.getStringValue();
            ScriptDefinition scriptDefinition = new  ScriptDefinition();
            scriptDefinition.setName(name);
            // default groovy
            scriptDefinition.setType(StringUtils.isBlank(type) ? ScriptLang.GROOVY.getCode() : type);
            scriptDefinition.setScript(scriptValue);
            scriptList.add(scriptDefinition);
        }
        return scriptList;
    }
}
