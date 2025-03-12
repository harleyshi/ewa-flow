//package com.ewa.engine.parser.xml;
//
//import com.ewa.engine.parser.definition.EnginesDefinition;
//import com.ewa.engine.parser.ParserCtx;
//import com.ewa.engine.parser.definition.ComponentDefinition;
//import com.ewa.engine.parser.definition.NodeDefinition;
//import com.ewa.operator.common.enums.NodeType;
//import org.dom4j.Element;
//
//import static com.ewa.operator.common.constants.NodeAttrConstants.*;
//
///**
// * @author harley.shi
// * @date 2024/10/29
// */
//public class ComponentElementParser extends ElementParser{
//
//    protected ComponentElementParser(EnginesDefinition builder) {
//        super(builder);
//    }
//
//    @Override
//    public NodeDefinition doParse(ParserCtx ctx, Element element) {
//        ComponentDefinition component = new ComponentDefinition();
//        // 正常流程算子
//        String operator = element.attributeValue(OPERATOR);
//        // 子流程
//        String subPipeline = element.attributeValue(SUB_PIPELINE);
//        // 正常算子参数
//        String operatorParamsKey = element.attributeValue(PARAMS);
//        // 用于回滚的算子信息
//        String rollback = element.attributeValue(ROLLBACK);
//
//        component.setOperator(operator);
//        component.setParams(operatorParamsKey);
//        component.setRollback(rollback);
//        component.setSubPipeline(subPipeline);
//        if (element.attributeValue(DESC) != null) {
//            component.setDesc(element.attributeValue(DESC));
//        }
//        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
//            component.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
//        }
//        if (element.attributeValue(TIMEOUT) != null) {
//            component.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
//        }
//        return component;
//    }
//
//    @Override
//    void handle(Element element) {
//
//    }
//
//    @Override
//    NodeType getElementName() {
//        return NodeType.COMPONENT;
//    }
//}
