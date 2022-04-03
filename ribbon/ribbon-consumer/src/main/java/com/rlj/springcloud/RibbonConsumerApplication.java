package com.rlj.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-30
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RibbonConsumerApplication {
    // 相较于之前Eureka，这里多了一个@LoadBalanced，它给RestTemplate添加Ribbon的负载均衡功能，
    // 之后我只需要传入服务名，就可以由Ribbon给我们选择一个具体的服务节点
    @Bean
    @LoadBalanced
    public RestTemplate addRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args){
        new SpringApplicationBuilder(RibbonConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
