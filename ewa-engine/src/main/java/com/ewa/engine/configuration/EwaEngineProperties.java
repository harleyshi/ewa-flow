package com.ewa.engine.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ewa.engine")
public class EwaEngineProperties {

    private String storeType;

    private String locationPath;

    private MySQLConfig mysqlConfig;

    @Data
    public static class MySQLConfig {
        private String url;

        private String username;

        private String password;
    }
}
