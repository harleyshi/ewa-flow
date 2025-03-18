package com.ewa.engine;

import com.ewa.engine.core.EngineManager;
import com.ewa.operator.OperatorsScanner;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public class EwaEngineSpringInitializer implements SmartInitializingSingleton{

    private final EngineManager engineManager;

    public EwaEngineSpringInitializer(EngineManager engineManager) {
        this.engineManager = engineManager;
    }

    @Override
    public void afterSingletonsInstantiated() {
        OperatorsScanner operatorsScanner = new OperatorsScanner();
        // TODO 包方外面传进来
        operatorsScanner.scannerOperators("com.ewa.test.springboot.operator");
        // init engine
        engineManager.load();
    }
}
