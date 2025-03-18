package com.ewa.engine.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.ewa.engine.EwaEngineSpringInitializer;
import com.ewa.engine.core.ClassPathXmlEngineManager;
import com.ewa.engine.core.EngineManager;
import com.ewa.engine.core.MySQLEngineManager;
import com.ewa.engine.loader.EngineLoader;
import com.ewa.engine.loader.MySQLEngineLoader;
import com.ewa.operator.core.factory.generate.SpringOperatorGenerate;
import com.ewa.operator.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
@Configuration
@EnableConfigurationProperties({EwaEngineProperties.class})
public class EwaEngineAutoConfiguration {

    @Bean("ewaEngineDataSource")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "ewa.engine.store-type", havingValue = "mysql")
    public DataSource ewaEngineDataSource(EwaEngineProperties properties) {
        String storeType = properties.getStoreType();
        try {
            if(StringUtils.isBlank(storeType)){
                throw new RuntimeException("storeType property cannot be null");
            }
            if(storeType.equalsIgnoreCase("mysql")){
                EwaEngineProperties.MySQLConfig mySqlConfig = properties.getMysqlConfig();
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setUrl(mySqlConfig.getUrl());
                dataSource.setUsername(mySqlConfig.getUsername());
                dataSource.setPassword(mySqlConfig.getPassword());
                dataSource.init();
                return dataSource;
            }
            throw new RuntimeException("storeType is not supported");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "ewa.engine.store-type", havingValue = "mysql")
    public EngineLoader engineLoader(@Qualifier("walleFlowDataSource") DataSource dataSource) {
        return new MySQLEngineLoader(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "ewa.engine.store-type", havingValue = "mysql")
    public EngineManager mySqlEngineManager(EngineLoader engineLoader) {
        return new MySQLEngineManager(engineLoader);
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "ewa.engine.store-type", havingValue = "classpath")
    public EngineManager classPathXmlEngineManager(EwaEngineProperties properties) {
        AssertUtil.notNull(properties.getLocationPath(), "locationPath must not be null");
        return new ClassPathXmlEngineManager(properties.getLocationPath());
    }

    @Bean
    @ConditionalOnMissingBean
    public EwaEngineSpringInitializer ewaEngineSpringInitializer(EngineManager engineManager) {
        return new EwaEngineSpringInitializer(engineManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringOperatorGenerate springOperatorGenerate() {
        return new SpringOperatorGenerate();
    }
}