package com.rlj.springcloud;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.time.ZonedDateTime;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-14
 */
@Configuration
public class GatewayConfiguration {
    @Bean
    @Order   // 指定我们自己定义的该Route在routes中的排序号
    public RouteLocator customizedRoutes(RouteLocatorBuilder builder){
        return builder
                .routes()                                // 其下可以添加多个.route()
                .route(r -> r.path("/java/**") // 路径以java开头
                        .and().method(HttpMethod.GET)    // 请求需要是Get请求
                        .and().header("name")            // 请求头要有name为key的键值对
                        .filters(f -> f.stripPrefix(1)   // 过滤器过滤掉第一个，且响应头添加一些内容
                                .addResponseHeader("java-param","gateway-config")
                        )
                        .uri("lb://EUREKA-CLIENT")       // 满足上述三个后，将请求负载均衡到到EUREKA-CLIENT下的某个节点中
                )
                .build();
    }
}
