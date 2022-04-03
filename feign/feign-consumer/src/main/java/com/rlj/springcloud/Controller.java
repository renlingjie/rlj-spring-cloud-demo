package com.rlj.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-03
 */
@RestController
public class Controller {
    // Feign会自动为加上注解@FeignClient("eureka-client")的接口IService生成代理类，
    // 所以这里看似我们没有写实现类，但是注入的正是该接口的代理类
    @Autowired
    private IService service;

    @GetMapping("hello")
    public String hello(){
        return service.hello();
    }

    @GetMapping("retry")
    public String retry(@RequestParam(name = "timeout") int timeout){
        return service.retryTest(timeout);
    }
}
