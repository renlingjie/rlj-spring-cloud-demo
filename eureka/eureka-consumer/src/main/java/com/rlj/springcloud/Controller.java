package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-30
 */
@RestController
@Slf4j
public class Controller {
    @Value("${server.port}")
    private String port;

    //负载均衡，将请求转发给服务提供者，在Eureka加载进来时，LoadBalancerClient就完成了初始化，
    //故直可接用，不过这是一个简易的负载均衡器，后续会替换成ribbon
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        //通过负载均衡器，拿到服务提供者的实例(会从注册中心的列表中选一个合适的)
        ServiceInstance instance = loadBalancerClient.choose("eureka-client");
        if (instance == null){
            return "No available instances";
        }
        //如果拿到了就可以发起调用了，拿到负载均衡器选择的提供者的url，通过restTemplate发起调用，并将结果返回
        String url = String.format("http://%s:%s/sayHi", instance.getHost(),instance.getPort());
        log.info("url is {}", url);
        return restTemplate.getForObject(url,String.class);
    }

    @PostMapping("/helloPost")
    public Friend helloPost(){
        //通过负载均衡器，拿到服务提供者的实例(会从注册中心的列表中选一个合适的)
        ServiceInstance instance = loadBalancerClient.choose("eureka-client");
        if (instance == null){
            return null;
        }
        //如果拿到了就可以发起调用了，拿到负载均衡器选择的提供者的url，通过restTemplate发起调用，并将结果返回
        String url = String.format("http://%s:%s/sayHiPost", instance.getHost(),instance.getPort());
        log.info("url is {}", url);
        Friend friend = new Friend();
        return restTemplate.postForObject(url,friend,Friend.class);
    }
}
