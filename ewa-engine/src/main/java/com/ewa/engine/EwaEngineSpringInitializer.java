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

    private final OperatorsScanner operatorsScanner;

    public EwaEngineSpringInitializer(EngineManager engineManager, OperatorsScanner operatorsScanner) {
        this.engineManager = engineManager;
        this.operatorsScanner = operatorsScanner;
    }

    @Override
    public void afterSingletonsInstantiated() {
        // scan operators
        operatorsScanner.scannerOperators();
        // init engine
        engineManager.load();
    }
}
