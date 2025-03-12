package com.ewa.engine.parser;

import com.ewa.engine.parser.definition.NodeDefinition;
import org.dom4j.Element;

/**
 * 流程解析器接口
 * @author harley.shi
 * @date 2024/6/25
 */
public interface EngineParser {

    NodeDefinition parse(Element element);
}
