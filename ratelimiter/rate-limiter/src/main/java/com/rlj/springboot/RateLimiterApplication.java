package com.rlj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateLimiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimiterApplication.class, args);
//        new SpringApplicationBuilder(RateLimiterApplication.class)
//                .web(WebApplicationType.SERVLET)
//                .run(args);
    }
}