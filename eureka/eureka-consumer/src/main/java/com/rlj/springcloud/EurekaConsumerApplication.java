package com.rlj.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-30
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaConsumerApplication {
    // 该消费者项目会通过负载均衡器拿到一个服务提供者，之后通过RestTemplate发起请求进行调用(后续会用Feign)。
    // 故先将RestTemplate声明一下，在项目启动时Spring就将其Bean加入到上下文中，之后就可@Autowired引入使用
    @Bean
    public RestTemplate addRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args){
        new SpringApplicationBuilder(EurekaConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
