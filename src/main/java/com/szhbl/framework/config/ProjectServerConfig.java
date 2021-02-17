package com.szhbl.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Filter配置
 *
 * @author szhbl
 */
@Component
@ConfigurationProperties(prefix = "server")
public class ProjectServerConfig
{
    private static String port;

    public static String getPort() {
        return port;
    }

    public void setPort(String port) {
        ProjectServerConfig.port = port;
    }
}
