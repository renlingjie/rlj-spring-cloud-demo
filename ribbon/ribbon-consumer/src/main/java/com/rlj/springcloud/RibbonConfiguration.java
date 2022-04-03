package com.rlj.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.rlj.springcloud.rules.ConsistentHashRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-02
 */
@Configuration
@RibbonClient(name = "eureka-client",configuration = ConsistentHashRule.class)
public class RibbonConfiguration {
    // 之后项目启动，就会用RandomRule替换掉默认的RoundRobinRule
//    @Bean
//    public IRule changeRule(){
//        return new RandomRule();
//    }
}
