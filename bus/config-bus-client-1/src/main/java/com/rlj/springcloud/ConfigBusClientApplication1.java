package com.rlj.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-08
 */
@SpringBootApplication
@EnableDiscoveryClient  //要进行服务发现，发现配置中心
public class ConfigBusClientApplication1 {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusClientApplication1.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
