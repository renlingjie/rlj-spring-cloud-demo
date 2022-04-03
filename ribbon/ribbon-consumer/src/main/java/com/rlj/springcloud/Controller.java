package com.rlj.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-02
 */
@RestController
public class Controller {
    @Autowired
    private RestTemplate restTemplate;
    // RestTemplate有Ribbon的负载均衡功能了，这里直接告诉要访问的serviceId是eureka-client，方法名是sayHi即可
    @GetMapping("/hello")
    public String sayHi(){
        return restTemplate.getForObject("http://eureka-client/sayHi",String.class);
    }
}
