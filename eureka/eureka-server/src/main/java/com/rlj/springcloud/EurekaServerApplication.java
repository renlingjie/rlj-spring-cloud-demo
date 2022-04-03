package com.rlj.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-26
 */
@SpringBootApplication
@EnableEurekaServer //加上这个注解，Eureka的启动类就会被识别为一个Eureka的注册中心
public class EurekaServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApplication.class)
                //指定启动方式是web，web的参数是SERVLET。和之前的启动方法，但应该都可以
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
