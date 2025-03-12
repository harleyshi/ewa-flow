//package com.ewa.engine.parser.xml;
//
//import com.ewa.engine.parser.ParserCtx;
//import com.ewa.engine.parser.definition.NodeDefinition;
//import com.ewa.engine.parser.definition.PipelineDefinition;
//import org.dom4j.Element;
//
//import java.util.List;
//
//import static com.ewa.operator.common.constants.NodeAttrConstants.*;
///**
// * @author harley.shi
// * @date 2024/10/29
// */
//public class PipelineElementParser extends ElementParser {
//
//    public PipelineElementParser() {
//        super(builder);
//    }
//
//    @Override
//    public NodeDefinition doParse(ParserCtx ctx, Element element) {
//        PipelineDefinition pipeline = new PipelineDefinition();
//        List<Element> children = element.elements();
//        for (Element child : children) {
//            pipeline.addChild(parseSubElement(ctx, child));
//        }
//        if (element.attributeValue(NAME) != null) {
//            pipeline.setName(element.attributeValue(NAME));
//        }
//        if (element.attributeValue(ASYNC) != null) {
//            pipeline.setAsync(Boolean.parseBoolean(element.attributeValue(ASYNC)));
//        }
//        if (element.attributeValue(DESC) != null) {
//            pipeline.setDesc(element.attributeValue(DESC));
//        }
//        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
//            pipeline.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
//        }
//        if (element.attributeValue(TIMEOUT) != null) {
//            pipeline.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
//        }
//        return pipeline;
//    }
//}
