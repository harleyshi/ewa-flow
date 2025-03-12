package com.ewa.engine.loader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewa.engine.domain.EwaEngineDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author harley.shi
 * @date 2024-09-26 11:27:36
 */
@Mapper
public interface EwaEngineMapper extends BaseMapper<EwaEngineDO> {

    List<EwaEngineDO> listAll();

    List<EwaEngineDO> getPublishedEngines();

    EwaEngineDO getEngineByName(@Param("name") String name);
}