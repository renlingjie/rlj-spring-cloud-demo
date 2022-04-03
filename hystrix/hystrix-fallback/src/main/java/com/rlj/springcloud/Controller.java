package com.rlj.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-05
 */
@RestController
public class Controller {

    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/hystrix-fallback")
    public String hystrixFallback(){
        return hystrixService.error();
    }


    @GetMapping("/hystrix-timeout")
    public String hystrixTimeout(Integer timeout){
        return hystrixService.timeout(timeout);
    }

    @GetMapping("/test")
    public String timeout(@RequestParam(name = "timeout") int timeout){
        try {
            System.out.println(timeout * 1000);
            Thread.sleep((long) timeout * 1000);
            System.out.println("线程睡眠已经结束，继续执行了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "has execute " + timeout + "s";
    }
}
