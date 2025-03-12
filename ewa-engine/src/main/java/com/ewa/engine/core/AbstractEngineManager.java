package com.ewa.engine.core;

import com.ewa.engine.parser.*;
import com.ewa.engine.parser.definition.*;
import com.ewa.operator.common.enums.NodeType;
import com.ewa.operator.common.enums.ScriptLang;
import com.ewa.operator.ctx.FlowCtx;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.ewa.operator.common.constants.NodeAttrConstants.*;
/**
 * @author harley.shi
 * @date 2025/3/11
 */
public abstract class AbstractEngineManager implements EngineManager {
    protected final EngineRegister engineRegister;

    protected final DSLParser dslParser;

    public AbstractEngineManager() {
        this.engineRegister = EngineRegister.getInstance();
        this.dslParser = new DSLParser();
    }

    @Override
    public EngineExecutor<FlowCtx> getExecutor(String name) {
        return Objects.requireNonNull(engineRegister.get(name), String.format("engine %s not found", name));
    }

    protected EngineExecutor<FlowCtx> parseEngine(String xmlContent) throws Exception {
        return dslParser.parseEngine(xmlContent);
    }
}
