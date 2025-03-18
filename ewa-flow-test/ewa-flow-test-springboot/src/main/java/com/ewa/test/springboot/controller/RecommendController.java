package com.ewa.test.springboot.controller;


import com.ewa.engine.core.EngineExecutor;
import com.ewa.engine.core.EngineManager;
import com.ewa.operator.core.context.FlowCtx;
import com.ewa.test.springboot.context.OrderContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private EngineManager engineManager;

    @GetMapping("/test")
    public List<String> test(String engineName) {
        OrderContext ctx = new OrderContext();
        EngineExecutor<FlowCtx> executor = engineManager.getExecutor(engineName);
        executor.execute(ctx);
        return ctx.getItems();
    }
}
