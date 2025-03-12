//package com.ewa.engine.parser.xml;
//
//import com.ewa.engine.parser.ParserCtx;
//import com.ewa.engine.parser.definition.ChooseDefinition;
//import com.ewa.engine.parser.definition.NodeDefinition;
//import org.dom4j.Element;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.ewa.operator.common.constants.NodeAttrConstants.*;
//
///**
// * @author harley.shi
// * @date 2024/10/29
// */
//public class ChooseElementParser extends ElementParser{
//
//
//    public ChooseElementParser() {
//        super(builder);
//    }
//
//    @Override
//    public NodeDefinition doParse(ParserCtx ctx, Element element) {
//        ChooseDefinition choose = new ChooseDefinition();
//        String test = element.attributeValue(TEST);
//        choose.setTest(test);
//        // 处理默认分支
//        Element defaultElement = element.element(DEFAULT);
//        if (defaultElement != null) {
//            List<Element> children = defaultElement.elements();
//            choose.setDefaultDef(parseSubElements(ctx, children));
//        }
//        // 处理条件分支
//        Map<String, List<NodeDefinition>> caseMap = new HashMap<>();
//        List<Element> cases = element.elements(CASE);
//        for (Element caseElement : cases) {
//            String caseStr = caseElement.attributeValue(WHEN);
//            List<Element> subElements = caseElement.elements();
//            caseMap.put(caseStr, parseSubElements(ctx, subElements));
//        }
//        choose.setCaseMap(caseMap);
//
//        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
//            choose.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
//        }
//        if (element.attributeValue(TIMEOUT) != null) {
//            choose.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
//        }
//        return choose;
//    }
//}
