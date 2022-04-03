package com.rlj.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-05
 */
@FeignClient(name = "eureka-client",fallback = MyFallback.class)
public interface HystrixService {
    @GetMapping("/error")
    public String error();

    @GetMapping("/timeout")
    public String timeout(@RequestParam(name = "timeout") int timeout);
}
