package com.rlj.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-03
 *
 */
// Eureka服务节点的服务名称，意为告诉此接口，后续发起接口调用，发送的请求都是到eureka-client中
@FeignClient("eureka-client")
public interface IService {
    // 一旦调用该接口的sayHi方法，就会将请求发送到eureka-client相关服务节点的sayHi方法中
    @GetMapping("/sayHi")
    String hello();

    // 一旦调用这个接口，会调用服务节点的对应方法，这里的参数就是指定服务节点对应方法睡眠的时间
    @GetMapping("retry")
    public String retryTest(@RequestParam(name = "timeout")int timeOut);
}



