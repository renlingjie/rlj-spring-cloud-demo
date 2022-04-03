package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-05
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class TraceASleuthApplication {

    @LoadBalanced
    @Bean
    public RestTemplate remote(){
        return new RestTemplate();
    }

    // 上面定义的Bean加入到IOC，直接在这里注入
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/traceA")
    public String traceA(){
        log.info("-----Trace A-----");
        // 远程调用TraceB
        return restTemplate.getForEntity("http://sleuth-traceB/traceB",String.class).getBody();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(TraceASleuthApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
