package com.rlj.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-15
 */
@RestController
@Slf4j
public class Controller {
    //注入我们rate-limiter-annotation的AccessLimiter类
    @Autowired
    private AccessLimiter accessLimiter;

    @GetMapping("test")
    public String test(){
        accessLimiter.limiterAccess("ratelimiter-test",1);
        return "success";
    }

    // 如果该项目作为jar引入，注意配置扫包路径(com.rlj.springboot路径不同)
    @GetMapping("test-annotation")
    @com.rlj.springboot.annotation.AccessLimiter()
    public String testAnnotation(){
        return "success";
    }
}
