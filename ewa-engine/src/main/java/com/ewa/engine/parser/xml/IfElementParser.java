//package com.ewa.engine.parser.xml;
//
//import com.ewa.engine.parser.ParserCtx;
//import com.ewa.engine.parser.definition.IfDefinition;
//import com.ewa.engine.parser.definition.NodeDefinition;
//import org.dom4j.Element;
//
//import java.util.List;
//
//import static com.ewa.operator.common.constants.NodeAttrConstants.*;
///**
// * @author harley.shi
// * @date 2024/10/29
// */
//public class IfElementParser extends ElementParser {
//
//    public IfElementParser() {
//        super(builder);
//    }
//
//    @Override
//    public NodeDefinition doParse(ParserCtx ctx, Element element) {
//        IfDefinition ifDefinition = new IfDefinition();
//        String test = element.attributeValue(TEST);
//        ifDefinition.setTest(test);
//
//        // 处理默认分支
//        Element thenElement = element.element(THEN);
//        List<Element> thenChildren = thenElement.elements();
//        ifDefinition.setIfThen(parseSubElements(ctx, thenChildren));
//
//        // 处理else分支
//        Element elseElement = element.element(ELSE);
//        if(elseElement != null){
//            List<Element> elseChildren = elseElement.elements();
//            ifDefinition.setIfElse(parseSubElements(ctx, elseChildren));
//        }
//
//        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
//            ifDefinition.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
//        }
//        if (element.attributeValue(TIMEOUT) != null) {
//            ifDefinition.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
//        }
//        return ifDefinition;
//    }
//}
//
