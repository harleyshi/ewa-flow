package com.ewa.engine.loader;

import com.ewa.engine.domain.EwaEngineDO;

import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public interface EngineLoader {
    /**
     * load engine list
     */
    List<EwaEngineDO> loadPublishedEngines();

    /**
     * load all engine list
     */
    List<EwaEngineDO> loadAllEngineList();

    /**
     * load engine by name
     * @param engineName engine name
     */
    EwaEngineDO getEngineByName(String engineName);
}
